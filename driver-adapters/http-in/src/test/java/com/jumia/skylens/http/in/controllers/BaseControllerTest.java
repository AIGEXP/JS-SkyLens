package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.fakers.RestFaker;
import jakarta.annotation.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.Field;

abstract class BaseControllerTest {

    @Resource
    protected MockMvc mvc;

    @Resource
    protected JsonMapper jsonMapper;

    protected final RestFaker restFaker = new RestFaker();

    protected String asJsonString(final Object object) {

        return jsonMapper.writeValueAsString(object);
    }

    protected LinkedMultiValueMap<String, String> getRequestAsParams(Object object) {

        final LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                final Object value = field.get(object);
                if (value instanceof Iterable) {
                    for (Object v : (Iterable<?>) value) {
                        multiValueMap.add(field.getName(), String.valueOf(v));
                    }
                } else if (value != null) {
                    multiValueMap.add(field.getName(), String.valueOf(value));
                }
            } catch (IllegalAccessException exception) {
                throw new RuntimeException("Failed to convert object to MultiValueMap", exception);
            }
        }
        return multiValueMap;
    }
}
