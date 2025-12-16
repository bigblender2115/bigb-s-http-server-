package httpserver;

import java.util.Map;
import java.util.HashMap;

/**
 * http request and its components
 * format:
 * METHOD PATH HTTP/VERSION
 * header: val
 * header: val
 *
 * optional
 */
public class HttpRequest {

    //final coz we dont change this info
    private final String method; //GET, POST, PUT, DELETE, basically http methods
    private final String path; //path as in page ig, like index.html
    private final String version; //HTTP/1.1
    private final Map <String, String> headers;
    private final String body; //content sent by POST or PUT requests


    public HttpRequest(String method, String path, String version, Map<String, String> headers, String body) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = headers != null ? headers : new HashMap<>();
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String getHeader(String name) {
        return headers.get(name.toLowerCase());
    }

    // METHOD  PATH  HTTP/VERSION
    @Override
    public String toString() {
        return method + " " + path + " " + version;
    }
}
