package org.example.core.handler;

import org.example.core.annotations.Controller;
import org.example.core.annotations.GetRoute;
import org.example.core.annotations.PostRoute;
import org.example.core.annotations.RequestMapping;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public class RouteScanner {

    public static void scanAndRegister(String basePackage, HttpRouter router) {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class); // use a custom @Controller annotation
        for (Class<?> controllerClass : controllers) {
            try {
                Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
                scan(controllerInstance, router);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void scan(Object controllerInstance, HttpRouter router) {
        Class<?> instanceClass = controllerInstance.getClass();
        String basePath = "";
        if (instanceClass.isAnnotationPresent(RequestMapping.class)) {
            basePath = instanceClass.getAnnotation(RequestMapping.class).path();
        }

        for (Method method : instanceClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetRoute.class)) {
                GetRoute annotation = method.getAnnotation(GetRoute.class);
                String methodPath = annotation.path();
                String fullPath = (basePath + methodPath).replaceAll("//+", "/");
                router.addRoute("GET",fullPath, controllerInstance, method);
            } else if (method.isAnnotationPresent(PostRoute.class)) {
                PostRoute annotation = method.getAnnotation(PostRoute.class);
                String methodPath = annotation.path();
                String fullPath = (basePath + methodPath).replaceAll("//+", "/");
                router.addRoute("POST", fullPath, controllerInstance, method);
            }
        }
    }

}
