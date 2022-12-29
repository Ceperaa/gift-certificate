package ru.clevertec.ecl.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final String message = "  not found";
    private long id;

    public EntityNotFoundException(Class<?> object, long id) {
        super(object.getName() + " id: " + id + message);
        this.id = id;
    }

    public EntityNotFoundException(Class<?> object) {
        super(object.getName() + message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
