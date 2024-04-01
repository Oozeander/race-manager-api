package com.oozeander.courses_manager_api.exception;

public class ParticipantsNumberNotLinearException extends RuntimeException {

    public ParticipantsNumberNotLinearException() {
        super("participants[].number must begin at 1, be distinct as well as have no holes in between");
    }
}
