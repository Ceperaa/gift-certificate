package ru.clevertec.ecl.cluster.commitLog.impl;

import org.springframework.beans.factory.annotation.Value;

import static ru.clevertec.ecl.util.Constant.HTTP_URL;

public abstract class AbstractLog {

    @Value("${server.port}")
    protected String localServerPort;

    protected String buildUrl(String hostPort, String entityName, String api) {
        return String.format(HTTP_URL, hostPort, entityName, api);
    }
}
