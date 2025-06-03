package dev.lrxh.mCUI.pack;

import dev.lrxh.mCUI.MCUI;
import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AssetsLoader {
    private static int index;

    public static File load(String imageUrl) {
        try {
            BufferedImage image = ImageIO.read(new URL(imageUrl));
            File saveDirectory = new File(MCUI.getInstance().getDataFolder(), "assets");

            if (!saveDirectory.exists()) {
                if (!saveDirectory.mkdirs()) {
                    throw new IOException("Failed to create directory: " + saveDirectory.getAbsolutePath());
                }
            }

            File outputFile = new File(saveDirectory, "image_" + index++ + ".png");
            ImageIO.write(image, "png", outputFile);

            Bukkit.getLogger().info("[AssetsLoader] Loaded " + imageUrl);

            return outputFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to download image from URL: " + imageUrl, e);
        }
    }

}
