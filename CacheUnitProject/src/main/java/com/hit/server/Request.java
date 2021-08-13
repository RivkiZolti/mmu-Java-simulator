package main.java.com.hit.server;

import java.io.Serializable;
import java.util.Map;

public class Request<T>
        extends Object
        implements Serializable {
    protected Map<String, String> headers;
    protected T body;

    /**
     * constructor
     * a simple class, without logic
     * Represents an orderly form of request
     * @param headers
     * @param body
     */
    public Request(Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "Request{" +
                "headers=" + headers +
                ", body=" + body +
                '}';
    }

}
