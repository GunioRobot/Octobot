package com.urbanairship.octobot.task;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.jvyaml.YAML;

public class TestTaskConfig {

	@Test
	public void properlyParsesTaskConfig() {
		String sampleTask = "com.urbanairship.octobot.task.SampleTask";
		String sampleInitializer = "com.urbanairship.octobot.task.SampleTaskInitializer";
		
		String yaml = "tasks: {\n" +
			"   task1: {\n" +
			"      class: " + sampleTask + ",\n" +
			"      initializer: " + sampleInitializer + "\n" +
			"   }\n" +
			"}";
		
		HashMap<String, HashMap<String, String>> theMap = (HashMap<String, HashMap<String, String>>) YAML.load(yaml);
		HashMap<String, TaskConfig> config = TaskConfig.parse(theMap);
		
		assertFalse(config.get("task1").isStatic());
		assertEquals(sampleTask, config.get("task1").getTaskClass());
		assertEquals(sampleInitializer, config.get("task1").getTaskInitializer());
	}
	
}
