package com.dto;

import java.util.HashMap;
import java.util.Map;

public class JsonWrapper {
	private Map<String, Object> json = new HashMap<>();
	
	public JsonWrapper put(String key, Object value) {
		json.put(key, value);
		return this;
	}
	
	public Map<String, Object> build() {
		return json;
	}
}
