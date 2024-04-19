/*----------------------------------------------------------------------------*/
/* Source File:   JPSCLIENT.JAVA                                              */
/* Copyright (c), 2024 The Musketeers                                         */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Apr.19/2024  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.themusketeers.jps;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Contract definition around the JSON PlaceHolder Service (JPS) Rest Client.
 *
 * @param <T> Indicates the model data to work with.
 * @author COQ - Carlos Adolfo Ortiz Q.
 */
public interface JPSClient<T> {
    /**
     * Looks for data in the data source about &lt;T&gt;.
     *
     * @return Return a list of &lt;T&gt; objects.
     */
    Flux<T> findAll();

    /**
     * Given the identifier, tries to look for a &lt;T&gt; that matches it.
     *
     * @param id Indicates the identifier to locate.
     * @return &lt;T&gt; if found, NULL otherwise.
     */
    Mono<T> findById(Integer id);

    /**
     * Insersts &lt;T&gt; in the dada source.
     *
     * @param t Indicates the information to add.
     * @return &lt;T&gt; data.
     */
    Mono<T> create(T t);

    /**
     * Process to modify the &lt;T&gt; data stored in the data source.
     *
     * @param id Indicates the Identifier to use for the operation.
     * @param t  Indicates the information to update.
     * @return &lt;T&gt; data.
     */
    Mono<T> update(Integer id, T t);

    /**
     * Removes the record from the data source whose {@code id} matches.
     *
     * @param id Indicates the identifier to locate.
     * @return A bodiless response for the operation.
     */
    Mono<ResponseEntity<Void>> delete(Integer id);
}
