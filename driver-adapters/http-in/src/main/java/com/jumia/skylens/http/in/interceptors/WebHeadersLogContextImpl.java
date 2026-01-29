package com.jumia.skylens.http.in.interceptors;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class WebHeadersLogContextImpl implements WebHeadersLogContext {

    private static final String EMPTY_VALUE = "";

    @Value("#{'${app.logging.web.context.headers:}'.split(',')}")
    private List<String> headers;

    @PostConstruct
    public void cleanList() {

        headers.remove(EMPTY_VALUE);
    }
}
