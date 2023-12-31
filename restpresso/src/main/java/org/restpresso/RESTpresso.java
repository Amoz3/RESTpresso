package org.restpresso;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import org.restpresso.handler.RequestHandler;
import org.restpresso.http.HTTPRequest;
import org.restpresso.log.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class RESTpresso {
    RouteMap routes = new RouteMap();
    ExecutorService threadPool;


    public RESTpresso(int threadCount) {
        threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public RESTpresso addGET(String route, RequestHandler handler) {
        routes.put("GET" + route, handler);
        return this;
    }

    public RESTpresso addPOST(String route, RequestHandler handler) {
        routes.put("POST" + route, handler);
        return this;
    }

    public RESTpresso addHEAD(String route, RequestHandler handler) {
        routes.put("HEAD" + route, handler);
        return this;
    }

    public RESTpresso addDELETE(String route, RequestHandler handler) {
        routes.put("DELETE" + route, handler);
        return this;
    }

    public RESTpresso addCONNECT(String route, RequestHandler handler) {
        routes.put("CONNECT" + route, handler);
        return this;
    }

    public RESTpresso addOPTIONS(String route, RequestHandler handler) {
        routes.put("OPTIONS" + route, handler);
        return this;
    }

    public RESTpresso addTRACE(String route, RequestHandler handler) {
        routes.put("TRACE" + route, handler);
        return this;
    }

    public RESTpresso addPATCH(String route, RequestHandler handler) {
        routes.put("PATCH" + route, handler);
        return this;
    }

    public RESTpresso addGET(String route, Consumer<HTTPRequest> consumer) {
        routes.put("GET" + route, new RequestHandler(consumer));
        return this;
    }

    public RESTpresso addPOST(String route, Consumer<HTTPRequest> consumer) {
        routes.put("POST" + route, new RequestHandler(consumer));
        return this;
    }

    public RESTpresso addHEAD(String route, Consumer<HTTPRequest> consumer) {
        routes.put("HEAD" + route, new RequestHandler(consumer));
        return this;
    }

    public RESTpresso addDELETE(String route, Consumer<HTTPRequest> consumer) {
        routes.put("DELETE" + route, new RequestHandler(consumer));
        return this;
    }

    public RESTpresso addCONNECT(String route, Consumer<HTTPRequest> consumer) {
        routes.put("CONNECT" + route, new RequestHandler(consumer));
        return this;
    }

    public RESTpresso addOPTIONS(String route, Consumer<HTTPRequest> consumer) {
        routes.put("OPTIONS" + route, new RequestHandler(consumer));
        return this;
    }

    public RESTpresso addTRACE(String route, Consumer<HTTPRequest> consumer) {
        routes.put("TRACE" + route, new RequestHandler(consumer));
        return this;
    }

    public RESTpresso addPATCH(String route, Consumer<HTTPRequest> consumer) {
        routes.put("PATCH" + route, new RequestHandler(consumer));
        return this;
    }


    public void start(int port) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 1);

            httpServer.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) {
                    String method = exchange.getRequestMethod();
                    String path = exchange.getRequestURI().toString();
                    // combine the two to filter endpoints by method

                    String route = String.format("%s%s", method, path);
                    if (routes.containsKey(route)) {
                        Log.info("%s request made to %s", method, route);
                        threadPool.submit(routes.get(route).getRunnable(exchange));
                    } else {
                        Log.warn("%s request made to %s & was not found", method, route);
                        new HTTPRequest(exchange).respond(404, "Endpoint not found");
                    }
                }
            });

            httpServer.start();
        } catch (IOException e) {
            Log.warn("Failed to start http server");
            throw new RuntimeException(e);
        }
    }

    public void startTLS(int port) {
        try {
            HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress(port), 1);
            httpsServer.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) {
                    String method = exchange.getRequestMethod();
                    String path = exchange.getRequestURI().toString();
                    // combine the two to filter endpoints by method

                    String route = String.format("%s%s", method, path);
                    if (routes.containsKey(route)) {
                        Log.info("%s request made to %s", method, route);
                        threadPool.submit(routes.get(route).getRunnable(exchange));
                    } else {
                        Log.warn("%s request made to %s & was not found", method, route);
                        new HTTPRequest(exchange).respond(404, "Endpoint not found");
                    }
                }
            });

            httpsServer.start();
        } catch (IOException e) {
            Log.warn("Failed to start http server");
            throw new RuntimeException(e);
        }
    }
}
