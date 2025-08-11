package org.example.core.handler;

import org.example.core.model.HttpRequest;
import org.example.core.model.HttpResponse;
import org.example.core.model.MimeType;

public class HttpHandlerImpl implements HttpHandler{

    @Override
    public void handle(HttpRequest request, HttpResponse response){
        String contentType = MimeType.getMimeType(request.getPath());
        response.setBody("Hello");
    }
}
