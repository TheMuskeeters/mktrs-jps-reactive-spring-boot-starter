/*----------------------------------------------------------------------------*/
/* Source File:   JSONPLACEHOLDERSERVICECONFIGURATION.JAVA                    */
/* Copyright (c), 2024 The Musketeers                                         */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Apr.19/2024  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.themusketeers.jps.common.config;

import static com.themusketeers.jps.common.constants.ConfigurationConstants.CONFIGURING_JPS_WITH_PROPERTIES;

import com.themusketeers.jps.todo.JPSTodoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Holds the bean creation to configure.
 *
 * @author COQ - Carlos Adolfo Ortiz Q.
 */
@AutoConfiguration
@EnableConfigurationProperties(JsonPlaceholderServiceProperties.class)
public class JsonPlaceholderServiceConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JsonPlaceholderServiceConfiguration.class);

    private final JsonPlaceholderServiceProperties jpsProperties;

    /**
     * Constructor with parameters.
     *
     * @param jpsProperties Includes a property based data reference.
     */
    public JsonPlaceholderServiceConfiguration(JsonPlaceholderServiceProperties jpsProperties) {
        log.info(CONFIGURING_JPS_WITH_PROPERTIES, jpsProperties);
        this.jpsProperties = jpsProperties;
    }

    /**
     * Builds a reference for the {@link JPSTodoClient}
     *
     * @param webClient Includes a reference to a {@link WebClient}
     * @return A reference to a {@link JPSTodoClient}
     */
    @Bean
    public JPSTodoClient jpsTodoClient(WebClient webClient) {
        return new JPSTodoClient(webClient);
    }

    /**
     * Builds a named reference to the Json Placeholder Service Rest Client.
     *
     * @param builder Includes a reference of a {@link WebClient} builder.
     * @return A reference to a {@link WebClient}
     */
    @Bean("jsonPlaceholderRestClient")
    public WebClient restClient(WebClient.Builder builder) {
        return builder
            .baseUrl(jpsProperties.baseUrl())
            .build();
    }
}
