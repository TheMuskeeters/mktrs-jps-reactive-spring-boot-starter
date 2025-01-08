/*----------------------------------------------------------------------------*/
/* Source File:   JPSTODOCLIENTTEST.JAVA                                      */
/* Copyright (c), 2025 The Musketeers                                         */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Jan.07/2025  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.themusketeers.jps.todo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.themusketeers.jps.common.config.JsonPlaceholderServiceConfiguration;
import com.themusketeers.jps.common.config.JsonPlaceholderServiceProperties;
import com.themusketeers.jps.todo.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

/**
 * Unit Test for JPSTodoClient.
 *
 * @author COQ - Carlos Adolfo Ortiz Q.
 */
class JPSTodoClientTest {
    public static final int TODO_ITEMS_EXPECTED = 200;
    public static final int TODO_ID_0 = 0;
    public static final int TODO_ID_1 = 1;
    public static final int TODO_ID_CREATED = 201;

    public static final String JSON_PLACEHOLDER_REST_CLIENT_BEAN = "jsonPlaceholderRestClient";
    public static final String JPS_TODO_CLIENT_BEAN = "jpsTodoClient";
    public static final String JSON_PLACEHOLDER_URL = "https://jsonplaceholder.typicode.com";
    public static final String JSON_PLACEHOLDER_SERVICE_BASE_URL_PORT_3000_URL = "json-placeholder-service.base-url=https://localhost:3000";
    public static final String LOCALHOST_PORT_3000_URL = "https://localhost:3000";
    public static final String TODO_TITLE = "delectus aut autem";
    public static final String TITLE_LOREN_IPSUN = "Loren Ipsun";
    public static final String TODO_ID_FIELD = "id";
    public static final String TODO_TITLE_UPDATED = "The Title updated";
    public static final String RECORD_NOT_FOUND_MESSAGE = "404 Not Found";

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(RestClientAutoConfiguration.class, JsonPlaceholderServiceConfiguration.class));

    @Test
    @DisplayName("Verify that the beans Todo Rest Client are present.")
    void shouldContainTodoRestClientBean() {
        contextRunner.run(context -> {
            assertThat(context.containsBean(JSON_PLACEHOLDER_REST_CLIENT_BEAN)).isTrue();
            assertThat(context.containsBean(JPS_TODO_CLIENT_BEAN)).isTrue();
        });
    }

    @Test
    @DisplayName("Verify the default Base URL is set.")
    void shouldContainDefaultBaseUrl() {
        contextRunner
            .run(context -> {
                assertThat(context).hasSingleBean(JsonPlaceholderServiceProperties.class);
                assertThat(context.getBean(JsonPlaceholderServiceProperties.class).baseUrl()).isEqualTo(JSON_PLACEHOLDER_URL);
            });
    }

    @Test
    @DisplayName("Verify we can set a base URL properly.")
    void shouldSetCustomBaseUrl() {
        contextRunner
            .withPropertyValues(JSON_PLACEHOLDER_SERVICE_BASE_URL_PORT_3000_URL)
            .run((context) -> {
                assertThat(context).hasSingleBean(JsonPlaceholderServiceProperties.class);
                assertThat(context.getBean(JsonPlaceholderServiceProperties.class).baseUrl()).isEqualTo(LOCALHOST_PORT_3000_URL);
            });
    }

    @Test
    @DisplayName("Verify that we can retrieve a list of TODOs")
    void shouldFindAllTodos() {
        contextRunner
            .run(context -> {
                var todoClient = context.getBean(JPSTodoClient.class);
                var todos = todoClient.findAll();

                StepVerifier.create(todos)
                    .expectNextCount(TODO_ITEMS_EXPECTED)
                    .verifyComplete();
            });
    }

    @Test
    @DisplayName("Verify we can retrieve a TODO using its 'id'")
    void shouldFindByIdIsFound() {
        contextRunner
            .run(context -> {
                var todoClient = context.getBean(JPSTodoClient.class);
                var expectedTodo = buildTodo();
                var todo = todoClient.findById(TODO_ID_1);

                assertThat(todo)
                    .isNotNull()
                    .isEqualTo(expectedTodo);
            });
    }

    @Test
    @DisplayName("Verify we cannot retrieve a TODO using its 'id'.")
    void shouldFindByIdIsNotFound() {
        contextRunner
            .run(context -> {
                var todoClient = context.getBean(JPSTodoClient.class);

                assertThatThrownBy(() -> todoClient.findById(TODO_ID_0))
                    .isInstanceOfSatisfying(HttpClientErrorException.class, e ->
                        assertThat(e.getStatusCode())
                            .isNotNull()
                            .satisfies(statusCode -> {
                                assertThat(statusCode.is4xxClientError()).isTrue();
                                assertThat(statusCode.value()).isEqualTo(HttpStatus.NOT_FOUND.value());
                            })).hasMessageContaining(RECORD_NOT_FOUND_MESSAGE);
            });
    }

    @Test
    @DisplayName("Verify we can create a TODO in the JSON place-holder API.")
    void shouldCreateATODORecord() {
        contextRunner
            .run(context -> {
                var todoClient = context.getBean(JPSTodoClient.class);
                var todo = buildTodoCreate();
                var todoCreated = todoClient.create(todo);

                assertThat(todoCreated)
                    .isNotNull()
                    .extracting(TODO_ID_FIELD)
                    .isEqualTo(TODO_ID_CREATED);
            });
    }

    @Test
    @DisplayName("Verify we can update a TODO in the JSON place-holder API.")
    void shouldUpdateATODORecord() {
        contextRunner
            .run(context -> {
                var todoClient = context.getBean(JPSTodoClient.class);
                var updateTodo = buildTodoUpdate();
                var updatedTodo = todoClient.update(TODO_ID_1, updateTodo);

                assertThat(updatedTodo)
                    .isNotNull()
                    .isEqualTo(updateTodo);
            });
    }

    @Test
    @DisplayName("Verify we can remove a TODO in the JSON place-holder API.")
    void shouldRemoveATODORecord() {
        contextRunner
            .run(context -> {
                var todoClient = context.getBean(JPSTodoClient.class);
                var removedTodo = todoClient.delete(TODO_ID_1);

                StepVerifier.create(removedTodo)
                    .assertNext(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    })
                    .verifyComplete();
            });
    }

    private Todo buildTodo() {
        return new Todo(TODO_ID_1, TODO_ID_1, TODO_TITLE, false);
    }

    private Todo buildTodoCreate() {
        return new Todo(1000, null, TITLE_LOREN_IPSUN, false);
    }

    private Todo buildTodoUpdate() {
        return new Todo(TODO_ID_1, TODO_ID_1, TODO_TITLE_UPDATED, false);
    }

}