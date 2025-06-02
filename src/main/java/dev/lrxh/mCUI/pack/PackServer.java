package dev.lrxh.mCUI.pack;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class PackServer {
    private final File fileToServe;
    private final int port;

    public PackServer(File fileToServe, int port) {
        this.fileToServe = fileToServe;
        this.port = port;
    }

    public void start() {
        Thread serverThread = new Thread(() -> {
            try {
                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/pack.zip", new FileHandler());
                server.setExecutor(null);
                server.start();

                System.out.println("[PackServer] Serving " + fileToServe.getName() + " on http://localhost:" + port + "/pack.zip");
            } catch (IOException e) {
                System.err.println("[PackServer] Failed to start: " + e.getMessage());
                e.printStackTrace();
            }
        });

        serverThread.setName("PackServer-Thread");
        serverThread.setDaemon(true);
        serverThread.start();
    }

    private class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!fileToServe.exists()) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }

            byte[] fileBytes = Files.readAllBytes(fileToServe.toPath());

            exchange.getResponseHeaders().set("Content-Type", "application/zip");
            exchange.sendResponseHeaders(200, fileBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(fileBytes);
            }
        }
    }
}

