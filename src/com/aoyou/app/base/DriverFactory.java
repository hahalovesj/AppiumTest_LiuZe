package com.aoyou.app.base;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.aoyou.app.exception.AppiumDriverException;
import com.aoyou.app.util.AdbExecutor;
import com.aoyou.app.util.Config;
import com.aoyou.app.util.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverFactory {
	
	 public  static AppiumDriver getAppiumDriver(int serverPort ,String deviceName,String udid,String platformVersion){
		 
		 	AppiumDriver driver = null;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			capabilities.setCapability("udid", udid);
			if(!AdbExecutor.isPackageInstalled(udid,Config.packageName)){
				File apk = new File(Config.appPath);
				if(Config.appPath.isEmpty()&&!apk.exists()){
					throw new AppiumDriverException("要安装的apk路径错误或文件不存在");
				}else capabilities.setCapability(MobileCapabilityType.APP, Config.appPath);
			}
			capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, Config.packageName);
			capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, Config.activityName);
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Config.newCommandTimeout);
			capabilities.setCapability("noReset", Config.noReset);
			capabilities.setCapability("fullReset", Config.fullReset);
			capabilities.setCapability(MobileCapabilityType.DEVICE_READY_TIMEOUT, Config.deviceReadyTimeout);
			capabilities.setCapability("unicodeKeyboard", Config.unicodeKeyboard);
						
			try {
				driver = new AndroidDriver(new URL("http://127.0.0.1:"+String.valueOf(serverPort)+"/wd/hub"), capabilities);
				
			}catch (Exception e) {
				Log.logError("启动AndroidDriver异常："+e.getMessage());
			}
			
			driver.manage().timeouts().implicitlyWait(Config.objectWaitTime, TimeUnit.SECONDS);			
			return driver;		
	 }
	 
	 public  static AppiumDriver getAppiumDriver(int serverPort,String deviceName){
		 
		 	AppiumDriver driver = null;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			if(!AdbExecutor.isPackageInstalled(Config.packageName)){
				File apk = new File(Config.appPath);
				if(Config.appPath.isEmpty()&&!apk.exists()){
					throw new AppiumDriverException("要安装的apk路径错误或文件不存在");
				}else capabilities.setCapability(MobileCapabilityType.APP, Config.appPath);
			}
			capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, Config.packageName);
			capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, Config.activityName);
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Config.newCommandTimeout);
			capabilities.setCapability("noReset", Config.noReset);
			capabilities.setCapability("fullReset", Config.fullReset);
			capabilities.setCapability(MobileCapabilityType.DEVICE_READY_TIMEOUT, Config.deviceReadyTimeout);
			capabilities.setCapability("unicodeKeyboard", Config.unicodeKeyboard);
						
			try {
				driver = new AndroidDriver(new URL("http://127.0.0.1:"+String.valueOf(serverPort)+"/wd/hub"), capabilities);
				
			}catch (Exception e) {
				Log.logError("启动AndroidDriver异常："+e.getMessage());
			}
			
			driver.manage().timeouts().implicitlyWait(Config.objectWaitTime, TimeUnit.SECONDS);			
			return driver;		
	 }
	 
}
