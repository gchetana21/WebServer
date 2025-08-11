package org.example.core.handler;

import org.example.core.model.HttpRequest;
import org.example.core.model.HttpResponse;

import java.lang.reflect.Method;

public class RouteHandler {

    private final Object controller;
    private final Method method;

    public RouteHandler(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public HttpResponse invoke(HttpRequest request) throws Exception {
        return (HttpResponse) method.invoke(controller, request);
    }

}
