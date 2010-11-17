package com.urbanairship.octobot.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.urbanairship.octobot.Queue;
import com.urbanairship.octobot.task.sample.SampleSideEffectTask;
import com.urbanairship.octobot.task.sample.SampleSideEffectTaskInitializer;

public class TestTaskExecutor extends TaskTest {

	protected Queue getMockQueueForMapping(Map<String, TaskConfig> mapping) {
		Queue mockQueue = mock(Queue.class);
		when(mockQueue.getTasks()).thenReturn(mapping);
		return mockQueue;
	}
	
	@Test
	public void createsTaskButFailsInvocation() {
		Queue mockQueue = getMockQueueForMapping(new HashMap<String, TaskConfig>());
		
		TaskExecutor te = new TaskExecutor(mockQueue);
		try {
			te.execute(BROKEN_TASK, new JSONObject());
		} catch (NoSuchMethodException e) {
			assertTrue(true);
			return;
		} catch (Exception e) {
			assertTrue(false);
		}
		assertTrue(false);
	}
	
	@Test
	public void properlyRunsTask() throws Exception {
		Map<String, TaskConfig> basicMapping = new HashMap<String, TaskConfig>();
		basicMapping.put("taskA", new TaskConfig(SIDEEFFECT_TASK, SIDEEFFECT_TASK_INITIALIZER));
		Queue mockQueue = getMockQueueForMapping(basicMapping);
		
		TaskExecutor te = new TaskExecutor(mockQueue);
		te.execute("taskA", new JSONObject());
		
		assertTrue(SampleSideEffectTask.wasRunCalled());
		assertTrue(SampleSideEffectTask.wasInitializeCalled());
	}
}
