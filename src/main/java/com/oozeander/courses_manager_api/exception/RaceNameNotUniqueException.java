package com.oozeander.courses_manager_api.exception;

public class RaceNameNotUniqueException extends RuntimeException {

    public RaceNameNotUniqueException() {
        super("Race must have a unique name");
    }
}
