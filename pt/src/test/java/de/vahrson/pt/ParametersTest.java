/*
 * Created on 03.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/ParametersTest.java,v 1.2 2005/03/18 23:26:15 wova Exp $
 */
package de.vahrson.pt;

import java.util.*;

import de.vahrson.pt.om.Parameter;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ParametersTest extends TestCase {
	public void testNameSpace() throws Exception{
		Parameters tested= new Parameters(createParameterList(), createDefaultParameterList());
		tested.setNameSpace("primerfinder");
		
		assertEquals("1",tested.get("xxx.yyy"));
		assertEquals("1",tested.get("primerFinder.xxx.yyy"));
		assertEquals("2",tested.get("primerfinder.aaa.ddd"));
		
	}
	
	public void testClip() throws Exception{
		Parameters tested= new Parameters(createParameterList(), createDefaultParameterList());
		tested.setNameSpace("primerfinder");
		Properties p= tested.createPropertiesWithClippedKeys("XXX.");
		assertEquals("1",p.getProperty("yyy"));
	}
	
	List createParameterList() throws Exception{
		List answer= new ArrayList();
		answer.add(createParameter("primerFinder.xxx.yyy", "1"));
		answer.add(createParameter("Primerfinder.xXx.zzz", "1"));
		answer.add(createParameter("primerfinder.aaa.bBb", "1"));
		return answer;
	}
	
	List createDefaultParameterList() throws Exception{
		List answer= new ArrayList();
		answer.add(createParameter("primerFinder.xxx.yyy", "2"));
		answer.add(createParameter("Primerfinder.xXx.zzz", "2"));
		answer.add(createParameter("primerfinder.aaa.bBb", "2"));
		answer.add(createParameter("primerfinder.aaa.ccc", "2"));
		answer.add(createParameter("primerfinder.aaa.ddd", "2"));
		return answer;
	}
	
	Parameter createParameter(String key, String value) throws Exception{
		Parameter p= new Parameter();
		p.setParameterKey(key);
		p.setParameterValue(value);
		return p;
	}
	
	public void testFilter() throws Exception{
		Parameters.PrefixFilter tested= new Parameters.PrefixFilter("primerFinder");
		assertTrue( tested.accept(createParameter("primerFinder.xxx.yyy", "2")));
		assertTrue( !tested.accept(createParameter("ZZZ.xxx.yyy", "2")));
	}
}
