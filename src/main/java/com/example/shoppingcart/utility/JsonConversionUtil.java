/**
 * 
 */
package com.example.shoppingcart.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Shakti
 *
 */
public class JsonConversionUtil {

	private JsonConversionUtil() {

	}

	public static String objectToJson(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> T objectFromJsonString(String content, Class<T> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(content, clazz);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
