package org.example.core.model;

public class HttpResponse {
    private int statusCode;
    private String contentType;
    private String statusText;
    private String body = "";

    public void setStatusCode(int status) {
        this.statusCode = status;
        this.statusText = resolveStatusText(statusCode);
    }
    public void setContentType(String type) { this.contentType = type; }
    public void setBody(String body) { this.body = body; }

    public HttpResponse(int status, String body){
        this.statusCode = status;
        this.statusText = resolveStatusText(statusCode);
        this.body = body;

    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
        sb.append("Content-Type: ").append(contentType).append("\r\n");
        sb.append("Content-Length: ").append(body.getBytes().length).append("\r\n");
        sb.append("Connection: close\r\n");
        sb.append("\r\n");
        sb.append(body);
        return sb.toString();
    }


    private String resolveStatusText(int code) {
        return switch (code) {
            case 200 -> "OK";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Unknown";
        };
    }

}
