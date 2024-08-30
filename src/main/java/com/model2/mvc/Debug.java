package com.model2.mvc;
// W D 

import javax.servlet.http.HttpServletRequest;

import com.model2.mvc.common.util.CommonUtil;

public abstract class Debug {
	
	// Constructor
	private Debug() {
	}
	
	
	// Field
	private static String daoName;
	private static String serviceName;

	public static void setDaoName(String daoName) {
		Debug.daoName = daoName;
	}
	
	public static void setServiceName(String serviceName) {
		Debug.serviceName = serviceName;
		Debug.serviceConstruct();
	}

	// Method
	public static void daoConstruct (String daoName) {
		setDaoName(daoName);
		System.out.println("\n-- "+daoName);
	}
	
	public static void daoConstruct() {
		System.out.println("\n-- "+daoName);
	}
	
	public static void serviceConstruct() {
		System.out.println("\n++ "+serviceName);
	}
	
	public static void startDaoMethod (String methodName, String params) {
		System.out.println(String.format("\n\t%s().%s(%s)\n", 
										daoName, methodName, CommonUtil.null2str(params)));
	}
	
	public static void printSQL(String sql) {
		System.out.println("\t\tSQL= "+sql);
	}
	
	public static void endDaoMethod () {
		System.out.println("\t;");
	}
	
	// Dao에서 T2
	public static void printDataT2(String dataName, Object data) {
		System.out.println(String.format("\t\t%s= %s",
										dataName, null2str(data)));
		
	}
	
	// Action에선 T1
	public static void printDataT1(String dataName, Object data) {
		System.out.println(String.format("\t%s= %s",
										dataName, null2str(data)));
	}
	
	public static String null2str(Object obj) {
		return (obj==null)? "" : obj.toString();
	}
	
	public static void startAction(String actionName) {
		System.out.println("\n>> "+actionName);
	}
	
	public static void endAction() {
		System.out.println(";");
	}
	
	public static String getParamStr(HttpServletRequest request, String paramName) {
		String paramValue = request.getParameter(paramName);
		System.out.println(String.format("\t%s= %s", 
										paramName, (paramValue==null)? "" : paramValue));
		
		return paramValue;
	}
	
	public static int getParamInt(HttpServletRequest request, String paramName) {
		String paramValue = request.getParameter(paramName);
		int intValue = (paramValue==null || paramValue.equals(""))? 0 : Integer.parseInt(paramValue);
		System.out.println(String.format("\t%s= %d", 
										paramName, intValue));
		
		return intValue;
	}
	
	public static int getPage(HttpServletRequest request, String paramName) {
		String paramValue = request.getParameter(paramName);
		int intValue = (paramValue==null || paramValue.equals(""))? 1 : Integer.parseInt(paramValue);
		System.out.println(String.format("\t%s= %d", 
										paramName, intValue));
		
		return intValue;
	}
	
	public static String[] getParamValues(HttpServletRequest request, String paramName) {
		String[] paramValues = request.getParameterValues(paramName);
		System.out.println(String.format("\t%s= %s", 
										paramName, paramValues));
		
		return paramValues;
	}
	
	public static void startJsp(String jspName) {
		System.out.println("\n:: "+jspName+".jsp");
	}
	
	public static void endJsp() {
		System.out.println(";");
	}
	
	public static String str2date(String str) {
		str = CommonUtil.null2str(str);
		str = str.split(" ")[0];
		
		String dateStr = "";
		
		for (String s : str.split("-")) {
			dateStr += s;
		}
		
		return dateStr;
	}
	
}
// class end
