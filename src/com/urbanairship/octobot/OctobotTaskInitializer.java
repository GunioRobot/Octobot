package com.urbanairship.octobot;


/* 
 * abstract interface that task initializers declared in 
 * the task mapping should implement 
 */
public interface OctobotTaskInitializer {

	public void initialize(OctobotTask t);
	
}
