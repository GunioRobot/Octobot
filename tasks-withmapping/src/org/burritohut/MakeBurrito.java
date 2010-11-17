package org.burritohut;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.apache.log4j.Logger;

import com.urbanairship.octobot.OctobotTask;

public class MakeBurrito implements OctobotTask {
    
    private static Logger log = Logger.getLogger("Burritobot");
	private String name;

    public void initialize(String name) {
    	this.name = name;
    }
    
    public void run(JSONObject task) {
        JSONArray args =  (JSONArray) task.get("args");

        String meat  = (String) args.get(0);
        String beans = (String) args.get(1);
        String salsa = (String) args.get(2);
        
        log.info("Hello!  My name is " + name + ".  " +
        		 "Making a " + meat + " burrito with " +
        		 beans + " beans and " + salsa + " salsa!");
    }

}
