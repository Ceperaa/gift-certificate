package ru.clevertec.ecl.interceptors;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.util.Constant.REPEAT;

public abstract class AbstractInterceptor {

    @Value("${ports}")
    protected List<String> ports;

    @Value("${server.port}")
    protected String localServerPort;

    @SneakyThrows()
    protected void response(HttpServletResponse response, Object o) {
        try (PrintWriter printWriter = response.getWriter()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(o));
        }
    }

    protected boolean isRepeat(HttpServletRequest request) {
        return request.getHeader(REPEAT) != null;
    }

    protected String replacePort(HttpServletRequest request, String port) {
        return String.format("%s?%s", request.getRequestURL().toString().replace(localServerPort, port),
                Optional.ofNullable(request.getQueryString()).orElse(""));
    }
}
