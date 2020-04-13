package org.vc.task.vct01.rs.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Alexander Sosnin
 * @since 15.04.2019 12:55
 */
public class TestRsUtil {

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
																		 MediaType.APPLICATION_JSON.getSubtype(),
																		 StandardCharsets.UTF_8
	);

	private MockMvc mockMvc;

	public TestRsUtil(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public <T> T postObjToRs(String urlTemplate, T obj) {
		return sendObjToRs(post(urlTemplate), obj, null);
	}

	public <T> T putObjToRs(String urlTemplate, T obj) {
		return sendObjToRs(put(urlTemplate), obj, null);
	}

	public <T> T putObjToRs(String urlTemplate, Class<T> objType) {
		return sendObjToRs(put(urlTemplate), null, objType);
	}

	private <T> T sendObjToRs(MockHttpServletRequestBuilder requestBuilder, T obj, Class<T> objType) {
		try {
			if ( obj != null ) {
				requestBuilder.contentType(APPLICATION_JSON_UTF8).content(toString(obj));
			}
			String contentString = mockMvc.perform(requestBuilder)
										  .andExpect(status().isOk())
										  .andReturn().getResponse().getContentAsString();
			return contentString == null || contentString.isEmpty() ? null : toObj(objType != null ? objType : (Class<T>)obj.getClass(), contentString);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T getObjFromRs(String urlTemplate, Class<T> objType) {
		return getObjFromRs(get(urlTemplate), objType);
	}

	private <T> T getObjFromRs(MockHttpServletRequestBuilder requestBuilder, Class<T> objType) {
		String contentString = getContentFromRs(requestBuilder);
		return contentString == null || contentString.isEmpty() ? null : toObj(objType, contentString);
	}

	public <T> List<T> getObjListFromRs(String urlTemplate, Class<T> objType) {
		return getObjListFromRs(get(urlTemplate), objType);
	}

	private <T> List<T> getObjListFromRs(MockHttpServletRequestBuilder requestBuilder, Class<T> objType) {
		String contentString = getContentFromRs(requestBuilder);
		List list = toObj(List.class, contentString);
		ArrayList<T> res = new ArrayList<>();
		for ( Object o : list ) {
			res.add(toObj(objType, toString(o)));
		}
		return res;
	}

	private String getContentFromRs(MockHttpServletRequestBuilder requestBuilder) {
		try {
			return mockMvc.perform(requestBuilder)
						  .andExpect(status().isOk())
						  .andReturn().getResponse().getContentAsString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T toObj(Class<T> objType, String json) {
		try {
			return (new ObjectMapper()).readValue(json, objType);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> String toString(T obj) {
		try {
			return (new ObjectMapper()).writeValueAsString(obj);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
