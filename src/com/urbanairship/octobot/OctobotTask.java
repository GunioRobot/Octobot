package com.urbanairship.octobot;

import org.json.simple.JSONObject;

// An OctobotTask is an object that 
// has a non-static run method.

// Non-static tasks are initialized by
// OctobotTaskInitializer objects.

// This will allow task behavior to be unit 
// tested without depending on any static 
// variables.

public interface OctobotTask {

	public void run(JSONObject object);
	
}
