package ru.clevertec.ecl.exception;

public class NodeNotActiveException extends RuntimeException {

    private static final String message = " not active";

    public NodeNotActiveException() {
        super("nodes: " + message);
    }

}
