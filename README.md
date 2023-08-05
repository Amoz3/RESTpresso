<img align="right" width="190px" src="./restpresso.png">


# Tiny Java http server for when you dont need all those damned beans.
## Example
```java
        // simple hello world request
        new RESTpresso(32) // thread count
                .addGET("/hello", new RequestHandler(req -> req.respond(200, "Hello world!")))
                .addGET("/hi", req -> {
                req.respond(200, "hello :)");
                })
                .start(8088); // port, this method is blocking
```
