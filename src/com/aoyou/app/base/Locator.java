package com.aoyou.app.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aoyou.app.exception.ElementsNotFound;
import com.aoyou.app.util.Config;
import com.aoyou.app.util.Log;
import com.aoyou.app.util.ParseXml;
import io.appium.java_client.AppiumDriver;

public class Locator {
	
	private AppiumDriver driver;
	private String filePath; 	//filePath �����xml�ļ���·������config�ļ�������
	private ParseXml px;
	private Boolean existFlag;
	
	public Locator(AppiumDriver driver){
		this.driver = driver;
		filePath=Config.objectRespository;
		px = new ParseXml(filePath);
		existFlag =true;
	}
	
	public AppiumDriver getDriver(){
		return driver;
	}
	
	public WebElement element(String pageKey,String objKey){
		return this.getLocator(pageKey, objKey,true,true);				
	}
	
	/**
	* @Description ���ݶ���xml��������Ӧ�ĵ���ҳ��Ԫ�أ�����WebElement���޵ȴ�ʱ��
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @see getLocator
	* @return WebElement 
	*/
	public WebElement elementNoWait(String pageKey,String objKey) {		
		return this.getLocator(pageKey, objKey, false,true);		
	}
	
	/**
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ�����WebElement�б�
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @see getLocators
	* @return List<WebElement>
	*/
	public List<WebElement> elements(String pageKey,String objKey){
		return this.getLocators(pageKey,objKey);		
	}
	
	/** 
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ������б��е�һ������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return WebElement 
	*/
	public WebElement theFirstElement(String pageKey,String objKey){
		List<WebElement> elements =this.getLocators(pageKey,objKey);	
		return elements.get(0);
	}
	
	/** 
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ������б������һ������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return WebElement 
	*/
	public WebElement theLastElement(String pageKey,String objKey){
		List<WebElement> elements =this.getLocators(pageKey,objKey);	
		return elements.get(elements.size()-1);
	}
	
	/** 
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�أ������б��е�һ���������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return WebElement 
	*/
	public WebElement theRandomElement(String pageKey,String objKey){
		List<WebElement> elements =this.getLocators(pageKey,objKey);	
		int index = (int)(Math.random()*elements.size());
		return elements.get(index);
	}
	
	/**
	* @Description ���ݶ���xml���ж�ĳ��ҳ��Ԫ���Ƿ���ڣ����ز������ʽ
	* @param wait  �����ͱ������Ƿ���Ҫ�ȴ�
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @see getLocator
	* @return boolean
	*/
	public boolean elementExist(boolean wait,String pageKey,String objKey){		
		if (wait){
			this.getLocator(pageKey, objKey,true,false);
			return existFlag;
		}
		else{
			this.getLocator(pageKey, objKey,false,false);
			return existFlag;
		}
	}
	
	/**
	* @Description ���ݶ���xml������ͬһ����λ�����µĶ��ҳ��Ԫ�ص�Textֵ�������ַ�������
	* @param pageKey ����xml�е�ҳ������
	* @param objKey  ����xml�еĶ�������
	* @return String[] 
	*/
	public String[] elementsText(String pageKey,String objKey){		
		String []text = new String[this.elements(pageKey, objKey).size()];		
			for(int i=0;i<this.elements(pageKey, objKey).size();i++){			
				text[i] = this.elements(pageKey, objKey).get(i).getText();			
			}		
		return text;		
	}
	
	public List<String> elementsTextForList(String pageKey,String objKey){	
		List<String> results = new ArrayList<String>();
		String []text = new String[this.elements(pageKey, objKey).size()];		
			for(int i=0;i<this.elements(pageKey, objKey).size();i++){			
				text[i] = this.elements(pageKey, objKey).get(i).getText();	
			}	
			
			results =Arrays.asList(text);
			
		return results;		
	}
	
