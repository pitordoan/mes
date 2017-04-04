/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     pitor - initial implementation
 ***********************************************************************************/

package com.cimpoint.mes.common;

import java.util.Collection;
import java.util.Date;

import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.i18n.client.DateTimeFormat;

public class MESUtils {

	public static MESTrxInfo newTrxInfo(String comment) {
		MESTrxInfo ti = new MESTrxInfo();
		ti.setComment(comment);
		ti.setTime(new Date());
		ti.setApplicationName("MES");		
		String timezone = "UTC-6h"; //TODO fix me, both DateTimeFormat and JsDate don't work
		ti.setTimeZoneId(timezone);
		
		return ti;
	}
	
	public static <T extends Enum<T>> String[] enumNameToStringArray(T[] values) {   
	    int i = 0;   
	    String[] result = new String[values.length];   
	    for (T value: values) {   
	        result[i++] = value.name();   
	    }   
	    return result;   
	}   
	  
	public static <E> String[] getArrayFromCollection(Collection<E> collection){
		int size = collection.size();
		Object[] objs = collection.toArray();
		String[] result = new String[size];
		for (int i = 0; i < size; i++) {
			result[i] = objs[i].toString();
		}
		return result;
	}
	
	public static class Dates {

		public static final String DateTime = "MM/dd/yyyy hh:mm:ss a";
		public static final String Date = "MM/dd/yyyy";
		
		 /**
	     * Return a String from date and format
	     *
	     * @param date, data format
	     * @return a String
	     */
	    public static String toString(Date date, String format) {
	    	return DateTimeFormat.getFormat(format).format(date);
	    }
	    
	    /**
	     * Return a String from date
	     *
	     * @param date
	     * @return a String
	     */
	    public static String toString(Date date){
			String strCreationTime = "";
			if(date != null){
				strCreationTime = toString(date, DateTime);
			}
			return strCreationTime;
	    }

	}
	
	public static class Strings {

		 /**
	     * Return a String from String
	     *
	     * @param string
	     * @return a String
	     */
	    public static String value(String string) {
	    	return string == null ? "" : string;
	    }

		 /**
	     * Return a boolean
	     *
	     * @param string
	     * @return a boolean
	     */
	    public static boolean isEmptyString(String string) {
	    	if(string == null || string.trim().equals(""))
	    		return true;
	    	return false;
	    }
	    
	    
	}
}
