package com.aoyou.app.util;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
* @Package com.aoyou.test.util
* @ClassName CheckPoint 
* @Description 检查点模块
* 
* 7个公共接口方法：equals/notEquals/startWith/contains/result/isFaild/isSuccess
* 
*/

public class CheckPoint {
	
	private int flag;
	private ScreenShot screenShot;
	@SuppressWarnings("unused")
	private WebDriver driver;
	private String currentClassName;
	/**
	 * 实例化ScreenShot对象
	 * 初始化 标志位
	 */
	public CheckPoint(WebDriver driver){
		this.driver=driver;
		flag=0;
		screenShot = new ScreenShot(driver);
	}
	
	public CheckPoint(WebDriver driver,String currentClassName){
		this.driver=driver;
		this.currentClassName=currentClassName;
		flag=0;
		screenShot = new ScreenShot(driver);
	}
	
	
	/**
	* @Description 等值对比，false检查点标志位+1
	* @param message:值不相等时，需要记录到日志的错误内容
	*/
	public void equals(Object actual,Object expected,String message){
		try{
			Assert.assertEquals(actual, expected);
		}catch(Error e){
			flag=flag+1;
			Log.logError("["+currentClassName+"]"+message);		
			screenShot.takeScreenshot();
		}
	}

	public void equals(boolean actual,boolean expected,String message){
		try{
			Assert.assertEquals(actual, expected);
		}catch(Error e){
			flag=flag+1;
			Log.logError("["+currentClassName+"]"+message);
			screenShot.takeScreenshot();
		}
	}
	
	public void equals(boolean actual,boolean expected){
		try{
			Assert.assertEquals(actual, expected);
		}catch(Error e){
			flag=flag+1;
		}
	}
		
	public void equals(String actual,String expected,String message){
		try{
			Assert.assertEquals(actual, expected);

		}catch(Error e){
			flag=flag+1;
			Log.logError("["+currentClassName+"]"+message);
			screenShot.takeScreenshot();
		}		
	}
	
	public void equals(int actual,int expected,String message){
		try{
			Assert.assertEquals(actual, expected);
		}catch(Error e){
			flag=flag+1;
			Log.logError("["+currentClassName+"]"+message);	
			screenShot.takeScreenshot();
		}				
	}
	
	public void equals(int actual,int expected){
		try{
			Assert.assertEquals(actual, expected);
		}catch(Error e){
			flag=flag+1;			
		}				
	}
	
	/**
	* @Description 不等值对比，false检查点标志位+1
	* @param message:值不相等时，需要记录到日志的错误内容
	*/
	public void notEquals(String actual,String expected,String message){
		
		if 	(actual.equals(expected)){
			Log.logError("["+currentClassName+"]"+message);
			flag=flag+1;
		}	
		
	}
	
	public void notEquals(int actual,int expected,String message){
		
		if 	(actual == expected){
			Log.logError("["+currentClassName+"]"+message);
			flag=flag+1;
			screenShot.takeScreenshot();
		}	
		
	}
	
	public void notEquals(String[]actual,String expected){
		if(actual.length!=0){	
			for(int i=0;i<actual.length;i++){				
				this.notEquals(actual[i], expected, actual[i]+"异常！");
			}
		}else this.isFaild("数组对象为空！");
	}
						
	public void notEquals(ArrayList<String>actual,String expected){
		if(actual.size()!=0){
			for(int i=0;i<actual.size();i++){			
				this.notEquals(actual.get(i), expected, actual.get(i)+"异常！");
			}
		}else this.isFaild("数组对象为空！");
	}
	
	public void notEquals(List<String>actual,String expected){
		if(actual.size()!=0){
			for(int i=0;i<actual.size();i++){			
				this.notEquals(actual.get(i), expected, actual.get(i)+"异常！");
			}
		}else this.isFaild("数组对象为空！");
	}
	
	/** 
	* @Description 判断actual字符串是否以完整的expected开始。false检查点标志位+1
	*/
	public void startWith(String actual,String expected,String message){
		
		if (!actual.startsWith(expected)){			
			Log.logError("["+currentClassName+"]"+message);
			flag=flag+1;
			screenShot.takeScreenshot();
		}			
	}
	
	public void startWith(String[] actual,String expected){
		
		for(String act : actual )			
			if (!act.startsWith(expected)){				
				Log.logError("["+currentClassName+"] "+act+"异常！");
				flag=flag+1;
			}			
	}
	
	public void startWith(String[] actual,String[] expected){
		
		if (actual.length==expected.length){			
			for(int i=0;i<actual.length;i++){				
				if (!actual[i].startsWith(expected[i])){	
					Log.logError("["+currentClassName+"] "+"异常："+actual[i]+expected[i]);
					flag=flag+1;
					}	
			}	
		}else{
			Log.logError("["+currentClassName+"] "+"数组长度不等，检查页面对象个数！");
		}
	}
	
	/**
	* @Description 判断actual字符串数组中的每一个值，是否都包含expected字符 。false检查点标志位+1
	*/
	public void contains(String[] actual,String expected,String message){
		if(actual.length!=0){
		for(String act : actual )			
			if (!act.contains(expected)){			
				Log.logError("["+currentClassName+"] "+message+" 【异常】"+act);
				flag=flag+1;
			}
		}else this.isFaild("数组对象为空！");
	}
	
	public void contains(String[] actual,String expected1,String expected2,String message ){
		if(actual.length!=0){
		for(String act : actual )			
			if (!act.contains(expected1) && !act.contains(expected2) ){			
				Log.logError("["+currentClassName+"] "+message+" 【异常】"+act);
				flag=flag+1;
			}
		}else this.isFaild("数组对象为空！");
	}
	
	/**
	* @Description 判断actual字符，是否都包含expected字符 。false检查点标志位+1
	*/
	public void contains(String actual,String expected,String message){
				
			if (!actual.contains(expected)){			
				Log.logError("["+currentClassName+"] "+message);
				flag=flag+1;
				screenShot.takeScreenshot();
			}
		}
	
	/**
	* @Description 当case中需要用到if语句检查结果，错误时使用该检查点
	* @param message：错误时需要记录到日志的信息
	*/
	public void isFaild(String message){
		this.equals(true, false, "["+currentClassName+"]"+message);
	}
	
	/**
	* @Description 当case中需要用到if语句检查结果，pass时强制使用该检查点
	* @param message：pass时需要记录到日志的信息
	*/
	public void isSuccess(String message){
		this.equals(true, true, "["+currentClassName+"]"+message);
	}
	
	/**
	* @Description 依据flag，判断此前所有检查点中是否有错误值。若失败，退出当前运行的.java程序。
	* 			      失败后，result方法下方的代码将不会被执行
	* @param message:case成功时需要记录到日志的信息
	*/
	public void result(String message){	
		Assert.assertEquals(flag, 0);
		Log.logInfo("["+currentClassName+"]"+message);
		Log.reporter(0, "["+currentClassName+"]"+message);
	}
	
}
