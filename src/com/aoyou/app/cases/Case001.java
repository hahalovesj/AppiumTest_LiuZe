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
		locator.element("��ҳ", "����������").click();
		locator.element("����ҳ", "Ŀ�ĵ������").clear();
		locator.element("����ҳ", "Ŀ�ĵ������").sendKeys("�ձ�");
		locator.element("����ҳ", "Ŀ�ĵ�ѡ����").click();
		locator.sleep(3);
		proSize = locator.elements("����ҳ", "��Ʒ").size();
		
		if(proSize!=0){
			title = locator.elementsTextForList("����ҳ", "��Ʒ����");
			price = locator.elementsTextForList("����ҳ", "��Ʒ�۸�");
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
				checkPoint.isFaild("Ŀ�ĵأ��ձ�,δ��������Ʒ!");
			 }
		
		checkPoint.result("Ŀ�ĵأ��ձ�,������"+proSize+"����Ʒ����Ʒ���⣬��Ʒ�۸�������");
	}

}
