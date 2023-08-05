package org.restpresso;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.restpresso.handler.RequestHandler;
import org.restpresso.log.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RESTpresso {
    RouteMap routes = new RouteMap();
    ExecutorService threadPool;


    public RESTpresso(int threadCount) {
        threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public RESTpresso addRoute(String route, RequestHandler handler) {
        routes.put(route, handler);
        return this;
    }

    public void start(int port) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 1);

            httpServer.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) {
                    String route = exchange.getRequestURI().toString();
                    Log.info("Request made to " + route);
                    if (routes.containsKey(route)) {
                        threadPool.submit(routes.get(route).getRunnable(exchange));
                    }
                }
            });

            httpServer.start();
        } catch (IOException e) {
            Log.warn("Failed to start http server");
            throw new RuntimeException(e);
        }
    }
}
