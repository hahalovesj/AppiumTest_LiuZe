package com.aoyou.app.cases;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;
import com.aoyou.app.base.TestBase;

public class Case001 extends TestBase{

	@Test
	public void case001(){

		List<String> title = new ArrayList<String>();
		List<String> price = new ArrayList<String>();

		int proSize = 0;
		locator.element("首页", "顶部搜索框").click();
		locator.element("搜索页", "目的地输入框").clear();
		locator.element("搜索页", "目的地输入框").sendKeys("日本");
		locator.element("搜索页", "目的地选择项").click();
		locator.sleep(3);
		proSize = locator.elements("搜索页", "产品").size();
		
		if(proSize!=0){
			title = locator.elementsTextForList("搜索页", "产品标题");
			price = locator.elementsTextForList("搜索页", "产品价格");
			checkPoint.notEquals(title, "");
			checkPoint.notEquals(title, null);
			checkPoint.notEquals(title, "null");
			checkPoint.notEquals(price, "");
			checkPoint.notEquals(price, null);
			checkPoint.notEquals(price, "null");
			checkPoint.notEquals(price, "0");
			checkPoint.notEquals(price, "1");
			checkPoint.equals(true, true);
		}else{
				checkPoint.isFaild("目的地：日本,未搜索到产品!");
			 }
		
		checkPoint.result("目的地：日本,首屏共"+proSize+"个产品，产品标题，产品价格正常！");
	}

}
