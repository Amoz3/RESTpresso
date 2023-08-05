package org.restpresso;

import org.restpresso.handler.RequestHandler;
import org.restpresso.log.Log;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        new RESTpresso(24)
                .addGET("/cock", new RequestHandler(req -> {
                    System.out.println(req);
                    for (Map.Entry<String, List<String>> entry : req.getHeaders().entrySet()) {
                        Log.info("Header: %s Value %s", entry.getKey(), entry.getValue());
                    }

                    req.respond(200, "yup");
                }))
                .addGET("/penis", new RequestHandler(req -> {
                    System.out.println("penis request");
                    req.respond(201, "oh my gosh.");
                }))
                .start(8088);
    }
}
