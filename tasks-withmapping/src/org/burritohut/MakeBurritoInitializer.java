package org.burritohut;

import com.urbanairship.octobot.OctobotTask;
import com.urbanairship.octobot.OctobotTaskInitializer;

public class MakeBurritoInitializer implements OctobotTaskInitializer {

	private int serverIndex;
	private String[] servers;

	public MakeBurritoInitializer() {
		this.serverIndex = 0;
		this.servers = new String[] { "Peter", "Paul", "Mary" };
	}
	
	@Override
	public void initialize(OctobotTask t) {
		MakeBurrito mb = (MakeBurrito) t;
		mb.initialize(this.servers[serverIndex]);

		this.serverIndex = (this.serverIndex + 1) % this.servers.length;
	}
}
