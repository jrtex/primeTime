package de.vahrson.util;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (08.11.2002 18:07:12)
 * @author: Wolfgang Vahrson
 */
public class TextUtils {
/**
 * TextUtil constructor comment.
 */
public TextUtils() {
	super();
}


/**
 * Insert the method's description here.
 * Creation date: (08.11.2002 19:00:40)
 */
public static boolean isEmptyString(Object o) {
	boolean answer= o == null;
	return answer || o.toString().length() == 0;
}


/**
 * Answer a text that enumerates the List, calling String.value on each element
 * Creation date: (08.11.2002 18:09:13)
 * @return java.lang.String
 * @param l java.util.List
 * @param separator java.lang.String
 * @param lastSeparator java.lang.String
 */
public static String textEnumerate(List fields, String separator, String lastSeparator) {
	List l= new NonEmptyStringFilter().filter(fields);
	StringBuffer answer= new StringBuffer();
	final int n= l.size();
	if (n == 0) {
	}
	else if (n == 1) {
		answer.append(String.valueOf(l.get(0)));
	}
	else {
		for (int i= 0; i < n - 2; i++) {
			answer.append(l.get(i));
			answer.append(separator);
		}
		answer.append(l.get(n - 2));
		answer.append(lastSeparator);
		answer.append(l.get(n - 1));
	}
	return answer.toString();
}
}