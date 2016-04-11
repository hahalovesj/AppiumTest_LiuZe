package com.aoyou.app.base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aoyou.app.functions.Action;
import com.aoyou.app.util.AdbExecutor;
import com.aoyou.app.util.CheckPoint;
import com.aoyou.app.util.Config;

public class TestBase {
	
	protected AppiumServer server;
	protected Locator locator;
	protected Action action;
	protected CheckPoint checkPoint;

	@BeforeTest
	@Parameters({"udid","deviceName","platformVersion"})
	public void beforeTest(String udid,String deviceName,String platformVersion){
		server = new AppiumServer(udid);
		server.startService();
		Data.driverMap.put(udid, DriverFactory.getAppiumDriver(server.getAppiumPort(), deviceName, udid, platformVersion));
	}
	
	@BeforeClass
	@Parameters({"deviceName","udid"})
	public void beforeCase(String deviceName,String udid){
		locator = new Locator(Data.driverMap.get(udid));
		action = new Action(locator);
		action.skipToMain(3);
		String currentClassName = this.getClass().getSimpleName()+"-"+deviceName;
		checkPoint = new CheckPoint(Data.driverMap.get(udid),currentClassName);
		AdbExecutor.startTheActivity(udid,Config.packageName, Config.startActivityName);
	}
	
	@AfterTest
	@Parameters({"udid"})
	public void afterTest(String udid){
		Data.driverMap.get(udid).quit();
	}
	
	@AfterSuite
	public void afterSuite(){
		AppiumServer.stopService();
		AdbExecutor.closeCmdWindows();
	}
}
