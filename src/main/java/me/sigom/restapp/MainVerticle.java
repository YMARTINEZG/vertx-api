package me.sigom.restapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

    private final List<JsonObject> items = new ArrayList<>();
    @Override
    public void start() {
        // Create an HTTP server
        HttpServer server = vertx.createHttpServer();

        // Create a router to handle the HTTP requests
        Router router = Router.router(vertx);

        // Define the route for the root path
        router.get("/").handler(this::handleRoot);
        router.get("/q/health").handler(this::checkHealth);
        // Start the server and listen on port 8080
        server.requestHandler(router).listen(8080);
    }

    private void handleRoot(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        // Set the content type header
        response.putHeader("Content-Type", "text/html");

        // Send HTML content as the response
        response.end("<html><body><h1>Vert.x v1.0 application!, if you see this triggers is working yeahh!!</h1></body></html>");
    }

    private void checkHealth(RoutingContext ctx) {
        JsonObject item = new JsonObject();
        item.put("status", "healthy");
        item.put("today", LocalDate.now());
        item.put("healthy", 2);
        items.add(item);
        ctx.response()
                .putHeader("content-type", "application/json")
                .end(new JsonArray(items).encode());
    }
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
