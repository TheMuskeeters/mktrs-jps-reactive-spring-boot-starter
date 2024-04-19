/*----------------------------------------------------------------------------*/
/* Source File:   TODO.JAVA                                                   */
/* Copyright (c), 2024 The Musketeers                                         */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Apr.19/2024  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.themusketeers.jps.todo.model;

/**
 * Represents a TODO information.
 *
 * @param userId    Includes the user identifier.
 * @param id        Includes the identifier for the record.
 * @param title     Includes a name of the action to do in the record.
 * @param completed Indicates if the task was performed.
 * @author COQ - Carlos Adolfo Ortiz Q.
 */
public record Todo(Integer userId, Integer id, String title, Boolean completed) {
}