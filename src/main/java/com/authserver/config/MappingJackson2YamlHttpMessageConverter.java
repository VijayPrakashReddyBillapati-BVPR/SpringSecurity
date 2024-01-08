package com.authserver.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MappingJackson2YamlHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	MappingJackson2YamlHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.parseMediaType("application/x-yaml"));
    }
}
