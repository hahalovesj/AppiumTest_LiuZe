package com.aoyou.app.functions;

import com.aoyou.app.base.Locator;

public class Action {

	private Locator locator;
	
	public Action(Locator locator){
		this.locator = locator;
	}
	
	/**
	 * ͨ��������������������ҳ
	 * @param int times ��������
	 */
	public void skipToMain(int times){	
		if(locator.elementExist(false, "����ҳ", "����ͼ")){
			for(int i=0; i<times; i++){
				locator.swipeActivity("left");
				locator.sleep(1);
			}
			locator.element("����ҳ", "��ʼ��ť").click();
		}
	}
	
}
