package com.nuoshi.console.mapper;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class AbstractJsonParser<T>{
	protected static ObjectMapper jsonMapper;
	protected static Logger logger = Logger.getLogger(AbstractJsonParser.class);
	static {
		// 初始化参数配置
		jsonMapper = new ObjectMapper();
		jsonMapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
