package com.urbanairship.octobot.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;

import com.urbanairship.octobot.OctobotTask;
import com.urbanairship.octobot.OctobotTaskInitializer;

public class InitializedTask extends BaseTask {
	
	private OctobotTask instance;
	private OctobotTaskInitializer theInitializer;

	public InitializedTask(OctobotTask instance,
			OctobotTaskInitializer theInitializer, Method method) {
		super(method);
		this.instance = instance;
		this.theInitializer = theInitializer;
	}



	@Override
	public void execute(JSONObject object) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		
		theInitializer.initialize(instance);
		method.invoke(instance, new Object[] { object });
	}

	
}
