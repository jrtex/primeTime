/*
 * Created on 13.03.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/ReflectionUtils.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.util;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReflectionUtils {


	public static Method findMethodCaseInsensitive(Method[] methods, String name, int nArgs) {
		Method found= null;
		name= name.toLowerCase();
		for (int m= 0; m < methods.length && found == null; m++) {
			if (methods[m].getName().toLowerCase().equals(name) && methods[m].getParameterTypes().length == nArgs) {
				found= methods[m];
			}
		}
		return found;
	}
	
	
	private static Map gWrappers= new HashMap();
	static {
		gWrappers.put("long", Long.class);
		gWrappers.put("int", Integer.class);
		gWrappers.put("short", Short.class);
		gWrappers.put("char", Character.class);
		gWrappers.put("byte", Byte.class);
		gWrappers.put("boolean", Boolean.class);
	}
	
	public static Class wrapperForPrimitiveType(String typeName) {
		return (Class) gWrappers.get(typeName);
	}

}
