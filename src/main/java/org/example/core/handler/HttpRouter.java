package org.example.core.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpRouter {
    private final Map<String, RouteHandler> routeMap = new HashMap<>();

    public void addRoute(String method, String path, Object controller, Method handlerMethod){
        String key = method.toUpperCase() + " " +path;
        routeMap.put(key, new RouteHandler(controller, handlerMethod));
    }

    public RouteHandler findRouteHandler(String method, String path){
        String key = method.toUpperCase() + " " +path;
        return routeMap.get(key);
    }
}
