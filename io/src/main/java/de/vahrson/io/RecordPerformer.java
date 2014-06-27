/*
 * Created on 06.02.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/io/RecordPerformer.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.io;


public interface RecordPerformer {
	void perform(String line) throws Exception;
	String describe();
	void end()throws Exception;
}