package ru.clevertec.ecl.interceptors;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static ru.clevertec.ecl.util.Constant.ORDERS;
import static ru.clevertec.ecl.util.Constant.REPEAT;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestProcessing implements HandlerInterceptor {

    private final OrderServiceInterceptor orderInterceptorService;
    private final EntityServiceInterceptor interceptorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isSwaggerOrSequence(request)) {
            return true;
        }
        if (request.getRequestURI().contains(ORDERS)) {
            return orderProcess(request, response);
        }
        return entitiesProcess(request, response);
    }

    private boolean orderProcess(HttpServletRequest request, HttpServletResponse response) {
        if (isRepeat(request)) {
            return true;
        }
        orderInterceptorService.buildRequest(request, response);
        return false;
    }

    private boolean entitiesProcess(HttpServletRequest request, HttpServletResponse response) {
        if (isRepeat(request)) {
            return true;
        }
        return interceptorService.buildRequest(request, response);
    }

    @SneakyThrows()
    public static void response(HttpServletResponse response, Object o) {
        try (PrintWriter printWriter = response.getWriter()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(o));
        }
    }

    private boolean isSwaggerOrSequence(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("swagger") ||
                requestURI.contains("api/error") ||
                requestURI.contains("/sequence");
    }

    private boolean isRepeat(HttpServletRequest request) {
        return request.getHeader(REPEAT) != null;
    }
}
