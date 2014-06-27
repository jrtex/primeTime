/*
 * Created on 19.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/WsDbFetchClient.java,v 1.2 2005/02/22 12:22:39 wova Exp $
 */
package de.vahrson.rusa;

import java.util.*;

/**
 * @author wova
 *
 * Client for the EBI wSDbFetch web service
 * Modelled after DbfetchClient.java by Sharmila Pillai
 */
//
//  DbfetchClient.java
//  
//
//  Created by Sharmila Pillai on Tue Jun 18 2002.
//  Copyright (c) 2003 EBI. All rights reserved.
//
import org.apache.axis.client.*;

import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Set;
import java.io.*;
import java.net.MalformedURLException;

import javax.activation.DataHandler;
import org.apache.axis.AxisFault;

import org.apache.axis.MessageContext;
import org.apache.axis.encoding.XMLType;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.namespace.QName;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.axis.encoding.ser.JAFDataHandlerSerializerFactory;
import org.apache.axis.encoding.ser.JAFDataHandlerDeserializerFactory;
import org.apache.log4j.Logger;

/**
 * Client for WS-Dbfetch webservice.
 * 
 * @author Sharmila Pillai <sharmila@ebi.ac.uk>
 *  
 */

public class WsDbFetchClient implements DbFetcher {
    private static Logger log = Logger.getLogger(WsDbFetchClient.class);

    public void fetch(Usa usa, Writer out) throws Exception {
        Call call = createServiceCall();
        String[] result = (String[])call.invoke(new Object[] {usa.getDb() + ":" + usa.getId(), usa.getFormat(), "raw" });
        examineCallResult(result);
        BufferedWriter w= new BufferedWriter(out);
        for (int i=0; i<result.length; i++) {
            w.write(result[i]);
            w.write('\n');
        }
        w.flush();
    }

    /**
     * @param ret
     * @throws AxisFault
     */
    private void examineCallResult(Object ret) throws AxisFault {
        if (null == ret) {
            log.warn("Received null ");
            throw new AxisFault("", "Received null", null, null);
        }
        if (ret instanceof String) {
            log.warn("Received problem response from server: " + ret);
            throw new AxisFault("", (String) ret, null, null);
        }
    }

    /**
     * @return
     * @throws ServiceException
     * @throws MalformedURLException
     */
    private Call createServiceCall() throws ServiceException, MalformedURLException {
        Call call = (Call) new Service().createCall();
        call.setTargetEndpointAddress(new java.net.URL("http://www.ebi.ac.uk/ws/services/Dbfetch"));

        call.setOperationName (new QName("urn:Dbfetch", "fetchData"));
        call.addParameter( "query", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter( "format", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter( "style", XMLType.XSD_STRING, ParameterMode.IN);
        call.setReturnType(XMLType.SOAP_ARRAY);
        
        return call;
    }
}