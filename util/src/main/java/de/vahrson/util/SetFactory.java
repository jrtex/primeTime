package de.vahrson.util;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (31.10.2002 13:55:41)
 * @author: Wolfgang Vahrson
 */
public class SetFactory {
/**
 * SetFactory constructor comment.
 */
public SetFactory() {
	super();
}


/**
 * Create a Set containing 1 element 
 * Creation date: (31.10.2002 13:56:16)
 * @return java.util.Set
 * @param o1 java.lang.Object
 */
public static Set create(Object o1) {
	Set answer= new HashSet();
	answer.add(o1);
	return answer;
}


/**
 * Create a Set containing 2 elements 
 * Creation date: (31.10.2002 13:56:16)
 * @return java.util.Set
 * @param o1 java.lang.Object
 */
public static Set create(Object o1, Object o2) {
	Set answer= new HashSet();
	answer.add(o1);
	answer.add(o2);
	return answer;
}


/**
 * Create a Set containing 3 elements 
 * Creation date: (31.10.2002 13:56:16)
 * @return java.util.Set
 * @param o1 java.lang.Object
 */
public static Set create(Object o1, Object o2, Object o3) {
	Set answer= new HashSet();
	answer.add(o1);
	answer.add(o2);
	answer.add(o3);
	return answer;
}
}