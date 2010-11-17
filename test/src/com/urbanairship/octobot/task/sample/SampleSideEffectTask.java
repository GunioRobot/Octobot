package com.urbanairship.octobot.task.sample;

import org.json.simple.JSONObject;

import com.urbanairship.octobot.task.OctobotTask;

public class SampleSideEffectTask implements OctobotTask {

	private static boolean initializeCalled = false;
	private static boolean runCalled = false;
	
	public static void reset() {
		initializeCalled = false;
		runCalled = false;
	}

	public static boolean wasInitializeCalled() {
		return initializeCalled;
	}

	public static boolean wasRunCalled() {
		return runCalled;
	}

	@Override
	public void run(JSONObject object) {
		SampleSideEffectTask.runCalled = true;
	}
	
	public void initialize() {
		SampleSideEffectTask.initializeCalled = true;
	}
	
}
