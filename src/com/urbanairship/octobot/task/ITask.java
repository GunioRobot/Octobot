package com.urbanairship.octobot.task;

import java.lang.reflect.InvocationTargetException;

import org.json.simple.JSONObject;

public interface ITask {

	public void execute(JSONObject object) throws IllegalArgumentException, 
												  IllegalAccessException, 
												  InvocationTargetException;
	
}
