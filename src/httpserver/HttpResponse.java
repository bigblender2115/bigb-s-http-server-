package httpserver;

import java.util.HashMap;
import java.util.Map;

/**
 * represents a http response back to the client
 * format:
 * HTTP/VERSION STATUSCODE STATUSMESSAGE
 * header: val
 * header: val
 *
 * body
 */
public class HttpResponse {
    private final int statusCode;
    private final String statusMessage;
    private String body;
    private final Map<String, String> headers;

    public HttpResponse(int statusCode, String statusMessage, String body) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.body = body != null ? body : "";
        this.headers = new HashMap<>();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    //http responses
    public static HttpResponse ok(String body) {
        return new HttpResponse(200, "OK", body);
    }

    public static HttpResponse created(String body) {
        return new HttpResponse(201, "Created", body);
    }

    public static HttpResponse badRequest(String body) {
        return new HttpResponse(400, "Bad Request", body);
    }
    public static HttpResponse notFound(String body) {
        return new HttpResponse(404, "Not Found", body);
    }

    public static HttpResponse internalError(String body) {
        return new HttpResponse(500, "Internal Server Error", body);
    }

}
