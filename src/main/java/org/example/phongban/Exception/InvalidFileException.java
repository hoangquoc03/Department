package org.example.phongban.Exception;

public class InvalidFileException
        extends RuntimeException {

    public InvalidFileException(String message) {
        super(message);
    }
}