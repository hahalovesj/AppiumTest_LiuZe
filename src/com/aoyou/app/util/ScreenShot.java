package com.aoyou.app.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
/**
 * @Package com.aoyou.test.util
 * @ClassName ScreenShot
 * @Description 测试截图模块
 * 1个公共接口方法:takeScreenshot
 */

public class ScreenShot {
	public WebDriver driver;
	
	/**
	 * @param drvier:当前需要截图的driver
	 */
	public ScreenShot(WebDriver driver){
		this.driver=driver;
	}
	
	private void takeScreenshot(String screenPath){
		try{
			File srcFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(screenPath));
		}catch(IOException e){
			Log.logError("Screen shot error:" + screenPath);
		}
	}
	
	/**
	 *截图方法，默认路径为当前project目录下：test-output/snapshot
	 */
    public void takeScreenshot(){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String mDateTime = formatter.format(new Date().getTime());
		String screenName = mDateTime+".bmp";
    	File dir=new File("test-output/snapshot");
    	if(!dir.exists())
    		dir.mkdirs();
    	String screenPath=dir.getAbsolutePath() + "/" + screenName;
    	this.takeScreenshot(screenPath);
    }
}