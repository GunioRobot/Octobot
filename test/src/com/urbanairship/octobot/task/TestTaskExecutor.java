package com.urbanairship.octobot.task;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.urbanairship.octobot.Queue;

public class TestTaskExecutor {

	private static final String NONEXISTANT_CLASS = "com.urbanairship.octobot.task.sample.NonexistingTask";
	private static final String EXISTING_CLASS = "com.urbanairship.octobot.task.sample.SampleTask";

	private Queue getMockQueueForMapping(Map<String, String> mapping) {
		Queue mockQueue = mock(Queue.class);
		when(mockQueue.getTasks()).thenReturn(mapping);
		return mockQueue;
	}
	
	@Test
	public void testCreatesSampleTask() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Queue mockQueue = getMockQueueForMapping(new HashMap<String, String>());
		
		TaskExecutor te = new TaskExecutor(mockQueue);

		try {
			te.execute(EXISTING_CLASS, 
					   new JSONObject());
		}
		catch (Exception e) {
			assertFalse(true);
		}
		
		// should not have thrown an exception
		assertTrue(true);
	}
	
	@Test
	public void testCantCreateNonexistentTask() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Queue mockQueue = getMockQueueForMapping(new HashMap<String, String>());
		TaskExecutor te = new TaskExecutor(mockQueue);
		
		try {
			te.execute(NONEXISTANT_CLASS,
					   new JSONObject());
		} catch (ClassNotFoundException e) {
			assertTrue(true);
			return;
		}
		
		assertTrue(false);
	}
	
	
	@Test
	public void testCreatesTaskWithMapping() {
		Map<String, String> basicMapping = new HashMap<String, String>();
		basicMapping.put("task1", EXISTING_CLASS);
		Queue mockQueue = getMockQueueForMapping(basicMapping);
		
		TaskExecutor te = new TaskExecutor(mockQueue);
		try {
			te.execute("task1", new JSONObject());
		} catch (Exception e) {
			assertFalse(true);
		}
		assertTrue(true);
	}
}
