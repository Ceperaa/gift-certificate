package ru.clevertec.ecl.cluster.interceptors;

import lombok.SneakyThrows;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class BodyInputStreamWrapper extends HttpServletRequestWrapper {

    private Object line;

    public BodyInputStreamWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (!getRequest().getInputStream().isFinished()) {
            stream();
        }
        return new ServletInputCashedStream(new ByteArrayInputStream(line.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public void setLine(Object line) {
        this.line = line;
    }

    @SneakyThrows
    private void stream() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getRequest().getInputStream()));
        line = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public class ServletInputCashedStream extends ServletInputStream {

        ByteArrayInputStream byteArrayInputStream;

        public ServletInputCashedStream(ByteArrayInputStream byteArrayInputStream) {
            this.byteArrayInputStream = byteArrayInputStream;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {
        }

        @Override
        public int read() {
            return byteArrayInputStream.read();
        }
    }
}
