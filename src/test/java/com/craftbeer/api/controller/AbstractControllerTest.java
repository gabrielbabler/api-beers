package com.craftbeer.api.controller;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    protected MockHttpServletResponse response;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    protected <T> T stringJsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    protected <T> List<T> stringJsonToList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, new TypeReference<List<T>>() {
                @Override
                public Type getType() {
                    return mapper.getTypeFactory().constructCollectionType(List.class, clazz);
                }
            });
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    protected String objectToStringJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}