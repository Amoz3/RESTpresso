package org.restpresso.http;

import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class HTTPRequest {
    @Getter
    HttpExchange exchange;

    public HTTPRequest(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public String getHeader(String headerName) {
        return exchange.getRequestHeaders().get(headerName).get(0);
    }

    public Map<String, List<String>> getHeaders() {
        return exchange.getRequestHeaders();
    }

    public void respond(int statusCode, String message) {
        byte[] response = message.getBytes();
        OutputStream os = exchange.getResponseBody();
        try {
            exchange.sendResponseHeaders(statusCode, response.length);
            os.write(response);
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
