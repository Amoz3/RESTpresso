package org.restpresso.handler;

import com.sun.net.httpserver.HttpExchange;
import org.restpresso.http.HTTPRequest;

import java.util.function.Consumer;

public class RequestHandler {
    Consumer<HTTPRequest> consumer;

    public RequestHandler(Consumer<HTTPRequest> consumer) {
        this.consumer = consumer;
    }

    public Runnable getRunnable(HttpExchange exchange) {
        return () -> consumer.accept(new HTTPRequest(exchange));
    }
}
