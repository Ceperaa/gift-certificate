package ru.clevertec.ecl.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final String message = " not found";
    private long id;

    public EntityNotFoundException(Class<?> object, long id) {
        super(object.getName() + " id: " + id + message);
        this.id = id;
    }

    public EntityNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }

    public EntityNotFoundException(Class<?> object) {
        super(object.getName().concat(message));
    }

    public long getId() {
        return id;
    }
}
