package com.aoyou.app.cases;

import io.appium.java_client.android.AndroidKeyCode;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.aoyou.app.base.TestBase;

public class Case002 extends TestBase{

	@Test		
	public void case002(){
	
	List<WebElement> links = locator.elements("��ҳ", "Ƶ������");
	String title = "";
	
		links.get(0).click();
		title = locator.element("��ҳ","Ƶ��ҳ������").getText().trim();
		checkPoint.equals(title, "��������", "��������ת����");
		locator.sendKeyEvent(AndroidKeyCode.BACK);
		locator.sleep(1);

		links.get(1).click();
		title = locator.element("��ҳ","Ƶ��ҳ������").getText().trim();
		checkPoint.equals(title, "��������", "��������ת����");
		locator.sendKeyEvent(AndroidKeyCode.BACK);
		locator.sleep(1);

		links.get(3).click();
		title = locator.element("��ҳ","Ƶ��ҳ������").getText().trim();
		checkPoint.equals(title, "���λ�", "���Ż���ת����");
		locator.sendKeyEvent(AndroidKeyCode.BACK);
		locator.sleep(1);

		checkPoint.result("��ҳ�������Σ������Σ����Ż�������ת������");
	}	
}


