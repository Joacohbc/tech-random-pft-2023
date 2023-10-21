package com.utec.proyectofinaltecnicatura.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> json = new HashMap<>();
	
	public JsonDTO put(String key, Object value) {
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
