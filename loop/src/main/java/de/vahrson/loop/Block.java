/*
 * Created on 24.03.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/loop/Block.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.loop;

/**
 * A code block, not quite as in SmallTalk...
 * @author wova
 *
 */
public interface Block {
	Object execute() throws Exception;
	Object execute(Object arg1) throws Exception;
	Object execute(Object arg1, Object arg2) throws Exception;
	
	boolean test() throws Exception;
	boolean test(Object arg1) throws Exception;
	boolean test(Object arg1, Object arg2) throws Exception;
}
