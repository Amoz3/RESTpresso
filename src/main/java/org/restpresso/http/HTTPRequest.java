package org.restpresso.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public <E> E marshalBody(E pojo) {
        InputStream inputStream = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        return new Gson().fromJson(body, (Class<E>) pojo.getClass());
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
