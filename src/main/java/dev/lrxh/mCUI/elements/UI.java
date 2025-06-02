package dev.lrxh.mCUI.elements;

import dev.lrxh.mCUI.MCUI;
import dev.lrxh.mCUI.pack.PackServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UI {
    private final List<Element> elements;
    private int CURRENT_UNICODE;
    private List<UUID> uuids;
    private PackServer packServer;
    private final MCUI plugin;
    private int port;
    private Path tempFolder;

    public UI(MCUI plugin) {
        this.elements = new ArrayList<>();
        this.CURRENT_UNICODE = 0xE000;
        this.uuids = new ArrayList<>();
        this.packServer = null;
        this.plugin = plugin;
    }

    public void load(int port) {
        this.port = port;
        try {
            this.tempFolder = Files.createTempDirectory("mcui-pack-" + port);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary directory", e);
        }

        generate(tempFolder.toFile());

        File zipFile = new File(tempFolder.toFile(), "pack.zip");
        zipFolder(tempFolder, zipFile.toPath());

        packServer = new PackServer(zipFile, port);
        packServer.start();
    }

    public void addViewer(UUID uuid) {
        if (packServer == null) {
            throw new IllegalStateException("Pack server is not initialized. Call load() first.");
        }

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        player.setResourcePack("http://localhost:" + port + "/pack.zip", (@org.jetbrains.annotations.Nullable byte[]) null, true);

        uuids.add(uuid);
    }

    public void removeViewer(UUID uuid) {
        if (packServer == null) {
            throw new IllegalStateException("Pack server is not initialized. Call load() first.");
        }

        Player player  =Bukkit.getPlayer(uuid);
        if (player == null) return;
        String url = "http://localhost:" + port + "/pack.zip";
        player.removeResourcePack(UUID.nameUUIDFromBytes(url.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        uuids.remove(uuid);
    }

    public void add(File image) {
        Element element = new Element(
                CURRENT_UNICODE++,
                image
        );

        elements.add(element);
    }

    private void generate(File outputFolder) {
        try {
            final int GLYPH_SIZE = 16;

            if (!outputFolder.exists() && !outputFolder.mkdirs()) {
                throw new IOException("Failed to create output directory");
            }

            Path fontFolder = outputFolder.toPath().resolve("assets/minecraft/font");
            Files.createDirectories(fontFolder);

            Path textureFolder = outputFolder.toPath().resolve("assets/minecraft/textures/custom");
            Files.createDirectories(textureFolder);

            StringBuilder fontJsonBuilder = new StringBuilder();
            fontJsonBuilder.append("{\n\t\"providers\": [\n");

            for (int i = 0; i < elements.size(); i++) {
                Element e = elements.get(i);
                BufferedImage img = ImageIO.read(e.getImage());

                if (img.getWidth() != GLYPH_SIZE || img.getHeight() != GLYPH_SIZE) {
                    BufferedImage resized = new BufferedImage(GLYPH_SIZE, GLYPH_SIZE, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = resized.createGraphics();
                    g.drawImage(img, 0, 0, GLYPH_SIZE, GLYPH_SIZE, null);
                    g.dispose();
                    img = resized;
                }

                String textureName = "icon_" + i + ".png";
                File textureFile = textureFolder.resolve(textureName).toFile();
                ImageIO.write(img, "png", textureFile);

                fontJsonBuilder.append("\t\t{\n")
                        .append("\t\t\t\"type\": \"bitmap\",\n")
                        .append("\t\t\t\"file\": \"minecraft:custom/").append(textureName).append("\",\n")
                        .append("\t\t\t\"ascent\": 8,\n")
                        .append("\t\t\t\"height\": 8,\n")
                        .append("\t\t\t\"chars\": [\"\\u").append(String.format("%04X", e.getUnicode())).append("\"]\n")
                        .append("\t\t}");

                if (i < elements.size() - 1) fontJsonBuilder.append(",");
                fontJsonBuilder.append("\n");
            }

            fontJsonBuilder.append("\t]\n}\n");

            Files.write(fontFolder.resolve("default.json"), fontJsonBuilder.toString().getBytes());

            String mcmeta = """
            {
              "pack": {
                "pack_format": 15,
                "description": "Auto-generated custom UI font pack"
              }
            }
            """;
            Files.write(outputFolder.toPath().resolve("pack.mcmeta"), mcmeta.getBytes());

            System.out.println("Dynamic resource pack generated at " + outputFolder.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zipFolder(Path sourceDirPath, Path zipPath) {
        try {
            try (var zs = new java.util.zip.ZipOutputStream(Files.newOutputStream(zipPath))) {
                Files.walk(sourceDirPath)
                        .filter(path -> !Files.isDirectory(path))
                        .forEach(path -> {
                            java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(sourceDirPath.relativize(path).toString().replace("\\", "/"));
                            try {
                                zs.putNextEntry(zipEntry);
                                Files.copy(path, zs);
                                zs.closeEntry();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("Failed to zip folder: " + e.getMessage());
        }
    }
}
