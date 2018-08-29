package br.com.carrental.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Config class of the API
 *
 * @author David
 * */

@Configuration
public class APIConfig {

    /**
     * Method that generates an object Mapper to configure dates.
     *
     * @return ObjectMapper - Returns an ObjectMapper with the date format setted.
     * */
    @Bean
    public ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC-3"));
        objectMapper.setDateFormat(simpleDateFormat);
        return objectMapper;
    }
}
