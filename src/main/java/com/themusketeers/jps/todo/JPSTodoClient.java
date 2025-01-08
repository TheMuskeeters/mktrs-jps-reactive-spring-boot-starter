/*----------------------------------------------------------------------------*/
/* Source File:   JPSTODOCLIENT.JAVA                                          */
/* Copyright (c), 2024 The Musketeers                                         */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Apr.19/2024  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.themusketeers.jps.todo;

import static com.themusketeers.jps.common.constants.TodoClientConstants.TODOS_API;
import static com.themusketeers.jps.common.constants.TodoClientConstants.TODOS_API_ID;

import com.themusketeers.jps.JPSClient;
import com.themusketeers.jps.todo.model.Todo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implements a Json Place Holder (JPS) Rest Client.
 *
 * @author COQ - Carlos Adolfo Ortiz Q.
 */
public class JPSTodoClient implements JPSClient<Todo> {
    private final WebClient webClient;

    public JPSTodoClient(@Qualifier("jsonPlaceholderRestClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<Todo> findAll() {
        return webClient.get()
            .uri(TODOS_API)
            .retrieve()
            .bodyToFlux(new ParameterizedTypeReference<>() {
            });
    }

    @Override
    public Mono<Todo> findById(Integer id) {
        return webClient.get()
            .uri(TODOS_API_ID, id)
            .retrieve()
            .bodyToMono(Todo.class);
    }

    @Override
    public Mono<Todo> create(Todo todo) {
        return webClient.post()
            .uri(TODOS_API)
            .bodyValue(todo)
            .retrieve()
            .bodyToMono(Todo.class);
    }

    @Override
    public Mono<Todo> update(Integer id, Todo todo) {
        return webClient.put()
//            .uri(TODOS_API, id)
            .uri(TODOS_API_ID, id)
            .bodyValue(todo)
            .retrieve()
            .bodyToMono(Todo.class);
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(Integer id) {
        return webClient.delete()
            .uri(TODOS_API_ID, id)
            .retrieve()
            .toBodilessEntity();
    }
}
