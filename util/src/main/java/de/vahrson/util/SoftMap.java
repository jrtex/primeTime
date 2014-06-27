/*
 * Created on 24.06.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.vahrson.util;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * @author wova
 *
 * This map keeps soft references to its values -- they may be reclaimed
 * by the gc when memory is low. It is possible that get() retruns null even when 
 * a value was stored there before.
 */
public class SoftMap extends HashMap {

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object key) {
		Reference ref= (Reference)super.get(key);
		return ref == null ? null : ref.get();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
		Reference ref= new SoftReference(value);
		return super.put(key, ref);
	}

}
