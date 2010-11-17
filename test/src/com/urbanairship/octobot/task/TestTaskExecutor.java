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

	@Before
	public void setUp() {
		SampleSideEffectTask.reset();
		SampleSideEffectTaskInitializer.reset();
	}
	
	@Test
	public void createsSampleTask() {
		Queue mockQueue = getMockQueueForMapping(new HashMap<String, TaskConfig>());
		
		TaskExecutor te = new TaskExecutor(mockQueue);

		try {
			te.execute(WORKING_TASK, 
					   new JSONObject());
		}
		catch (Exception e) {
			assertFalse(true);
		}
		
		// should not have thrown an exception
		assertTrue(true);
	}
	
	@Test
	public void cantCreateNonexistentTask() {
		Queue mockQueue = getMockQueueForMapping(new HashMap<String, TaskConfig>());
		TaskExecutor te = new TaskExecutor(mockQueue);
		
		try {
			te.execute(NONEXISTANT_CLASS, new JSONObject());
		} catch (ClassNotFoundException e) {
			assertTrue(true);
			return;
		}
		catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(false);
	}
	
	
	@Test
	public void createsTaskUsingMapping() {
		Map<String, TaskConfig> basicMapping = new HashMap<String, TaskConfig>();
		basicMapping.put("task1", new TaskConfig(WORKING_TASK));
		Queue mockQueue = getMockQueueForMapping(basicMapping);
		
		TaskExecutor te = new TaskExecutor(mockQueue);
		try {
			te.execute("task1", new JSONObject());
		} catch (Exception e) {
			assertFalse(true);
		}
		assertTrue(true);
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
	public void createsTaskButIgnoresMapping() {
		Map<String, TaskConfig> basicMapping = new HashMap<String, TaskConfig>();
		basicMapping.put("task1", new TaskConfig(WORKING_TASK));
		Queue mockQueue = getMockQueueForMapping(basicMapping);
		
		TaskExecutor te = new TaskExecutor(mockQueue);
		try {
			te.execute(WORKING_TASK, new JSONObject());
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(true);
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
	
	@Test
	public void runsTaskTwiceButOnlyCreatesInitializerOnce() throws Exception {
		Map<String, TaskConfig> basicMapping = new HashMap<String, TaskConfig>();
		basicMapping.put("taskA", new TaskConfig(SIDEEFFECT_TASK, SIDEEFFECT_TASK_INITIALIZER));
		Queue mockQueue = getMockQueueForMapping(basicMapping);
		
		TaskExecutor te = new TaskExecutor(mockQueue);
		te.execute("taskA", new JSONObject());
		te.execute("taskA", new JSONObject());
		
		assertTrue(SampleSideEffectTask.wasRunCalled());
		assertTrue(SampleSideEffectTask.wasInitializeCalled());
		assertEquals(1, SampleSideEffectTaskInitializer.howManyTimesCreated());
		assertEquals(2, SampleSideEffectTask.howManyTimesCreated());
	}
}
