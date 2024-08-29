package com.model2.mvc;
// W D 

import javax.servlet.http.HttpServletRequest;

public abstract class Debug {
	
	// Constructor
	public Debug() {
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
										daoName, methodName, params));
	}
	
	public static void printSQL(String sql) {
		System.out.println("\t\tSQL= "+sql);
	}
	
	public static void endDaoMethod () {
		System.out.println("\t;");
	}
	
	public static void printDataInDao(String dataName, Object data) {
		System.out.println(String.format("\t\t%s= %s",
										dataName, (data==null)? "null" : data.toString()));
	}
	
	public static void printDataInAction(String dataName, Object data) {
		System.out.println(String.format("\t%s= %s",
										dataName, data.toString()));
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
	
}
// class end
