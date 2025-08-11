package org.example.api.controller;

import org.example.core.annotations.Controller;
import org.example.core.annotations.GetRoute;
import org.example.core.annotations.PostRoute;
import org.example.core.annotations.RequestMapping;
import org.example.core.model.HttpRequest;
import org.example.core.model.HttpResponse;

@Controller
@RequestMapping(path = "/api")
public class HelloController {

    @GetRoute(path = "/hello")
    public HttpResponse sayHello(HttpRequest request) {
        return new HttpResponse(200, "Hello from custom server!");
    }

    @PostRoute(path = "/echo")
    public HttpResponse echo(HttpRequest request) {
        String requestBody = request.getBody();
        return new HttpResponse(200, "You posted: " + requestBody);
    }
}
