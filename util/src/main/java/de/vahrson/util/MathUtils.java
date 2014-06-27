package de.vahrson.util;

/**
 * Insert the type's description here.
 * Creation date: (08.10.2002 20:34:03)
 * @author: Wolfgang Vahrson
 */
public class MathUtils {
/**
 * MathUtils constructor comment.
 */
public MathUtils() {
	super();
}
/**
 * Answer a (pseudo-)random integer lo <= x < hi
 * Creation date: (08.10.2002 20:34:42)
 * @return int
 * @param low int
 * @param up int
 */
public static int random(int lo, int hi) {
	int len= hi-lo;
	int x= (int)(Math.random()*len);
	
	return lo+x;
}
/**
 * Insert the method's description here.
 * Creation date: (06.11.2002 12:32:29)
 * @return int
 * @param l long
 */
public static int sign(long l) {
	int answer= 0;
	if (l > 0) {
		answer= 1;
	}
	else if (l < 0) {
		answer= -1;
	}
	return answer;
}
}