	/**
	* @Description �̶�ʱ��ȴ�
	* @param time�̶��ȴ���ʱ������λΪ����
	* @return void 
	*/
	public void sleep(int time){
		try {
			int sTime = time*1000;
			Thread.sleep(sTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sendKeyEvent(int key){
		try {
			driver.sendKeyEvent(key);
		} catch (Exception e) {
			Log.logError("��Locator��:sendKeyEvent"+e.getMessage());
		}
	}
	
	/**
	 * ��������
	 * @param indicator:�ɴ���Ĳ���ֵ��left,right,up,down
	 */
	public void swipeActivity(String indicator){
		Dimension dimension = driver.manage().window().getSize();
		int height = dimension.getHeight();
		int width = dimension.getWidth();
		indicator = indicator.toLowerCase().trim();
		
		switch(indicator){
			case "left":
				driver.swipe(width-20, height/2, 20, height/2, 300);
				break;	
			case "right":
				driver.swipe(20, height/2, width-20, height/2, 300);
				break;		
			case "up":
				driver.swipe(width/2, height-20, width/2, 20, 300);
				break;	
			case "down":
				driver.swipe(width/2, 20, width/2, height-20, 300);
				break;
			default:Log.logError("��������,������ʹ���");
		}
	}
	
	private By getBy(String type ,String value){
		By by = null;
		switch(type){
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "className":
			by = By.className(value);	
			break;
		case "tagName":
			by = By.tagName(value);	
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;	
		case "cssSelector":
			by = By.cssSelector(value);
			break; 	
		}
		return by;	 
	}
	
	private WebElement waitForElement(final By by,boolean log,String pageKey,String objKey) {
	    WebElement element = null;    
	    try {
	        element = new WebDriverWait(driver, Config.objectWaitTime)
	                .until(new ExpectedCondition<WebElement>() {
	                    public WebElement apply(WebDriver d) {
	                    	existFlag = true;	//�����Ƿ���ڵı�־λ
	                        return d.findElement(by);
	                    }
	                });
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	if(log){
	        Log.logError("��Locator�� "+pageKey + "  "+objKey+" is not exist until " + Config.objectWaitTime);
	    	}
	        existFlag = false;
	    }
	    return element;
	}    
	
	/*
	 * �������أ�ΪAppium��װ��findElement�����ṩwaitFor����
	 */
	@SuppressWarnings("unused")
	private WebElement waitForElement(final String type,final String value,boolean log,String pageKey,String objKey) {
	    WebElement element = null;    
	    try {
	        element = new WebDriverWait(driver, Config.objectWaitTime)
	                .until(new ExpectedCondition<WebElement>() {
	                	@Override
	                    public WebElement apply(WebDriver d) {
	                    	existFlag = true;	//�����Ƿ���ڵı�־λ
	                    	if("accessibilityId".equals(type)){
	                    		 return driver.findElementByAccessibilityId(value);
	                    	}else if("name".equals(type)){
	                    		return driver.findElementByClassName(value);
	                    	}
							return null;	                       
	                    }
	                });
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	if(log){
	        Log.logError("��Locator�� "+pageKey + "  "+objKey+" is not exist until " + Config.objectWaitTime);
	    	}
	        existFlag = false;
	    }
	    return element;
	}   
	
	private boolean waitElementToBeDisplayed(final WebElement element,String page,String obj) {
	    boolean wait = false;
	    if (element == null)
	        return wait;
	    try {
	        wait = new WebDriverWait(driver, Config.objectWaitTime)
	                .until(new ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver d) {
	                        return element.isDisplayed();
	                    }
	                });
	    } catch (Exception e) {
	    	Log.logError("��Locator�� "+page + "  "+obj + " is not displayed");
	    }
	    return wait;
	}
	
	private WebElement getLocator(String pageKey,String objKey, boolean wait,boolean log) {	
	    WebElement element = null;
	    	if (px.isExist("/����/"+pageKey+"/"+objKey)) {	    	
	    		//��̬����xml�еĶ����Ӧ��type��value
	        	String type = px.getElementText("/����/"+pageKey+"/"+objKey+"/type");
	        	String value = px.getElementText("/����/"+pageKey+"/"+objKey+"/value");
	        		
	        		//�����λ����Ϊ��name��accessibilityId���Ͳ���ʹ��ƴ��By����λԪ��
	        		if("name".equals(type) || "accessibilityId".equals(type)){
	        			 
	        		            try {
	        		            	
	        		            	if("name".equals(type)){
	        		            		element = driver.findElementByName(value);
	        		            	}else if("accessibilityId".equals(type)){
	        		            		element = driver.findElementByAccessibilityId(value);        		            		
	        		            	}
	        		            	existFlag = true;      		                    
	        		            } catch (Exception e) {
	        		                element = null;
	        		                if(log){
	        		                Log.logError("��Locator�� "+pageKey+"-"+objKey+" "+type+":"+value+" is not found!!!");
	        		                }
	        		                existFlag = false;
	        		            }
	        		        
	        			  return element;  
	        		}
	        	
	        By by = this.getBy(type, value);	        
	        if (wait) {
	        	//�ж�ҳ��Ԫ���Ƿ�����ҿɼ�
	            element = this.waitForElement(by,log,pageKey,objKey);
	            boolean flag = this.waitElementToBeDisplayed(element,pageKey,objKey);
	            if (!flag)
	                element = null;
	        } else {
	            try {
	                element = driver.findElement(by);
	                existFlag = true;	  //elementExistʹ��              
	            } catch (Exception e) {
	                element = null;
	                if(log){
	                Log.logError("��Locator�� "+pageKey+"-"+objKey+" "+type+":"+value+" is not found!!!");
	                }
	                existFlag = false;	 //elementExistʹ��
	            }
	        }
	    } else
	        Log.logError("��Locator�� "+ pageKey+"-"+objKey + " is not exist in " + filePath);//xml�б���������
	    return element;
	}
	
	/**
	* @Description ���ҳ��Ԫ�ز������棬����WebElement�б�����Ԫ�ض�λʧ��ʱ����ӡ������־����ֹ��ǰ.java����
	* @return List<WebElement> 
	*/
	private List<WebElement> getLocators(String pageKey,String objKey) {	
	    List <WebElement> elements = null;
	    	if (px.isExist("/����/"+pageKey+"/"+objKey)) {	    		
	        	String type = px.getElementText("/����/"+pageKey+"/"+objKey+"/type");
	        	String value = px.getElementText("/����/"+pageKey+"/"+objKey+"/value");	   
	        	
        		if("name".equals(type) || "accessibilityId".equals(type)){
       			 	            	
		            	if("name".equals(type)){
		            		elements = driver.findElementsByName(value);
		            	}else if("accessibilityId".equals(type)){
		            		elements = driver.findElementsByAccessibilityId(value);        		            		
		            	}
		            	     		                    
		                if(elements.size()==0){
		                	throw new ElementsNotFound("��Locators��"+pageKey+"-"+objKey+" :objects not found!!!");
		                }
		                	for(WebElement e : elements){
		                		if(e.equals("")){
		                			Log.logError("��Locators�� "+pageKey+"-"+objKey+" :"+type+" :"+value+" some objects not found!!!");
		                			Assert.assertFalse(true);
		                		}
		                	}
	                	    	
		           return elements;

        		}
        		
	        By by = this.getBy(type, value);	        
	                elements = driver.findElements(by);	 
	                
	                if(elements.size()==0){
	                	throw new ElementsNotFound("��Locators��"+pageKey+"-"+objKey+" :objects not found!!!");
	                }
	                	for(WebElement e : elements){
	                		if(e.equals("")){
	                			Log.logError("��Locators�� "+pageKey+"-"+objKey+" :"+type+" :"+value+" some objects not found!!!");
	                			Assert.assertFalse(true);
	                		}
	                	}
                	
	    	}else 	    		
	           Log.logError("��Locator�� "+pageKey+"-"+objKey + " is not exist in " + filePath);	    	
	    return elements;
	}
	
}
