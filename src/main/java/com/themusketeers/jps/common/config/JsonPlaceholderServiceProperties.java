/*----------------------------------------------------------------------------*/
/* Source File:   JSONPLACEHOLDERSERVICEPROPERTIES.JAVA                       */
/* Copyright (c), 2024 The Musketeers                                         */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Apr.19/2024  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.themusketeers.jps.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Holds configuration data.
 *
 * @param baseUrl Indicates the default value for the {@Code RestClient} URL.
 * @author COQ - Carlos Adolfo Ortiz Q.
 */
@ConfigurationProperties("json-placeholder-service")
public record JsonPlaceholderServiceProperties(@DefaultValue("https://jsonplaceholder.typicode.com") String baseUrl) {
}
