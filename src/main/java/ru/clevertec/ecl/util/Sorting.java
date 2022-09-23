package ru.clevertec.ecl.util;

public enum Sorting {

    NAME("t.name"),
    CREATE_DATE("createDate"),
    NAME_AND_CREATE_DATE("t.name","createDate");


    private final String[] sort;

    Sorting(String... sort) {
        this.sort = sort;
    }

    public String[] getSort() {
        return sort;
    }
}
