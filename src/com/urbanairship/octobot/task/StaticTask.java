package com.urbanairship.octobot.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;

public class StaticTask extends BaseTask {

	public StaticTask(Method method) {
		super(method);
	}

	@Override
	public void execute(JSONObject jsonMessage) throws IllegalArgumentException, 
													   IllegalAccessException, 
													   InvocationTargetException {
        method.invoke(null, new Object[]{ jsonMessage });
	}

}
