package com.urbanairship.octobot.task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import com.urbanairship.octobot.Queue;

public class TaskTest {

	protected static final String NONEXISTANT_CLASS = "com.urbanairship.octobot.task.sample.NonexistingTask";
	protected static final String WORKING_TASK = "com.urbanairship.octobot.task.sample.SampleTask";
	protected static final String BROKEN_TASK = "com.urbanairship.octobot.task.sample.SampleNonRunnableTask";
	protected static final String SIDEEFFECT_TASK = "com.urbanairship.octobot.task.sample.SampleSideEffectTask";
	protected static final String SIDEEFFECT_TASK_INITIALIZER = "com.urbanairship.octobot.task.sample.SampleSideEffectTaskInitializer";
}