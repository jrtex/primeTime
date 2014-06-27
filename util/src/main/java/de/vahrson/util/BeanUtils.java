package de.vahrson.util;

import java.lang.reflect.*;
import java.net.URLEncoder;
import org.apache.log4j.Logger;

/**
 * Insert the type's description here.
 * Creation date: (12.03.2002 21:19:33)
 * @author: 
 */
public class BeanUtils {
	private static Logger log= Logger.getLogger(BeanUtils.class);

/**
 * BeanUtils constructor comment.
 */
public BeanUtils() {
	super();
}


/**
 * Insert the method's description here.
 * Creation date: (17.03.2002 13:51:38)
 */
public static void appendAsURLParameter(StringBuffer answer, String key, Object value) {
	answer.append(key);
	answer.append("=");
	answer.append(URLEncoder.encode(String.valueOf(value)));
}


/**
 * Answer the properties of a bean in URLEncoded form
 * rw_only: only list r/w properties
 * Creation date: (12.03.2002 21:20:04)
 * @return java.lang.String
 * @param bean java.lang.Object
 */
public static String asURL(Object bean) {
	StringBuffer answer= new StringBuffer();
	Class c= bean.getClass();
	Method[] methods= c.getMethods();
	final int n= methods.length;
	int count= 0;
	for (int i= 0; i < n; i++) {
		Method m= methods[i];
		if (isGetter(m) && !isSuppressedMethod(m)) {
			Object value;
			try {
				Object o= m.invoke(bean, new Object[0]);
				if (o != null) {
					value= o;
				}
				else {
					value= null;
				}
			}
			catch (InvocationTargetException e) {
				value= null;
				DefaultErrorHandler.handleError("BeanUtils.asURL 1", e);
			}
			catch (IllegalAccessException iae) {
				value= null;
				DefaultErrorHandler.handleError("BeanUtils.asURL 2", iae);
			}
			if (value != null) {
				String key= getterAsProperty(m);
				if (value.getClass().isArray()) {
					Object[] values= (Object[]) value;
					final int l= values.length;
					for (int k= 0; k < l; k++) {
						if (count > 0) {
							answer.append("&");
						}
						appendAsURLParameter(answer, key, values[k]);
						count++;
					}
				}
				else {
					if (count > 0) {
						answer.append("&");
					}
					appendAsURLParameter(answer, key, value);
					count++;
				}
			}
		}
	}

	return answer.toString();
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.2002 21:35:05)
 * @return java.lang.String
 * @param m java.lang.reflect.Method
 */
public static String getterAsProperty(Method m) {
	StringBuffer answer= new StringBuffer(m.getName().substring(3));
	answer.setCharAt(0, Character.toLowerCase(answer.charAt(0)));
	return answer.toString();
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.2002 21:30:49)
 * @return boolean
 * @param m java.lang.reflect.Method
 */
public static boolean isGetter(Method m) {
	return m.getName().startsWith("get") && m.getParameterTypes().length==0;
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.2002 21:30:49)
 * @return boolean
 * @param m java.lang.reflect.Method
 */
public static boolean isSetter(Method m) {
	return m.getName().startsWith("set");
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.2002 22:01:56)
 * @return boolean
 * @param m java.lang.reflect.Method
 */
public static boolean isSuppressedMethod(Method m) {
	return m.getName().equals("getClass");
}


/**
 * Answer the corresponding method name for the property
 * Creation date: (13.06.2002 10:40:17)
 * @return java.lang.String
 * @param property java.lang.String
 */
public static String propertyToSetterName(String property) {
	return "set" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
}

/**
 * Answer the corresponding method name for the property
 * Creation date: (13.06.2002 10:40:17)
 * @return java.lang.String
 * @param property java.lang.String
 */
public static String propertyToGetterName(String property) {
	return "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
}


/**
 * Try to set property on bean to value. 
 * Answer whether successful or not
 * Silently ignores Exceptions
 * 
 * Creation date: (13.06.2002 10:36:46)
 * @param bean java.lang.Object
 * @param property java.lang.String
 * @param Value java.lang.String
 */
public static boolean set(Object bean, String property, String value) {
	boolean answer= false;
	String mName= propertyToSetterName(property);
	Class bclass= bean.getClass();
	Class[] argumentClasses= { String.class };
	try {
		Method m= bclass.getMethod(mName, argumentClasses);
		Object[] arguments= { value };
		m.invoke(bean, arguments);
		answer= true;
	}
	catch (NoSuchMethodException nsm) {
		log.debug("BeanUtils.set No such method: " + mName +" (This is usually OK).");
	}
	catch (Exception e) {
		DefaultErrorHandler.handleError("BeanUtils.set", e);
	}
	return answer;

}
/**
 * Try to set property on bean to value. 
 * Answer whether successful or not
 * Silently ignores Exceptions
 * 
 * Creation date: (13.06.2002 10:36:46)
 * @param bean java.lang.Object
 * @param property java.lang.String
 * @param Value java.lang.String
 */
public static Object get(Object bean, String property) {
	Object answer= null;
	String mName= propertyToGetterName(property);
	Class bclass= bean.getClass();
	Class[] argumentClasses= { };
	try {
		Method m= bclass.getMethod(mName, argumentClasses);		
		answer= m.invoke(bean, null);
	}
	catch (NoSuchMethodException nsm) {
		log.debug("BeanUtils.get No such method: " + mName +" (This is usually OK).");
	}
	catch (Exception e) {
		DefaultErrorHandler.handleError("BeanUtils.get", e);
	}
	return answer;

}
}