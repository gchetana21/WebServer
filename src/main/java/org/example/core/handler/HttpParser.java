package org.example.core.handler;

import org.example.core.model.HttpRequest;
import org.example.core.model.HttpResponse;
import org.example.core.model.MimeType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class HttpParser {

    private final HttpRouter router;

    public HttpParser(HttpRouter router) {
        this.router = router;
    }


    public byte[] httpRequestParser(String request) throws IOException {

        //parsing request line
        String[] lines = request.split("\r\n");
        if (lines.length == 0) {
            HttpResponse response =  new HttpResponse(400, "Bad Request");
            return response.build().getBytes(StandardCharsets.UTF_8);
        }
        String[] requestLine = lines[0].split(" ");
        if(requestLine.length < 2){
            HttpResponse response =  new HttpResponse(400, "Bad Request");
            return response.build().getBytes(StandardCharsets.UTF_8);
        }
        String method = requestLine[0];
        String path = requestLine[1];

        String contentType = MimeType.getMimeType(path);
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setPath(path);
        httpRequest.setMethod(method);

        //dynamic routing
        RouteHandler handlerOptional = router.findRouteHandler(method, path);
        if (handlerOptional != null ){
            try {
                HttpResponse responseBody = handlerOptional.invoke(httpRequest);
                return  responseBody.build().getBytes(StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return handleStaticFile(path);
    }

    private  byte[] handleStaticFile(String path) throws IOException {
        Path filePath = Paths.get("src/main/resources" + path);
        if (!Files.exists(filePath)) {
            return httpResponse(404, "File not found");
        }

        byte[] content = Files.readAllBytes(filePath);
        String contentType = MimeType.getMimeType(path);


        return content;
    }

    private byte[] httpResponse(int statusCode, String message) {
        return buildHttpResponse(statusCode, "text/plain", message.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] buildHttpResponse(int statusCode, String contentType, byte[] body) {
        String headers = "HTTP/1.1 " + statusCode + " OK\r\n" +
                "Content-Length: " + body.length + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n\r\n";

        ByteBuffer buffer = ByteBuffer.allocate(headers.getBytes(StandardCharsets.UTF_8).length + body.length);
        buffer.put(headers.getBytes(StandardCharsets.UTF_8));
        buffer.put(body);
        return buffer.array();
    }
}
