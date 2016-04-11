package com.aoyou.app.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;

import com.aoyou.app.base.Data;



/**
 * @Package com.aoyou.test.util
 * @ClassName Log
 * @Description ��־��ӡģ��.��־����:Info<Warn<Error
 * 3�������ӿڷ���:logError/logInfo/logWarn
 * 
 */
 
public class Log {
	
	private static Logger logger;
	private static String filePath="src/log4j.properties";
	private static boolean flag=false;
	
	private static synchronized void getPropertyFile(){	
		logger=Logger.getLogger("");
		PropertyConfigurator.configure(new File(filePath).getAbsolutePath());
		flag=true;
	}
	
	/**
	 * ��ʼ����飬������״ε������¼����ļ�·��
	 */
	private static void getFlag(){		
		if(flag == false)
			Log.getPropertyFile();
	}
	
	/**
	 * ��ӡInfo�������־
	 * @param message ��Ҫ��ӡ����Ϣ
	 */
	public static void logInfo(String message){
		Log.getFlag();
		logger.info(message);
	}
	
	/**
	 * ��ӡError�������־
	 * @param message ��Ҫ��ӡ����Ϣ
	 */
	public static void logError(String message){
		Log.getFlag();
		logger.error(message);
		reporter(1,message);
	}
	
	/**
	 * ��ӡWarning�������־
	 * @param message ��Ҫ��ӡ����Ϣ
	 */
	public static void logWarn(String message){
		Log.getFlag();
		logger.warn(message);
		reporter(2,message);
	}
	
	public static void reporter(int level,String message){
		switch(level){
		case 0:
			Reporter.log("��PASS��"+" "+message);
			Data.reporterLog.add("��PASS��"+" "+message);
			break;
		case 1:
			Reporter.log("��FAILED�� "+" "+message);
			Data.reporterLog.add("��FAILED�� "+" "+message);
			break;	
		case 2:
			Reporter.log("��WARNING�� "+" "+message);
			Data.reporterLog.add("��WARNING�� "+" "+message);
			break;	
		default:System.out.println("���漶�����!");	
		}
		
	}
	
	
}
