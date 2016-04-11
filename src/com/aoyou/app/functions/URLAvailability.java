package com.aoyou.app.functions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.aoyou.app.util.Log;



/** 
* 文件名称为：URLAvailability.java 
* 文件功能简述： 描述一个URL地址是否有效 
*  
*/  
public class URLAvailability {  
private static URL url;  
private static HttpURLConnection con;
private static HttpsURLConnection cons;  
private static int state = -1;  
  

/** 
 * 功能：检测当前URL是否可连接或是否有效, 
 * 描述：最多连接网络 N 次, 如果 N 次都不成功，视为该地址不可用 
 * @param urlStr 指定URL网络地址 
 * @param connectionNumber 尝试连接次数
 * @return URL 
 */  
public static synchronized  boolean isConnect(String urlStr,int connectionNumber) {  
	   boolean flag=true;
	   int counts = 0;  
	   if (urlStr == null) {    
		   flag = false;     
		   Log.logError("链接数组为空");
		   return flag;
	   }  
	   
	   while (counts < connectionNumber) {  
	    try {  
	    
	     url = new URL(urlStr);  
	     con = (HttpURLConnection) url.openConnection();  

	     state = con.getResponseCode();  

	     if (state == 200) {  
	    	 System.out.println(""+state);
	     }  
	     break;  
	    }catch (Exception e) {  
	     counts++;   
	     Log.logError("URL不可用，第" + counts + "次链接" + urlStr + "为"+state);  
	     flag=false;
	     continue;  
	    }  
	   }  
	   return flag;

}  






/** 
   * 功能：检测当前URL是否可连接或是否有效, 
   * 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用 
   * @param urlStr 指定URL网络地址 
   * @return URL 
   */  
public static synchronized  boolean isConnect(List<String> urlStr) {  
   boolean flag=true;
   int counts = 0;  
   if (urlStr == null || urlStr.size() <= 0) {    
	   flag = false;     
	   Log.logError("链接数组为空");
   }  
   
   for(int i=0;i<urlStr.size();i++){

   if(urlStr.get(i).equalsIgnoreCase(null)){
	   Log.logError(urlStr.get(i)+"为空链接");
	   flag = false;
   }
   while (counts < 3) {  
    try {  
    
     url = new URL(urlStr.get(i));  
     con = (HttpURLConnection) url.openConnection();  

     state = con.getResponseCode();  
//     System.out.println("第" + counts + "次链接" + urlStr.get(i) + "为"+state);  
     if (state == 200) {  
    	 ;
//      System.out.println("URL可用！");  
     }  
     break;  
    }catch (Exception e) {  
     counts++;   
     Log.logError("URL不可用，第" + counts + "次链接" + urlStr.get(i) + "为"+state);  
     //urlStr = null;  
     flag=false;
     continue;  
    }  
   }  

   }
   return flag;  
}  



public static synchronized  boolean isConnect(String[] urlStr) {  
	   boolean flag=true;
	   int counts = 0;  
	   if (urlStr == null || urlStr.length <= 0) {    
		   flag = false;     
		   Log.logError("链接数组为空");
	   }  
	   
	   for(int i=0;i<urlStr.length;i++){
	   if(urlStr[i].equalsIgnoreCase(null)){
		   Log.logError(urlStr[i]+"为空链接");
		   flag = false;
	   }
	   while (counts < 5) {  
	    try {  
	    
	     url = new URL(urlStr[i]);  
	     con = (HttpURLConnection) url.openConnection();  
	     state = con.getResponseCode();  
//	     System.out.println("第" + counts + "次链接" + urlStr.get(i) + "为"+state);  
	     if (state == 200) {  
	    	 ;
//	      System.out.println("URL可用！");  
	     }  
	     break;  
	    }catch (Exception e) {  
	     counts++;   
	     Log.logError("URL不可用，第" + counts + "次链接" + urlStr[i] + "为"+state);  
	     //urlStr = null;  
	     flag=false;
	     continue;  
	    }  
	   }  

	   }
	   return flag;  
	}



public static synchronized  boolean httpsIsConnect(List<String> urlStr) {  
	   boolean flag=true;
	   int counts = 0;  
	   if (urlStr == null || urlStr.size() <= 0) {    
		   flag = false;     
		   Log.logError("链接数组为空");
	   }  
	   
	   for(int i=0;i<urlStr.size();i++){

	   if(urlStr.get(i).equalsIgnoreCase(null)){
		   Log.logError(urlStr.get(i)+"为空链接");
		   flag = false;
	   }
	   while (counts < 3) {  
	    try {  
	    
	     url = new URL(urlStr.get(i));  
	     cons = (HttpsURLConnection) url.openConnection();  

	     state = cons.getResponseCode();  
//	     System.out.println("第" + counts + "次链接" + urlStr.get(i) + "为"+state);  
	     if (state == 200) {  
	    	 ;
//	      System.out.println("URL可用！");  
	     }  
	     break;  
	    }catch (Exception e) {  
	     counts++;   
	     Log.logError("URL不可用，第" + counts + "次链接" + urlStr.get(i) + "为"+state);  
	     //urlStr = null;  
	     flag=false;
	     continue;  
	    }  
	   }  

	   }
	   return flag;  
	}  


public static synchronized  boolean httpsIsConnect(String[] urlStr) {  
	   boolean flag=true;
	   int counts = 0;  
	   if (urlStr == null || urlStr.length <= 0) {    
		   flag = false;     
		   Log.logError("链接数组为空");
	   }  
	   
	   for(int i=0;i<urlStr.length;i++){
	   if(urlStr[i].equalsIgnoreCase(null)){
		   Log.logError(urlStr[i]+"为空链接");
		   flag = false;
	   }
	   while (counts < 5) {  
	    try {  
	    
	     url = new URL(urlStr[i]);  
	     cons = (HttpsURLConnection) url.openConnection();  
	     state = cons.getResponseCode();  
//	     System.out.println("第" + counts + "次链接" + urlStr.get(i) + "为"+state);  
	     if (state == 200) {  
	    	 ;
//	      System.out.println("URL可用！");  
	     }  
	     break;  
	    }catch (Exception e) {  
	     counts++;   
	     Log.logError("URL不可用，第" + counts + "次链接" + urlStr[i] + "为"+state);  
	     //urlStr = null;  
	     flag=false;
	     continue;  
	    }  
	   }  

	   }
	   return flag;  
	}




}  
