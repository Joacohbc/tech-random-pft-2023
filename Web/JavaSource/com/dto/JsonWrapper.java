package com.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonWrapper {
	private Map<String, Object> json = new HashMap<>();
	
	public JsonWrapper put(String key, Object value) {
		json.put(key, value);
		return this;
	}
	
	public Map<String, Object> build() {
		return json;
	}
	
	public String toJSONString() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(json);
	}
}
