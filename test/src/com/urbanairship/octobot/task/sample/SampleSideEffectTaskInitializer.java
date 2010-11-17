package com.urbanairship.octobot.task.sample;

import com.urbanairship.octobot.OctobotTask;
import com.urbanairship.octobot.OctobotTaskInitializer;

public class SampleSideEffectTaskInitializer implements OctobotTaskInitializer {

	@Override
	public void initialize(OctobotTask t) {
		SampleSideEffectTask task = (SampleSideEffectTask) t;
		task.initialize();
	}	
}
