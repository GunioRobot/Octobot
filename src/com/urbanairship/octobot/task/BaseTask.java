package com.urbanairship.octobot.task;

import java.lang.reflect.Method;

abstract public class BaseTask implements ITask {

	protected Method method;

	public BaseTask(Method method) {
		this.method = method;
	}

	
	
}
