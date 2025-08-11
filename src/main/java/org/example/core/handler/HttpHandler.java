package org.example.core.handler;

import org.example.core.model.HttpRequest;
import org.example.core.model.HttpResponse;

public interface HttpHandler {
    void handle(HttpRequest request, HttpResponse response);

}
