package com.urbanairship.octobot.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TaskConfig {

	private static final String CLASS_NAME = "class";
	private static final String INITIALIZER_NAME = "initializer";
	private String taskClass;
	private String taskInitializer;

	public TaskConfig(String taskClass) {
		this.taskClass = taskClass;
		this.taskInitializer = null;
	}
	
	public TaskConfig(String taskClass, String taskInitializer) {
		this.taskClass = taskClass;
		this.taskInitializer = taskInitializer;
	}
	
	public static HashMap<String, TaskConfig> parse(Map<String, Map<String, String>> taskMap) {
		
		HashMap<String, TaskConfig> returnMap = new HashMap<String, TaskConfig>();

		for(Entry<String, Map<String, String>> e : taskMap.entrySet()) {
			Map<String, String> taskProperties = e.getValue();
			String taskClass = null;
			String taskInitializer = null;
			
			if (taskProperties.containsKey(INITIALIZER_NAME))
				taskInitializer = taskProperties.get(INITIALIZER_NAME);
			
			if (taskProperties.containsKey(CLASS_NAME))
				taskClass = taskProperties.get(CLASS_NAME);
			
			TaskConfig tc = new TaskConfig(taskClass, taskInitializer);
			returnMap.put(e.getKey(), tc);
		}
		
		return returnMap;
	}

	public boolean isStatic() {
		return (this.taskInitializer == null);
	}

	public String getTaskClass() {
		return this.taskClass;
	}

	public String getTaskInitializer() {
		return this.taskInitializer;
	}

}
