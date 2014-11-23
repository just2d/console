package com.nuoshi.console.service;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseCacheService {
	protected static Logger logger = Logger.getLogger(BaseCacheService.class);
	@Autowired
	protected MemcachedClient memCacheClient;

}
