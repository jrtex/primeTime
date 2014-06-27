package de.vahrson.util;

import java.util.*;
import java.io.*;
 
/**
 * Insert the type's description here.
 * Creation date: (27.10.99 09:35:10)
 * @author: 
 */
public class FileIterator implements Iterator{
	private LinkedList fFileList;
	private ListIterator fCursor;
	private FilenameFilter fFilter;
/**
 * FileIterator constructor comment.
 */
public FileIterator(File top) {
	this(top,null);
}
/**
 * FileIterator constructor comment.
 */
public FileIterator(File top, FilenameFilter filter) {
	super();
	fFileList= new LinkedList();
	fCursor= fFileList.listIterator();
	setFilter(filter);
	addOneFile(top);
}
/**
 * Insert the method's description here.
 * Creation date: (27.10.99 10:10:01)
 * @param f java.io.File
 */
public void addOneFile(File f) {
    fCursor.add(f);
    if (fCursor.hasPrevious()) {
        fCursor.previous(); // backup one, as add() moves forward
    }
}
/**
 * Insert the method's description here.
 * Creation date: (27.10.99 09:45:50)
 * @param top java.io.File
 */
protected void fillFileList(File top) {
    String[] names= top.list();
    if (names != null) {
        for (int i= 0; i < names.length; i++) {
            File f= new File(top, names[i]);
            if (fFilter.accept(f.getParentFile(), f.getName())) {
                addOneFile(f);
            }
        }
    }
}
/**
 * Insert the method's description here.
 * Creation date: (09.08.2001 20:44:25)
 * @return java.io.FileFilter
 */
public FilenameFilter getFilter() {
	return fFilter;
}
/**
 * Insert the method's description here.
 * Creation date: (27.10.99 09:56:16)
 * @return boolean
 */
public boolean hasNext() {
	return fCursor.hasNext();
}
/**
 * Insert the method's description here.
 * Creation date: (27.10.99 09:56:59)
 * @return java.lang.Object
 */
public Object next() {
	Object answer = fCursor.next();
	fCursor.remove(); // don't let the list grow infinitely
	if ( ((File)answer).isDirectory() ) {
		fillFileList((File)answer);
	}
	return answer;
}
/**
 * Remove not supported
 * Creation date: (27.10.99 09:57:21)
 */
public void remove() {
	throw new UnsupportedOperationException();
}
/**
 * Insert the method's description here.
 * Creation date: (09.08.2001 20:44:25)
 * @param newFilter java.io.FileFilter
 */
public void setFilter(FilenameFilter newFilter) {
	if (newFilter==null) {
		newFilter= new AcceptAll();
	}
	fFilter = newFilter;
}
}
