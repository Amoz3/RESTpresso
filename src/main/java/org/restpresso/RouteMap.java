package org.restpresso;

import org.restpresso.handler.RequestHandler;

import java.util.HashMap;

/**
 * forces lower case strings
 */
public class RouteMap extends HashMap<String, RequestHandler> {
    public RequestHandler get(String key) {
        return super.get(key.toLowerCase());
    }

    @Override
    public RequestHandler put(String key, RequestHandler value) {
        return super.put(key.toLowerCase(), value);
    }

    public boolean containsKey(String key) {
        return super.containsKey(key.toLowerCase());
    }
}
