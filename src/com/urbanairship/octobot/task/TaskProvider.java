package com.urbanairship.octobot.task;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.urbanairship.octobot.OctobotTask;
import com.urbanairship.octobot.OctobotTaskInitializer;

public class TaskProvider {

	private final Map<String, Class> classForTaskCache;
	
	private final Map<Class, Method> methodForTaskCache;
	private final Map<Class, OctobotTaskInitializer> initializerForTaskCache;
	
	private Map<String, TaskConfig> taskConfigMapping;

	public TaskProvider(Map<String, TaskConfig> tc) {
		this.methodForTaskCache = new HashMap<Class, Method>();
		this.classForTaskCache = new HashMap<String, Class>();
		this.initializerForTaskCache = new HashMap<Class, OctobotTaskInitializer>();
		this.taskConfigMapping = tc;
	}
	
	public ITask getTask(String taskName) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		Class theClass = getClassForTaskName(taskName);
		Method runMethod = this.getMethodForTask(theClass);
		
		if (!taskConfigMapping.containsKey(taskName) 
				|| taskConfigMapping.get(taskName).isStatic()) {
			return new StaticTask(runMethod);
		}

		OctobotTaskInitializer initializer = this.getInitializerForClass(theClass, taskConfigMapping.get(taskName));
		OctobotTask instance = (OctobotTask) theClass.newInstance();
		
		return new InitializedTask(instance, 
				initializer, runMethod);
	}

	private OctobotTaskInitializer getInitializerForClass(Class theClass, TaskConfig taskConfig) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (!this.initializerForTaskCache.containsKey(theClass)) {
			String initializerClass = taskConfig.getTaskInitializer();
			Class iClass = Class.forName(initializerClass);
			OctobotTaskInitializer theInitializer = (OctobotTaskInitializer) iClass.newInstance();
			this.initializerForTaskCache.put(theClass, theInitializer);
		}
		
		return this.initializerForTaskCache.get(theClass);
	}

	private Class getClassForTaskName(String taskName) throws ClassNotFoundException {

		if (classForTaskCache.containsKey(taskName))
			return classForTaskCache.get(taskName);

		// check task mapping
		Class task = null;
		String checkName = null;
		if (taskConfigMapping.containsKey(taskName)) {
			TaskConfig tc = taskConfigMapping.get(taskName);
			checkName = tc.getTaskClass();
		}
		else
			checkName = taskName;

		try {
			task = Class.forName(checkName);
			classForTaskCache.put(taskName, task);
		}
		catch (ClassNotFoundException e) {
			task = Class.forName(taskName);
			classForTaskCache.put(taskName, task);
		}
		return task;
	}


	@SuppressWarnings("unchecked")
	private Method getMethodForTask(Class theClass)
			throws ClassNotFoundException, NoSuchMethodException {
		Method method = null;

        if (methodForTaskCache.containsKey(theClass)) {
            method = methodForTaskCache.get(theClass);
        } else {
            method = theClass.getMethod("run", new Class[]{ JSONObject.class });
            methodForTaskCache.put(theClass, method);
        }
		return method;
	}
}
