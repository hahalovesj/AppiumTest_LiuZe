package com.aoyou.app.functions;

import com.aoyou.app.base.Locator;

public class Action {

	private Locator locator;
	
	public Action(Locator locator){
		this.locator = locator;
	}
	
	/**
	 * 通过滑动操作，跳过引导页
	 * @param int times 滑动次数
	 */
	public void skipToMain(int times){	
		if(locator.elementExist(false, "引导页", "引导图")){
			for(int i=0; i<times; i++){
				locator.swipeActivity("left");
				locator.sleep(1);
			}
			locator.element("引导页", "开始按钮").click();
		}
	}
	
}
