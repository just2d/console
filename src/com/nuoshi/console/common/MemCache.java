package com.nuoshi.console.common;

import org.apache.log4j.Logger;

public class MemCache {
    private static Logger logger = Logger.getLogger(MemCache.class);
    public static Integer cacheTime ;
    static{
		try {
			if( Resources.getString("memcached.cache.time")==null){
				cacheTime = 0;
			}else{
				cacheTime = Integer.parseInt(Resources.getString("memcached.cache.time"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
    }
}
