package dev.lrxh.mcui.elements;

import dev.lrxh.mcui.MCUI;
import dev.lrxh.mcui.util.NameUtils;
import dev.lrxh.mcui.util.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
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
    private final List<UUID> viewers;
    private final String name;
    private int CURRENT_UNICODE;

    public UI() {
        this.elements = new ArrayList<>();
        this.CURRENT_UNICODE = 0xE000;
        this.viewers = new ArrayList<>();
        this.name = NameUtils.get();
    }

    public void load() {
        Path tempFolder;
        try {
            tempFolder = Files.createTempDirectory(name);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary directory", e);
        }

        generate(tempFolder.toFile());

        File zipFile = new File(tempFolder.toFile(), "pack.zip");
        zipFolder(tempFolder, zipFile.toPath());

        MCUI.INSTANCE.getPackServer().addFile(name, zipFile);
    }

    public void addViewer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        player.setResourcePack(getUrl(), (@org.jetbrains.annotations.Nullable byte[]) null, true);
        viewers.add(uuid);
    }

    public void removeViewer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        player.removeResourcePack(UUID.nameUUIDFromBytes(getUrl().getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        viewers.remove(uuid);
    }

    public Element register(String name) {
        File image = new File(MCUI.INSTANCE.getDataFolder(), "assets/" + name);
        if (!image.exists()) {
            throw new IllegalArgumentException("Image file does not exist: " + image.getAbsolutePath());
        }

        return register(image);
    }

    public Element register(String name, int height, int ascent) {
        File image = new File(MCUI.INSTANCE.getDataFolder(), "assets/" + name);
        return register(image, height, ascent);
    }

    public Element register(File image) {
        return register(image, 8, 0);
    }

    public Element register(File image, int height, int ascent) {
        Element element = new Element(
                CURRENT_UNICODE++,
                image,
                height,
                ascent
        );

        elements.add(element);

        return element;
    }

    public String getUrl() {
        return "http://localhost:" + ServerUtils.getPort() + "/" + name;
    }

    private void generate(File outputFolder) {
        try {

            if (!outputFolder.exists() && !outputFolder.mkdirs()) {
                throw new IOException("Failed to create output directory");
            }

            Path fontFolder = outputFolder.toPath().resolve("assets/minecraft/font");
            Files.createDirectories(fontFolder);

            Path textureFolder = outputFolder.toPath().resolve("assets/minecraft/textures/custom");
            Files.createDirectories(textureFolder);

            StringBuilder fontJsonBuilder = new StringBuilder();
            fontJsonBuilder.append("""
                    {
                    \t"providers": [
                    \t\t{
                    \t\t\t"type": "space",
                    \t\t\t"advances": {
                    \t\t\t\t" ": 4,
                    \t\t\t\t"\\u0101": -1,
                    \t\t\t\t"\\u0102": -2,
                    \t\t\t\t"\\u0103": -3,
                    \t\t\t\t"\\u0104": -4,
                    \t\t\t\t"\\u0105": -5,
                    \t\t\t\t"\\u0106": -6,
                    \t\t\t\t"\\u0107": -7,
                    \t\t\t\t"\\u0108": -8,
                    \t\t\t\t"\\u0109": -9,
                    \t\t\t\t"\\u010a": -10,
                    \t\t\t\t"\\u010b": -11,
                    \t\t\t\t"\\u010c": -12,
                    \t\t\t\t"\\u010d": -13,
                    \t\t\t\t"\\u010e": -14,
                    \t\t\t\t"\\u010f": -15,
                    \t\t\t\t"\\u0110": -16,
                    \t\t\t\t"\\u0111": -17,
                    \t\t\t\t"\\u0120": -32,
                    \t\t\t\t"\\u0121": -33,
                    \t\t\t\t"\\u0130": -48,
                    \t\t\t\t"\\u0131": -49,
                    \t\t\t\t"\\u0140": -64,
                    \t\t\t\t"\\u0141": -65,
                    \t\t\t\t"\\u0201": 1,
                    \t\t\t\t"\\u0202": 2,
                    \t\t\t\t"\\u0203": 3,
                    \t\t\t\t"\\u0204": 4,
                    \t\t\t\t"\\u0205": 5,
                    \t\t\t\t"\\u0206": 6,
                    \t\t\t\t"\\u0207": 7,
                    \t\t\t\t"\\u0208": 8,
                    \t\t\t\t"\\u0209": 9,
                    \t\t\t\t"\\u020a": 10,
                    \t\t\t\t"\\u020b": 11,
                    \t\t\t\t"\\u020c": 12,
                    \t\t\t\t"\\u020d": 13,
                    \t\t\t\t"\\u020e": 14,
                    \t\t\t\t"\\u020f": 15,
                    \t\t\t\t"\\u0210": 16,
                    \t\t\t\t"\\u0220": 32,
                    \t\t\t\t"\\u0230": 48,
                    \t\t\t\t"\\u0240": 64
                    \t\t\t}
                    \t\t}""");

            if (!elements.isEmpty()) {
                fontJsonBuilder.append(",\n");
            }

            int i = 0;
            for (Element e : elements) {
                if (e.getImage() == null) continue;

                BufferedImage img = ImageIO.read(e.getImage());

                if (img.getHeight() > 64 || img.getWidth() > 64) {
                    throw new IllegalArgumentException("Image dimensions exceed 64x64: " + e.getImage().getAbsolutePath());
                }

                String textureName = "icon_" + i + ".png";
                File textureFile = textureFolder.resolve(textureName).toFile();
                ImageIO.write(img, "png", textureFile);

                fontJsonBuilder.append("\t\t{\n")
                        .append("\t\t\t\"type\": \"bitmap\",\n")
                        .append("\t\t\t\"file\": \"minecraft:custom/").append(textureName).append("\",\n")
                        .append("\t\t\t\"ascent\": ").append(e.getAscent()).append(",\n")
                        .append("\t\t\t\"height\": ").append(e.getHeight()).append(",\n")
                        .append("\t\t\t\"chars\": [\"\\u").append(String.format("%04X", e.getUnicode())).append("\"]\n")
                        .append("\t\t}");

                if (i < elements.size() - 1) fontJsonBuilder.append(",");
                fontJsonBuilder.append("\n");
                i++;
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
        } catch (IOException ignored) {
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

    public List<UUID> getViewers() {
        return viewers;
    }
}
