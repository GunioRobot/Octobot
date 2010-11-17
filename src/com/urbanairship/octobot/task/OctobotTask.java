package com.urbanairship.octobot.task;

import org.json.simple.JSONObject;

public interface OctobotTask {

	public void run(JSONObject object);
	
}
