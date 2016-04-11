package com.aoyou.app.util;

/**
 * @Package com.aoyou.app.util
 * @ClassName Config
 * @Description 全局配置，值持久化模块.在每次build前解析config.xml,加载配置信息.防止在测试运行中修改配置产生的影响
 */

public class Config {
	
	public static String automationName;
	public static String platformName;
	public static long appiumServerWaitTime;
	public static String appPath;
	public static String newCommandTimeout;
	public static Boolean noReset;
	public static Boolean fullReset;
	public static String packageName;
	public static String activityName;
	public static String startActivityName;
	public static String deviceReadyTimeout;
	public static Boolean unicodeKeyboard;
	public static int objectWaitTime;
	public static String objectRespository;
	public static int retryTimes;
	public static String SMTPserver;
	public static String from;
	public static String username;
	public static String password ;
	public static String to;
	public static String copyTo;
	public static String filename;
	public static String subject;
	public static String reportAddress;
	public static String projectName;
	
	static{		
		ParseXml pxConfig=new ParseXml("config/config.xml");
		automationName=pxConfig.getElementText("/config/general/automationName");
		platformName=pxConfig.getElementText("/config/general/platformName");
		appiumServerWaitTime=Integer.parseInt(pxConfig.getElementText("/config/general/appiumServerWaitTime"));
		appPath=pxConfig.getElementText("/config/general/appPath");
		newCommandTimeout=pxConfig.getElementText("/config/general/newCommandTimeout");
		noReset=Boolean.valueOf(pxConfig.getElementText("/config/general/noReset"));
		fullReset=Boolean.valueOf(pxConfig.getElementText("/config/general/fullReset"));
		packageName=pxConfig.getElementText("/config/android/packageName");
		activityName=pxConfig.getElementText("/config/android/activityName");
		startActivityName=pxConfig.getElementText("/config/android/startActivityName");
		deviceReadyTimeout=pxConfig.getElementText("/config/android/deviceReadyTimeout");
		unicodeKeyboard=Boolean.valueOf(pxConfig.getElementText("/config/android/unicodeKeyboard"));	
		objectWaitTime=Integer.valueOf(pxConfig.getElementText("/config/objectWaitTime"));
		objectRespository=pxConfig.getElementText("/config/objectRespository");	
		retryTimes=Integer.valueOf(pxConfig.getElementText("/config/retryTimes"));
		SMTPserver=pxConfig.getElementText("/config/mail/SMTPserver");
		from=pxConfig.getElementText("/config/mail/from");
		username=pxConfig.getElementText("/config/mail/username");
		password=pxConfig.getElementText("/config/mail/password");
		to=pxConfig.getElementText("/config/mail/to");
		copyTo=pxConfig.getElementText("/config/mail/copyTo");
		filename=pxConfig.getElementText("/config/mail/filename");
		subject=pxConfig.getElementText("/config/mail/subject"); 
		reportAddress=pxConfig.getElementText("/config/mail/reportAddress"); 
		projectName=pxConfig.getElementText("/config/mail/projectName"); 
	}
	
}


