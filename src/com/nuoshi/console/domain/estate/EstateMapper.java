package com.nuoshi.console.domain.estate;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.type.TypeReference;

import com.taofang.commons.exception.InternalException;
import com.nuoshi.console.mapper.AbstractJsonParser;

public class EstateMapper extends AbstractJsonParser<List<EstateSearch>>{
	private String json;
	private JsonNode jNode;
	private Integer totalNum;
	public EstateMapper(String json) {
		super();
		this.json = json;
	}
	private void init() throws JsonProcessingException, IOException{
		jNode = jsonMapper.readTree(json);
	}
	public List<EstateSearch> parse() {
		
		List<EstateSearch> list = null;
		try {
			if(json==null||json.length()==0){
				return null;
			}
			 init();
			json = jNode.findValue("docs").toString();
			list = jsonMapper.readValue(json, new TypeReference<List<EstateSearch>>() {
			});
		} catch (Exception e) {
			throw new InternalException(e);
		}
		return list;
	}
	public Integer getTotalNum(){
		if(jNode==null){
				return 0;
		}
		this.totalNum =  Integer.parseInt(jNode.findValue("numFound").toString());
		return this.totalNum;
	}

}
