package com.grupopan.featuredClientAlpha.config;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FeatureStore;
import org.ff4j.web.ApiConfig;
import org.ff4j.web.embedded.ConsoleServlet;
import org.ff4j.web.jersey2.store.FeatureStoreHttp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FeatureConfiguration {
	
	@Value("remoteStore")
	String remoteStore;

    @Bean
    public FF4j getFF4j() {
        FF4j ff4j = new FF4j();

        //Remote Store
        FeatureStore featureStore = new FeatureStoreHttp(remoteStore);
        ff4j.setFeatureStore(featureStore);

        //Create Features
        createFeature("example_feature", ff4j, featureStore);

        return ff4j;
    }

    private void createFeature(final String featureName, final FF4j ff4j, final FeatureStore featureStoreMongoDB) {
        if (!ff4j.getFeatureStore().exist(featureName)) {
            featureStoreMongoDB.create(new Feature(featureName, true));
        }
    }

    @Bean
    public ApiConfig getApiConfig(FF4j ff4j) {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setAuthenticate(false);
        apiConfig.setAutorize(false);
        apiConfig.setFF4j(ff4j);
        return apiConfig;
    }

    @Bean
    public ConsoleServlet getFF4JServlet(FF4j ff4j) {
        ConsoleServlet consoleServlet = new ConsoleServlet();
        consoleServlet.setFf4j(ff4j);
        return consoleServlet;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ConsoleServlet consoleServlet) {
        return new ServletRegistrationBean(consoleServlet, "/feature-console");
    }
}
