package com.urbanairship.octobot.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.jvyaml.YAML;

public class TestTaskConfig {

	@Test
	@SuppressWarnings("unchecked")
	public void properlyParsesTaskConfigWithInitializer() {
		String sampleTask = "com.urbanairship.octobot.task.SampleTask";
		String sampleInitializer = "com.urbanairship.octobot.task.SampleTaskInitializer";
		
		String yaml = "tasks: {\n" +
			"   task1: {\n" +
			"      class: " + sampleTask + ",\n" +
			"      initializer: " + sampleInitializer + "\n" +
			"   }\n" +
			"}";
		
		Map<String, Map<String,String>> taskMap = ((Map) ((Map) YAML.load(yaml)).get("tasks"));
		
		HashMap<String, TaskConfig> config = TaskConfig.parse(taskMap);
		assertFalse(config.get("task1").isStatic());
		assertEquals(sampleTask, config.get("task1").getTaskClass());
		assertEquals(sampleInitializer, config.get("task1").getTaskInitializer());
	}
	
}
