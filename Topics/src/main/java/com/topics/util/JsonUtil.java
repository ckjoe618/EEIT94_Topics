package com.topics.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

	private final static ObjectMapper objectMapper = 
            new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	/**.registerModule(new JavaTimeModule())
	    支援 Java 8 日期類型
	*/
	
	/**.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)  // 日期轉為 ISO 格式
	  	停用時間戳，預設日期轉為 ISO 格式
	*/
	/**.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		這個選項控制 當 JSON 內有未知屬性時，是否要拋出錯誤。
	 	true（預設值） → JSON 有不認識的欄位時，會拋出錯誤。
	 	false → JSON 可以有不認識的欄位，Json 只會解析匹配的欄位，不會報錯。
	 	適用場景:當 JSON 來源的欄位 可能會變動，但我們只關心特定欄位時，設定 false 可以避免因為額外的欄位導致解析失敗。
	*/

	private JsonUtil() {
		// 這個 私有建構子 代表 不允許外部建立 JsonUtil 的實例。
		// 這是一個 工具類（Utility Class），所有方法都是 static，不需要建立物件。
		// 透過 私有建構子，防止開發者寫出 new JsonUtil() 這樣的程式碼。
	}
	
	public static String toJson(Object obj) {
	    try {
	        return objectMapper.writeValueAsString(obj);
	    } catch (JsonProcessingException e) {
	    	e.printStackTrace();
	        return "";
	    }
	}
	
	public static <T> T toObject(String json, Class<T> objectClass) {
	    try {
	        return objectMapper.readValue(json, objectClass);
	    } catch (IOException e) {
	    	e.printStackTrace();
	        return null;
	    }
	}
}
