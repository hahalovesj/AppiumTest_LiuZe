package com.aoyou.app.base;

import io.appium.java_client.AppiumDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aoyou.app.util.ParseXml;

/* 框架代码中用的公共数据模块
 * 
 * 
 * */ 

public class Data {
	
	public static Map<String,String> globalMap = new HashMap<String,String>();
	public static List<String> reporterLog = new ArrayList<String>();
	public static Map<String,AppiumDriver> driverMap = new HashMap<String,AppiumDriver>();
	static{	
		ParseXml px = new ParseXml("data/global.xml");
		globalMap=px.getChildrenInfo("/data");
	}
	
}



