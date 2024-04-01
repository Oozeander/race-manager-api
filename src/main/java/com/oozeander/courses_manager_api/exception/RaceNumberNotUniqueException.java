package com.oozeander.courses_manager_api.exception;

public class RaceNumberNotUniqueException extends RuntimeException {

    public RaceNumberNotUniqueException() {
        super("Race must have a unique number");
    }
}
