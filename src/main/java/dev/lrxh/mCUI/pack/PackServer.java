package dev.lrxh.mCUI.pack;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PackServer {
    private final Map<String, File> filesToServe;
    private final int port;

    public PackServer() {
        this.filesToServe = new HashMap<>();
        this.port = 80;
    }

    public void addFile(String path, File file) {
        filesToServe.put(path, file);
    }

    public void start() {
        Thread serverThread = new Thread(() -> {
            try {
                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

                for (Map.Entry<String, File> entry : filesToServe.entrySet()) {
                    String path = entry.getKey();
                    File file = entry.getValue();

                    server.createContext(path, new FileHandler(file));
                    System.out.println("[PackServer] Serving " + file.getName() + " at http://localhost:" + port + path);
                }

                server.setExecutor(null);
                server.start();
            } catch (IOException e) {
                System.err.println("[PackServer] Failed to start: " + e.getMessage());
            }
        });

        serverThread.setName("PackServer-Thread");
        serverThread.setDaemon(true);
        serverThread.start();
    }

    private static class FileHandler implements HttpHandler {
        private final File fileToServe;

        public FileHandler(File fileToServe) {
            this.fileToServe = fileToServe;
        }

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
