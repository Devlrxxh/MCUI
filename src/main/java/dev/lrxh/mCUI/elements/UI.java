package dev.lrxh.mCUI.elements;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UI {
    private final List<Element> elements;
    private int CURRENT_UNICODE;


    public UI() {
        this.elements = new ArrayList<>();
        this.CURRENT_UNICODE = 0xE000;
    }

    public void add(File image) {
        Element element = new Element(
                CURRENT_UNICODE++,
                image
        );

        elements.add(element);
    }

    public void generate(File outputFolder) throws IOException {
        final int GLYPH_SIZE = 16;

        if (!outputFolder.exists() && !outputFolder.mkdirs()) {
            throw new IOException("Failed to create output directory");
        }

        // Create folder structure
        Path fontFolder = outputFolder.toPath().resolve("assets/minecraft/font");
        Files.createDirectories(fontFolder);

        Path textureFolder = outputFolder.toPath().resolve("assets/minecraft/textures/custom");
        Files.createDirectories(textureFolder);

        // Build JSON providers dynamically
        StringBuilder fontJsonBuilder = new StringBuilder();
        fontJsonBuilder.append("{\n\t\"providers\": [\n");

        for (int i = 0; i < elements.size(); i++) {
            Element e = elements.get(i);
            BufferedImage img = ImageIO.read(e.getImage());

            // Resize if needed
            if (img.getWidth() != GLYPH_SIZE || img.getHeight() != GLYPH_SIZE) {
                BufferedImage resized = new BufferedImage(GLYPH_SIZE, GLYPH_SIZE, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resized.createGraphics();
                g.drawImage(img, 0, 0, GLYPH_SIZE, GLYPH_SIZE, null);
                g.dispose();
                img = resized;
            }

            // Generate unique texture name
            String textureName = "icon_" + i + ".png";
            File textureFile = textureFolder.resolve(textureName).toFile();
            ImageIO.write(img, "png", textureFile);

            // Append JSON provider without "width"
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

        // Write font JSON
        Files.write(fontFolder.resolve("default.json"), fontJsonBuilder.toString().getBytes());

        // Write pack.mcmeta
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
    }

}