package com.urbanairship.octobot.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.urbanairship.octobot.Queue;

public class TaskExecutor {

    private static final HashMap<String, Method> taskCache =
            new HashMap<String, Method>();
    private static final HashMap<String, Class> taskmapCache = new HashMap<String, Class>();
    
    protected HashMap<String, String> classForTask;
    
    public TaskExecutor(Queue queue) {
    	// add task mapping here
    	classForTask = new HashMap<String, String>(queue.getTasks());
    }

	@SuppressWarnings("unchecked")
    public void execute(String taskName, JSONObject message) 
            throws ClassNotFoundException,
                   NoSuchMethodException,
                   IllegalAccessException,
                   InvocationTargetException {

        Method method = null;

        if (taskCache.containsKey(taskName)) {
            method = taskCache.get(taskName);
        } else {
            Class task = getClassForTaskName(taskName);
            method = task.getMethod("run", new Class[]{ JSONObject.class });
            taskCache.put(taskName, method);
        }

        method.invoke(null, new Object[]{ message });
    }

	private Class getClassForTaskName(String taskName)
			throws ClassNotFoundException {
		
		if (taskmapCache.containsKey(taskName))
			return taskmapCache.get(taskName);
		
		// check task mapping
		Class task = null;
		String checkName = null;
		if (classForTask.containsKey(taskName))
			checkName = classForTask.get(taskName);
		else
			checkName = taskName;
		
		try {
			task = Class.forName(checkName);
			taskmapCache.put(taskName, task);
		}
		catch (ClassNotFoundException e) {
			task = Class.forName(taskName);
			taskmapCache.put(taskName, task);
		}
		return task;
	}

}
