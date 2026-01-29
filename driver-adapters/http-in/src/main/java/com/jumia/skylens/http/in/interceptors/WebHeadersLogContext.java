package com.jumia.skylens.http.in.interceptors;

import java.util.List;

public interface WebHeadersLogContext {

    String WEB_CONTEXT = "web-context-";

    List<String> getHeaders();

    default String buildContextName(String header) {

        return WEB_CONTEXT + header;
    }
}
