package com.concurrencylabs;

import com.google.devtools.build.runfiles.Runfiles;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {
    public static void main(String[] args) throws Exception {
        Runfiles runfiles = Runfiles.create();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // API Endpoint for Java verification
        server.createContext("/api/hello", exchange -> {
            String response = Simulator.getHelloMessage();
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        // Static file handler for UI assets and index.html via Bazel Runfiles API
        server.createContext("/", exchange -> {
            String uriPath = exchange.getRequestURI().getPath();
            if (uriPath.equals("/")) {
                uriPath = "/index.html";
            }

            // Resolve path using runfiles API, checking repository namespace variants
            String runfilesRelativePath = "ui/dist" + uriPath;
            String resolvedPathStr = runfiles.rlocation("_main/" + runfilesRelativePath);
            if (resolvedPathStr == null || !Files.exists(Paths.get(resolvedPathStr))) {
                resolvedPathStr = runfiles.rlocation(runfilesRelativePath);
            }

            Path filePath = resolvedPathStr != null ? Paths.get(resolvedPathStr) : Paths.get("");

            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                String contentType = "text/plain";
                if (uriPath.endsWith(".html")) contentType = "text/html";
                else if (uriPath.endsWith(".js")) contentType = "application/javascript";
                else if (uriPath.endsWith(".css")) contentType = "text/css";

                byte[] response = Files.readAllBytes(filePath);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } else {
                // Fallback to index.html for SPA client-side routing via Runfiles
                String indexPathStr = runfiles.rlocation("_main/ui/dist/index.html");
                if (indexPathStr == null || !Files.exists(Paths.get(indexPathStr))) {
                    indexPathStr = runfiles.rlocation("ui/dist/index.html");
                }
                Path indexPath = indexPathStr != null ? Paths.get(indexPathStr) : Paths.get("");

                byte[] response = Files.exists(indexPath) 
                    ? Files.readAllBytes(indexPath) 
                    : "UI Bundle not found.".getBytes();
                
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            }
        });

        System.out.println("Server started on http://localhost:8080");
        server.start();
    }
}