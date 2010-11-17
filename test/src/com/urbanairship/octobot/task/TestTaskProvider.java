package com.urbanairship.octobot.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

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
	
}
