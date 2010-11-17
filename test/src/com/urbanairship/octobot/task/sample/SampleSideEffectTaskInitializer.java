package com.urbanairship.octobot.task.sample;

import com.urbanairship.octobot.task.OctobotTask;
import com.urbanairship.octobot.task.TaskInitializer;

public class SampleSideEffectTaskInitializer implements TaskInitializer {

	@Override
	public void initialize(OctobotTask t) {
		SampleSideEffectTask task = (SampleSideEffectTask) t;
		task.initialize();
	}	
}
