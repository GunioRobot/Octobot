package com.urbanairship.octobot.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.urbanairship.octobot.Queue;

public class TaskExecutor {

    private final Map<String, Method> methodForTaskCache;
    private final Map<String, Class> classForTaskCache;
    private final Map<String, TaskInitializer> initializerForTaskCache;

    private final Map<String, TaskConfig> configForTaskMapping;
    
    public TaskExecutor(Queue queue) {
    	this.methodForTaskCache = new HashMap<String, Method>();
    	this.classForTaskCache = new HashMap<String, Class>();
    	this.initializerForTaskCache = new HashMap<String, TaskInitializer>();
    	
    	this.configForTaskMapping = queue.getTasks();
    }

	@SuppressWarnings("unchecked")
    public void execute(String taskName, JSONObject message) 
            throws ClassNotFoundException,
                   NoSuchMethodException,
                   IllegalAccessException,
                   InvocationTargetException, 
                   InstantiationException {

        Method method = getMethodForTask(taskName);
        invokeMethod(taskName, message, method);
    }

	private void invokeMethod(String taskName, JSONObject message, Method method)
			throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
		if (this.configForTaskMapping.containsKey(taskName)) {
        	TaskConfig taskConfig = this.configForTaskMapping.get(taskName);
        	if (!taskConfig.isStatic()) {
        		OctobotTask instance = getInstanceForTask(taskName);

        		if (!this.initializerForTaskCache.containsKey(taskName)) {
        			String initializerClass = taskConfig.getTaskInitializer();
        			Class iClass = Class.forName(initializerClass);
        			TaskInitializer theInitializer = (TaskInitializer) iClass.newInstance();
        			theInitializer.initialize(instance);
        		}
        		
        		method.invoke(instance, new Object[] { message });

        		return;
        	}
        	
        }
        
        method.invoke(null, new Object[]{ message });
	}

	private OctobotTask getInstanceForTask(String taskName)
			throws InstantiationException, IllegalAccessException {
		Class theClass = classForTaskCache.get(taskName);
		OctobotTask instance = (OctobotTask) theClass.newInstance();
		return instance;
	}

	private Method getMethodForTask(String taskName)
			throws ClassNotFoundException, NoSuchMethodException {
		Method method = null;

        if (methodForTaskCache.containsKey(taskName)) {
            method = methodForTaskCache.get(taskName);
        } else {
            Class task = getClassForTaskName(taskName);
            method = task.getMethod("run", new Class[]{ JSONObject.class });
            methodForTaskCache.put(taskName, method);
        }
		return method;
	}

	private Class getClassForTaskName(String taskName)
			throws ClassNotFoundException {
		
		if (classForTaskCache.containsKey(taskName))
			return classForTaskCache.get(taskName);
		
		// check task mapping
		Class task = null;
		String checkName = null;
		if (configForTaskMapping.containsKey(taskName)) {
			TaskConfig tc = configForTaskMapping.get(taskName);
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

}
