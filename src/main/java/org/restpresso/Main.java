package org.restpresso;

import org.restpresso.handler.RequestHandler;

public class Main {
    public static void main(String[] args) {
        new RESTpresso(24)
                .addGET("/cock", new RequestHandler(req -> {
                    System.out.println(req);
                    req.respond(200, "yup");
                }))
                .addGET("/penis", new RequestHandler(req -> {
                    System.out.println("penis request");
                    req.respond(201, "oh my gosh.");
                }))
                .start(8088);
    }
}
