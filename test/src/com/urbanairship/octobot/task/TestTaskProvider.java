package com.urbanairship.octobot.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.urbanairship.octobot.Queue;
import com.urbanairship.octobot.task.sample.SampleSideEffectTask;
import com.urbanairship.octobot.task.sample.SampleSideEffectTaskInitializer;

public class TestTaskProvider extends TaskTest {
	
	@Test
	public void createsStaticTaskWithoutMappingProvided() throws Exception {
		HashMap<String, TaskConfig> noMapping = new HashMap<String, TaskConfig>();
		TaskProvider tp = new TaskProvider(noMapping);

		assertThat(tp.getTask(TaskTest.WORKING_TASK), 
				   is(StaticTask.class));
	}
	
	@Test
	public void createsStaticTaskWithMappingProvided() throws Exception {
		TaskConfig staticConfig = new TaskConfig(TaskTest.WORKING_TASK);
		Map<String, TaskConfig> basicMapping = new HashMap<String, TaskConfig>();
		basicMapping.put(TaskTest.WORKING_TASK, staticConfig);
		
		TaskProvider tp = new TaskProvider(basicMapping);

		assertThat(tp.getTask(TaskTest.WORKING_TASK), 
				   is(StaticTask.class));
	}
	
	@Test
	public void createsNonStaticTaskWithMappingProvided() throws Exception {
		TaskConfig nonstaticConfig = new TaskConfig(TaskTest.SIDEEFFECT_TASK, 
													TaskTest.SIDEEFFECT_TASK_INITIALIZER);
		Map<String, TaskConfig> basicMapping = new HashMap<String, TaskConfig>();
		basicMapping.put(TaskTest.WORKING_TASK, nonstaticConfig);
		
		TaskProvider tp = new TaskProvider(basicMapping);

		assertThat(tp.getTask(TaskTest.WORKING_TASK), 
				   is(InitializedTask.class));
	}

	@Test
	public void onlyCreatesInitializerOnce() throws Exception {
		Map<String, TaskConfig> basicMapping = new HashMap<String, TaskConfig>();
		basicMapping.put("taskA", new TaskConfig(SIDEEFFECT_TASK, SIDEEFFECT_TASK_INITIALIZER));

		TaskProvider tp = new TaskProvider(basicMapping);;
		tp.getTask("taskA");
		tp.getTask("taskA");

		assertEquals(1, SampleSideEffectTaskInitializer.howManyTimesCreated());
		assertEquals(2, SampleSideEffectTask.howManyTimesCreated());
	}
}
