package com.aoyou.app.base;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.aoyou.app.functions.Action;
import com.aoyou.app.util.AdbExecutor;
import com.aoyou.app.util.CheckPoint;
import com.aoyou.app.util.Config;

public class DebugBase {
	
	protected AppiumServer server;
	protected AppiumDriver driver;
	protected Locator locator;
	protected Action action;
	protected CheckPoint checkPoint;
	
	@BeforeClass
	public void setup(){
		server = new AppiumServer();
		server.startService();
		driver = DriverFactory.getAppiumDriver(server.getAppiumPort(),"Debug");
		locator = new Locator(driver);
		action = new Action(locator);
		action.skipToMain(3);
		String currentClassName = this.getClass().getSimpleName()+"-"+"Debug";
		checkPoint = new CheckPoint(driver,currentClassName);
		AdbExecutor.startTheActivity(Config.packageName, Config.startActivityName);
	}
	
	@AfterClass
	public void setdown(){
		driver.quit();
		AppiumServer.stopService();
		AdbExecutor.closeCmdWindows();
	}
}
