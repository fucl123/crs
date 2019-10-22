package com.kzkj.config;

import lombok.Data;

@Data
public class RabbitConfigInfo 
{
	private String address;
	
	private String username;
	
	private String password;
	
	private String vhost;
	
	private int prefetchCount;
	
	private int concurrentConsumers;
	
	private int maxConcurrency;
}
