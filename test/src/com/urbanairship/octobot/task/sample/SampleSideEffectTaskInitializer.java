package com.urbanairship.octobot.task.sample;

import com.urbanairship.octobot.OctobotTask;
import com.urbanairship.octobot.OctobotTaskInitializer;

public class SampleSideEffectTaskInitializer implements OctobotTaskInitializer {

	private static int createdTimes = 0;

	public SampleSideEffectTaskInitializer() {
		SampleSideEffectTaskInitializer.createdTimes++;
	}
	
	@Override
	public void initialize(OctobotTask t) {
		SampleSideEffectTask task = (SampleSideEffectTask) t;
		task.initialize();
	}

	public static int howManyTimesCreated() {
		return SampleSideEffectTaskInitializer.createdTimes;
	}
	
	public static void reset() {
		SampleSideEffectTaskInitializer.createdTimes = 0;
	}
}
