package com.urbanairship.octobot.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.urbanairship.octobot.OctobotTask;
import com.urbanairship.octobot.Queue;
import com.urbanairship.octobot.OctobotTaskInitializer;

public class TaskExecutor {

    private final TaskProvider provider;
    
    public TaskExecutor(Queue queue) {
    	this.provider = new TaskProvider(queue.getTasks());    	
    }

	@SuppressWarnings("unchecked")
    public void execute(String taskName, JSONObject message) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
		ITask theTask = this.provider.getTask(taskName);
		theTask.execute(message);
    }
}
