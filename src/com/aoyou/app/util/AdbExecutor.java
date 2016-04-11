package com.aoyou.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdbExecutor {

	public static final String AdbPath = "adb shell ";//"D:/android-sdk/platform-tools/adb";
	
	private static Process Execute(String cmd){
		
		Process adbProcess = null;
	
		try {
			adbProcess = Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			
			Log.logError("[AdbExecutor] 执行cmd命令失败:"+cmd);
		}
		return adbProcess;
	}
	
	private static String getOutput(Process process){		
		BufferedReader  br = null;
		String line = null;
		StringBuilder output = new StringBuilder();	
		br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		try {
			while ((line = br.readLine())!=null) {
				output.append(line+"\n");
			}
		} catch (IOException e) {
			Log.logError("[AdbExecutor] 获取cmd返回值失败"+e.getMessage());
		}
		 
		return output.toString();
	}
	
	
	/**
	 * 获取当前 焦点所在的活动名
	 */
	public static String getTheFocusedActivity(String udid){
		String cmd = "adb -s "+udid+" shell dumpsys activity | grep Focuse";	
		return getOutput(Execute(cmd));	
	}
	
	/**
	 * 获取当前 已连接的安卓设备信息
	 */
	public static String getDevicesInfo(){
		String cmd = "cmd /c adb devices";
		return getOutput(Execute(cmd));
	}
	
	/**
	 * 验证指定的包是否已在指定设备安装
	 */
	public static boolean  isPackageInstalled(String udid,String packageName){
		boolean flag = false; 
		String cmd = "adb -s "+udid+" shell pm list package";
		String output = getOutput(Execute(cmd));
		if(output.contains(packageName)){
			flag = true;
		}	
		return flag;			
	}
	
	public static boolean  isPackageInstalled(String packageName){
		boolean flag = false; 
		String cmd = AdbPath+" pm list package";
		String output = getOutput(Execute(cmd));
		if(output.contains(packageName)){
			flag = true;
		}	
		return flag;			
	}
	
	/**
	 * 启动指定设备上的指定活动
	 */
	public static void startTheActivity(String udid,String packageName,String activityName){
		String cmd = "adb -s "+udid+" shell am start -n "+packageName+"/"+packageName+"."+activityName;	
		Execute(cmd);	
	}
	
	public static void startTheActivity(String packageName,String activityName){
		String cmd = AdbPath+" am start -n "+packageName+"/"+packageName+"."+activityName;	
		Execute(cmd);	
	}
	
	/**
	 * 关闭指定app
	 */
	public static void closeTheAPP(String udid,String packageName){
		String cmd = "adb -s "+udid+" shell am force-stop "+packageName;	
		Execute(cmd);	
	}
	
	/**
	 * 清除app缓存 
	 * @param packagename
	 */
	public static void clearAndroidCache(String udid,String packagename){
		String cmd = "adb -s "+udid+" shell pm clear "+packagename;
		Execute(cmd);
	}
	
	/**
	 * 关闭所有已打开的cmd窗口
	 */
	public static void closeCmdWindows(){
		String cmd = "taskkill /f /im cmd.exe";
		Execute(cmd);
	}
	
}
