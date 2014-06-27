package de.vahrson.util;
import java.io.*;

/**
 * Read tabbed input.
 * Creation date: (13.11.02 21:12:03)
 * @author: wv
 */
public class TabReader {
	private static final String COMMENT_DELIMITER= "#";
	private static final String FIELD_DELIMITER= "\t{1}";
	private static final String MULTI_FIELD_DELIMITER= ",";

	private Reader fOriginalReader;
	private BufferedReader fReader;


/**
 * TabReader constructor comment.
 */
public TabReader(Reader r) {
	super();
	fOriginalReader= r;
}


/**
 * Insert the method's description here.
 * Creation date: (13.11.02 21:14:34)
 */
public void close() throws IOException{
	fReader.close();
	fReader= null;
}


/**
 * Insert the method's description here.
 * Creation date: (15.11.02 17:14:34)
 * @param tabDB java.io.File
 * @param sink de.vahrson.reservation.db.FieldWorker
 * @exception java.io.IOException The exception description.
 */
public static void doWithReader(Reader tabDB, FieldWorker sink) throws Exception {
	TabReader r= new TabReader(tabDB);
	r.open();
	String[] fields= r.next();
	while (fields != null) {
		sink.doWithFields(fields);
		fields= r.next();
	}
	r.close();

}

/**
 * Insert the method's description here.
 * Creation date: (17.01.03 16:27:05)
 * @return java.lang.String
 * @param fields java.lang.String[]
 */
public static String joinMultiFields(String[] fields) {
	StringBuffer answer= new StringBuffer();
	if (fields.length > 0) {
		answer.append(fields[0]);

		for (int i= 1; i < fields.length; i++) {
			answer.append(MULTI_FIELD_DELIMITER);
			answer.append(fields[i]);
		}
	}

	return answer.toString();
}


/**
 * Answer fields corresponding to the next line in input
 * Skip empty lines and comments
 * Creation date: (13.11.02 21:15:24)
 * @return java.lang.Object[]
 */
public String[] next() throws IOException {
	String[] answer= null;
	String line= fReader.readLine();
	while (line != null && (line.length() == 0 || line.startsWith(COMMENT_DELIMITER))) {
		line= fReader.readLine();
	}
	if (line != null) {
		answer= line.split(FIELD_DELIMITER);
	}

	return answer;
}


/**
 * Insert the method's description here.
 * Creation date: (13.11.02 21:14:18)
 */
public void open() throws IOException {
	fReader= new BufferedReader(fOriginalReader);
}


/**
 * Insert the method's description here.
 * Creation date: (13.11.02 21:21:03)
 * @return java.lang.String[]
 * @param f java.lang.String
 */
public static String[] splitMultiField(String f) {
	return f.split( MULTI_FIELD_DELIMITER);
}
}