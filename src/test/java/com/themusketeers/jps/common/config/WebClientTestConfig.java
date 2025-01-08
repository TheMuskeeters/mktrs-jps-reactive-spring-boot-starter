/*----------------------------------------------------------------------------*/
/* Source File:   WebClientTestConfig.JAVA                                    */
/* Copyright (c), 2025 The Musketeers                                         */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Jan.07/2025  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.themusketeers.jps.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class to provide a {@link WebClient.Builder} bean for use in tests.
 * This allows for the creation and customization of {@link WebClient} instances.
 * <p>
 * Example usage:
 * </p>
 * <pre>{@code
 * @SpringBootTest
 * @ContextConfiguration(classes = WebClientTestConfig.class)
 * public class MyServiceTest {
 *
 *     @Autowired
 *     private WebClient.Builder webClientBuilder;
 *
 *     @Test
 *     void testWebClient() {
 *         WebClient webClient = webClientBuilder.build();
 *         // Use the webClient in your test
 *     }
 * }
 * }</pre>
 *
 * @author COQ - Carlos Adolfo Ortiz Q.
 */
@Configuration
public class WebClientTestConfig {

    /**
     * Creates and provides a {@link WebClient.Builder} bean.
     * This builder can be used to create and configure {@link WebClient} instances.
     *
     * @return a {@link WebClient.Builder} instance
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
