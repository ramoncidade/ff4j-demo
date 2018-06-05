package com.grupopan.featureserver.config;

import org.ff4j.FF4j;
import org.ff4j.web.ApiConfig;
import org.ff4j.web.ApiConfigBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@ConditionalOnClass({FF4j.class})
@ComponentScan(value = {"org.ff4j.spring.boot.web.api", "org.ff4j.services", "org.ff4j.aop", "org.ff4j.spring"})
public class FeatureConfiguration {

	@Bean
    @ConditionalOnMissingBean
    public FF4j getFF4j() {
		FF4j ff4j = new FF4j();
        return ff4j;
    }
}
