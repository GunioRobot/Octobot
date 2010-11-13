package com.urbanairship.octobot;

import java.util.HashMap;

public class Queue {

    public String queueType;
    public String queueName;

    public String host;
    public Integer port;
    public String username;
    public String password;
    public String vhost;
	public HashMap<String, String> tasks;

    public Queue(String queueType, String queueName, String host, Integer port,
        String username, String password) {
    		this(queueType, queueName, host, port);
            this.username = username;
            this.password = password;
            this.vhost = "/";
    }

    public Queue(String queueType, String queueName, String host, Integer port) {
            this.queueType = queueType.toLowerCase();
            this.queueName = queueName;
            this.host = host;
            this.port = port;
            this.tasks = new HashMap<String, String>();
    }

    public Queue(HashMap<String, Object> config) {
    	this(((String) config.get("protocol")).toLowerCase(), 
    		 (String) config.get("name"),
    		 (String) config.get("host"),
    		 Integer.parseInt(((Long) config.get("port")).toString()));
    	
        this.vhost = (String) config.get("vhost");
        this.username = (String) config.get("username");
        this.password = (String) config.get("password");
        this.tasks = (HashMap<String, String>) config.get("tasks");
    }


    @Override
    public String toString() {
        return queueType + "/" + queueName + "/" + host + "/" + port + "/" +
            username + "/" + password + "/" + vhost;
    }

}

