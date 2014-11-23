package com.nuoshi.console.mapper;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.taofang.commons.exception.InternalException;
import com.nuoshi.console.domain.topic.Estate;

public class EstateMapper extends AbstractJsonParser<List<Estate>>{
	
	public List<Estate> parse(String json) {
		List<Estate> list = null;
		try {
			json = jsonMapper.readTree(json).findValue("docs").toString();
			list = jsonMapper.readValue(json, new TypeReference<List<Estate>>() {
			});
		} catch (Exception e) {
			throw new InternalException(e);
		}
		return list;
	}

}
