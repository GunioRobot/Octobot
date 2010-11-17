package com.urbanairship.octobot.task.sample;

import org.json.simple.JSONObject;

import com.urbanairship.octobot.OctobotTask;

public class SampleSideEffectTask implements OctobotTask {

	private static boolean initializeCalled = false;
	private static boolean runCalled = false;
	private static int numCreated = 0;
	
	public static void reset() {
		initializeCalled = false;
		runCalled = false;
		numCreated = 0;
	}
	
	public SampleSideEffectTask() {
		++numCreated;
	}

	public static boolean wasInitializeCalled() {
		return initializeCalled;
	}

	public static boolean wasRunCalled() {
		return runCalled;
	}

	public static int howManyTimesCreated() {
		return numCreated;
	}
	
	@Override
	public void run(JSONObject object) {
		SampleSideEffectTask.runCalled = true;
	}
	
	public void initialize() {
		SampleSideEffectTask.initializeCalled = true;
	}
	
}
