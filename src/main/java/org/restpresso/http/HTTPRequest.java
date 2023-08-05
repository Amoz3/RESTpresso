package org.restpresso.http;

import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;

public class HTTPRequest {
    @Getter
    HttpExchange exchange;

    public HTTPRequest(HttpExchange exchange) {
        this.exchange = exchange;
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
