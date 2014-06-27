/*
 * Created on 10.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/parser/LocationParser.java,v 1.2 2005/02/17 08:25:16 wova Exp $
 */
package de.vahrson.seq.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.vahrson.seq.Position;
import de.vahrson.seq.Region;

/**
 * @author wova
 * 
 * Parse an EMBL location description E.g.:
 * complement(join(171937..173156,173327..173511,174327..174511))
 */
public class LocationParser {
    private String fDescription;

    private List fTokens;

    // state during analysis:
    private ListIterator fTokenIterator;

    private Token fToken;

    //
    private List fRegions;

    public LocationParser(String locationDescription) {
        fDescription = locationDescription;
        fTokens = new ArrayList();
    }

    /**
     * 
     * @return List of Regions
     */
    public List parse() throws Exception {
        fRegions = new ArrayList();
        tokenize();
        analyze();
        return fRegions;
    }

    static class Token {
        static final String[] FIXED_TOKENS = { "join", "complement", "(", ")", ",", "..", "<", ">","^" };

        static final int JOIN = 0;
        static final int COMPLEMENT = 1;
        static final int LIST_OPEN = 2;
        static final int LIST_CLOSE = 3;
        static final int LIST_SEP = 4;
        static final int POS_SEP = 5;
        static final int LESS = 6;
        static final int GREATER = 7;
        static final int BETWEEN = 8;

        static final int NUMBER = 100;

        static final int START = 101;

        static final int STOP = 102;

        static final Token START_TOKEN = new Token(START, "");

        static final Token STOP_TOKEN = new Token(STOP, "");

        int fType;

        String fValue;

        public Token(int type, String value) {
            fType = type;
            fValue = value;
        }

        public String toString() {
            return fType + ":" + fValue;
        }
    }

    void tokenize() throws Exception {
        String src = fDescription;
        Pattern numberPattern = Pattern.compile("^\\d+"); // string starts with
                                                          // number
        fTokens.add(Token.START_TOKEN);
        while (src.length() > 0) {
            Token token = null;
            for (int i = 0; i < Token.FIXED_TOKENS.length && token == null; i++) {
                if (src.startsWith(Token.FIXED_TOKENS[i])) {
                    token = new Token(i, Token.FIXED_TOKENS[i]);
                }
            }
            if (token == null) {
                Matcher m = numberPattern.matcher(src);
                if (m.lookingAt()) {
                    token = new Token(Token.NUMBER, src.substring(m.start(), m.end()));
                }
            }
            if (token == null) {
                throw new Exception("Location description starts with unknown token: " + src);
            }
            src = src.substring(token.fValue.length());
            fTokens.add(token);
        }
        fTokens.add(Token.STOP_TOKEN);
    }

    void analyze() throws Exception {
        // get rid of outmost join/complement first. There are two cases:
        // 1. join(....)
        // 2. complement(join(....))
        boolean outerComplement = false;
        Token firstToken = (Token) fTokens.get(1);
        if (firstToken.fType == Token.JOIN) {
            // we can remove the outermost join, because we are joining anyway
            fTokens.remove(1); // JOIN
            fTokens.remove(1); // (
            fTokens.remove(fTokens.size() - 2); //)
        } else if (firstToken.fType == Token.COMPLEMENT) {
            outerComplement = true;
            if (((Token) fTokens.get(3)).fType == Token.JOIN) {
                fTokens.remove(1); // COMPLEMENT
                fTokens.remove(1); // (
                fTokens.remove(1); // JOIN
                fTokens.remove(01); // (
                fTokens.remove(fTokens.size() - 2); //)
                fTokens.remove(fTokens.size() - 2); //)
            }
        }

        // now, there are only regions left (possibly as complements):
        // 1. aaa..bbb,<ccc..ddd
        // 2. complement(aaa..bbb),complement(>ccc..ddd)
        // beware, this is also a "region":
        // 3. aaa

        fTokenIterator = fTokens.listIterator();
        nextToken();
        
        String limit = Position.EXACT;
        Position lpos = null;
        Position rpos = null;

        while (nextToken()) {
            Region region = analyzeRegion(outerComplement);
            fRegions.add(region);
            nextToken();
            if (fToken != null) {
                assertToken(Token.LIST_SEP);
            }
        }
        
        if (outerComplement) {
            Collections.reverse(fRegions);
        }
    }

    Region analyzeRegion(boolean outerComplement) throws Exception {
        boolean innerComplement = fToken.fType == Token.COMPLEMENT;
        if (innerComplement) {
            nextToken();
            assertToken(Token.LIST_OPEN);
            nextToken();
        }

        Position lPos = analyzePosition();
        nextToken();
        Position rPos;
        if (fToken != null && (fToken.fType == Token.POS_SEP || fToken.fType == Token.BETWEEN)) {
            nextToken();
            rPos = analyzePosition();
        } else {
            rPos = lPos;
            previousToken();
        }

        if (innerComplement) {
            nextToken();
            assertToken(Token.LIST_CLOSE);
        }
        return new Region(lPos, rPos, !(innerComplement || outerComplement));
    }

    Position analyzePosition() throws Exception {
        String limit = Position.EXACT;
        switch (fToken.fType) {
        case Token.GREATER:
        case Token.LESS:
            limit = fToken.fValue;
            nextToken();
            break;
        case Token.NUMBER:
            break;
        default:
            throw new Exception("Unexpected token: " + fToken);
        }
        assertToken(Token.NUMBER);
        return new Position(Integer.parseInt(fToken.fValue), limit);
    }

    void assertToken(int tokenType) throws Exception {
        if (fToken.fType != tokenType) {
            throw new Exception("Unexpected Token. Expected " + tokenType + " but found: " + fToken);
        }
    }

    boolean nextToken() {
        boolean answer = fTokenIterator.hasNext();
        if (answer) {
            fToken = (Token) fTokenIterator.next();
        } else {
            fToken = null;
        }
        if (fToken==Token.STOP_TOKEN) {
            fToken= null;
            answer= false;
        }
        return answer;
    }

    boolean previousToken() {
        boolean answer = fTokenIterator.hasPrevious();
        if (answer) {
            fToken = (Token) fTokenIterator.previous();
        } else {
            fToken = null;
        }
        if (fToken==Token.START_TOKEN) {
            fToken= null;
            answer= false;
        }
       return answer;
    }
}