package com.aoyou.app.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package com.aoyou.test.util
 * @ClassName Regex
 * @Description 正则表达式处理模块
 * 3个公共接口方法：matchStringList/isFind/isMatch
 */

public class Regex {
	
	/**
	 *@param text:待查找的字符串
	 *@param regularExpression:正则表达式字符串
	 *@return 返回匹配的字符串数组，类型为ArrayList
	 */
	public static ArrayList<String> matchStringList(String text,String regularExpression,boolean subTheEnd){	
	Pattern p = Pattern.compile(regularExpression); 
	Matcher m = p.matcher(text);
	ArrayList<String> results = new ArrayList<String>();	
		while (m.find()) {  
			if(!subTheEnd){
			results.add(m.group());
			}else results.add(m.group().substring(0, m.group().length()-1));
	    }	
	return results; 	
	}
	
	public static String matchString(String text,String regularExpression){	
	Pattern p = Pattern.compile(regularExpression); 
	Matcher m = p.matcher(text);
		if (m.find()) {  			
			return m.group(0);
	    }else
	    	return null; 	
	}
	/**
	 * matchStringList重载
	 *@param subIndex:截取的索引
	 *@return 返回匹配的，已被截取过的字符串数组
	 */
	public static ArrayList<String> matchStringList(String text,String regularExpression,int subIndex){
	Pattern p = Pattern.compile(regularExpression); 
	Matcher m = p.matcher(text);
	ArrayList<String> results = new ArrayList<String>();
		while (m.find()) {  	
			results.add(m.group().substring(subIndex));
	    }	
	return results; 	
	}
	
	
	public static ArrayList<String> matchStringList(String[] text,String regularExpression,int subIndex){			
	Pattern p = Pattern.compile(regularExpression); 
	ArrayList<String> results = new ArrayList<String>();
		for(int i=0;i<text.length;i++){
			Matcher m = p.matcher(text[i]);		
				while (m.find()) {  	
					results.add(m.group().substring(subIndex));
				}
		}
		return results; 		
	}
	
	public static String matchString(String text,String regularExpression,int subIndex){	
	Pattern p = Pattern.compile(regularExpression); 
	Matcher m = p.matcher(text);
		if (m.find()) {  			
			return m.group(0).substring(subIndex);
	    }else
	    	return null; 	
	}
	
	/**
	 * 是否存在
	 * @return boolean
	 */
	public static boolean isFind(String text,String regularExpression){
	boolean falg = false;
	Pattern p = Pattern.compile(regularExpression); 
	Matcher m = p.matcher(text);
	
		if (m.find()) {  	
			falg=true;
	    }	
	return falg; 	
	}
	
	/**
	 * 是否匹配字符
	 * @return boolean
	 */
	public static boolean isMatch(String text,String regularExpression){
	boolean falg = false;
	Pattern p = Pattern.compile(regularExpression); 
	Matcher m = p.matcher(text);
	
		if (m.matches()) {  	
			falg=true;
	    }	
	return falg; 	
	}
	
}
