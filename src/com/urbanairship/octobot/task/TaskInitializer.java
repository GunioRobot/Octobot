package com.urbanairship.octobot.task;

/* 
 * abstract interface that task initializers declared in 
 * the task mapping should implement 
 */
public interface TaskInitializer {

	public void initialize(Runnable t);
	
}
