package com.nttdata.dockerized.postgresql.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}