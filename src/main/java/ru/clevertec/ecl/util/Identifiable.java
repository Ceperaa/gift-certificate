package ru.clevertec.ecl.util;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}
