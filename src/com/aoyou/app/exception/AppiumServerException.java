package com.aoyou.app.exception;

import com.aoyou.app.util.Log;



@SuppressWarnings("serial")
public class AppiumServerException extends AoYouException{

	public AppiumServerException(String message) {
		super(message);
		Log.logWarn(message);
	}

}
