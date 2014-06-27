package de.vahrson.util;
/**
 * A FieldWorker works on the fields that are passed to it
 * for example by a TabReader. Allows Awk-style processing of files
 * Creation date: (15.11.02 17:11:32)
 * @author: 
 */
public interface FieldWorker {
/**
 * Insert the method's description here.
 * Creation date: (15.11.02 17:13:21)
 * @param fields java.lang.String[]
 */
void doWithFields(String[] fields) throws Exception;
}