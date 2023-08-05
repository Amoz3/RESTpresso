package org.example;

import org.restpresso.RESTpresso;
import org.restpresso.handler.RequestHandler;

public class Main {
    public static void main(String[] args) {
        // simple hello world request
        new RESTpresso(32) // thread count
                .addGET("/hello", new RequestHandler(req -> req.respond(200, "Hello world!")))
                .addGET("/hi", req -> {
                    req.respond(200, "hello :)");
                })
                .start(8080);
    }
}
