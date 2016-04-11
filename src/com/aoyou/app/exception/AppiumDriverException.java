package com.aoyou.app.exception;

import com.aoyou.app.util.Log;

@SuppressWarnings("serial")
public class AppiumDriverException extends AoYouException{

	public AppiumDriverException(String message) {
		super(message);
		Log.logWarn(message);
	}

}
