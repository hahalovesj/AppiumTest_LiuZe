package com.aoyou.app.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
* @ClassName: HandleString 
* @Description: 字符串处理模块
* 3个公共接口方法：subTextString/toLowerCaseFirstOne/toUpperCaseFirstOne
* @date 2015年6月9日 下午5:50:43
*/
public class HandleString {
	
	/**
	* @Description 将字符串text中以startString开头，endString结尾的所有匹配字符放在一个字符串数组中返回
	* @param text：待处理的字符串
	* @param startString：搜索起始字符串
	* @param endString：搜索结尾字符串
	* @return String[] 符合条件的字符串数组
	*/
	public static String[] subTextString(String text,String startString,String endString){

		String[]Text = text.split(startString);
		String []finalText = new String[Text.length-1];

		for (int i=1;i<Text.length;i++){		
			finalText[i-1] = Text[i].substring(0, Text[i].indexOf(endString)).trim();
		}	
		return finalText;
	}
	
	/**
	* @Description 首字母转小写
	* @param String：待处理的字符串
	* @return String
	*/
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
	/**
	* @Description 首字母转大写
	* @param String：待处理的字符串
	* @return String
	*/
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	
	public static Object[] swtichMapKeyToArray(Map<String, String> map){
		return map.keySet().toArray();
	}
	
	 /**
		* @Description 把字符串数组String[]转成List<String>
		* @param String[]：待处理的字符串数组
		* @return List<String>
		*/
	    public static List<String> stringArrayToList(String[] str){
	    List<String> list = new ArrayList<String>();
		for(int i=0;i<str.length;i++){
			if(str[i].startsWith("http"))
			list.add(str[i]);
		}
		   return list; 
	    }
	
		 /**
			* @Description 把字符串数组String[]转成String(中间添加分隔符)
			* @param String[]：待处理的字符串数组
			* @param separator：字符间的自定义分隔符
			* @return String
			*/
	    
		public static String stringArrayToString(String[] str,String separator){
	    StringBuffer sb = new StringBuffer();
		for(String s:str){
			sb.append(s+separator);
		}
		   return sb.toString(); 
	    }
	    
	    public static List<String> handleUrlForList(List<String> list){
		    List<String> url = new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				if(list.get(i).startsWith("http"))
				url.add(list.get(i));
			}
			   return url; 
		    }
	    
	    public static List<String> handlePriceForList(List<String> list,int subIndex){			
	    	
	    	String[] str= new String[list.size()];
	    		for(int i=0;i<list.size();i++){
	    			str[i] = list.get(i).substring(subIndex);
	    		}
	    		list= Arrays.asList(str);
	    		return list; 		
	    	}
	    
}