package dev.lrxh.mcui.pack;

import io.netty.channel.ChannelHandlerContext;
import net.mcbrawls.inject.http.HttpByteBuf;
import net.mcbrawls.inject.http.HttpInjector;
import net.mcbrawls.inject.http.HttpRequest;
import net.mcbrawls.inject.spigot.InjectSpigot;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PackServer {

    private static final Map<String, File> filesToServe = new HashMap<>();

    public void start() {
        InjectSpigot.INSTANCE.registerInjector(new FileInjector());
        Bukkit.getLogger().info("[PackServer] Injector registered. Ready to serve files via Inject.");
    }

    public void addFile(String path, File file) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("[PackServer] Path must not be null or empty");
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        filesToServe.put(path, file);
        Bukkit.getLogger().info("[PackServer] Will serve " + file.getName() + " at path: " + path);
    }

    private static class FileInjector extends HttpInjector {
        @Override
        public HttpByteBuf intercept(ChannelHandlerContext ctx, HttpRequest request) {
            String requestPath = request.getRequestURI();
            File file = filesToServe.get(requestPath);

            if (file == null) {
                return null;
            }

            if (!file.exists()) {
                HttpByteBuf notFoundBuf = HttpByteBuf.httpBuf(ctx);
                notFoundBuf.writeStatusLine("1.1", 404, "Not Found");
                notFoundBuf.writeText("404 - File Not Found: " + requestPath);
                return notFoundBuf;
            }

            try {
                byte[] raw = Files.readAllBytes(file.toPath());

                HttpByteBuf buf = HttpByteBuf.httpBuf(ctx);
                buf.writeStatusLine("1.1", 200, "OK");
                buf.writeHeader("Content-Type", "application/zip");
                buf.writeHeader("Content-Length", String.valueOf(raw.length));
                buf.writeBytes(raw);

                return buf;
            } catch (IOException e) {
                HttpByteBuf errBuf = HttpByteBuf.httpBuf(ctx);
                errBuf.writeStatusLine("1.1", 500, "Internal Server Error");
                errBuf.writeText("[PackServer] Failed to read file: " + e.getMessage());
                return errBuf;
            }
        }
    }
}
