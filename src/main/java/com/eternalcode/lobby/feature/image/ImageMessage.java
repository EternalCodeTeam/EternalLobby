package com.eternalcode.lobby.feature.image;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

class ImageMessage {
    private final Color[] colors = {
        new Color(0, 0, 0),
        new Color(0, 0, 170),
        new Color(0, 170, 0),
        new Color(0, 170, 170),
        new Color(170, 0, 0),
        new Color(170, 0, 170),
        new Color(255, 170, 0),
        new Color(170, 170, 170),
        new Color(85, 85, 85),
        new Color(85, 85, 255),
        new Color(85, 255, 85),
        new Color(85, 255, 255),
        new Color(255, 85, 85),
        new Color(255, 85, 255),
        new Color(255, 255, 85),
        new Color(255, 255, 255),
    };

    private final String[] lines;


    ImageMessage(BufferedImage image, int height, char imgChar) {
        Object[][] chatColors = toChatColorArray(image, height);
        lines = toImageMessage(chatColors, imgChar);
    }

    ImageMessage(ChatColor[][] chatColors, char ImageChar) {
        lines = toImageMessage(chatColors, ImageChar);
    }

    ImageMessage(String... imageLines) {
        lines = imageLines;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        AffineTransform af = new AffineTransform();
        af.scale(width / (double) originalImage.getWidth(), height / (double) originalImage.getHeight());

        AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(originalImage, null);
    }

    private static String[] toImageMessage(Object[][] colors, char imgChar) {
        String[] lines = new String[colors[0].length];
        for (int i = 0; i < colors[0].length; i++) {
            StringBuilder line = new StringBuilder();
            for (Object[] chatColors : colors) {
                Object color = chatColors[i];
                line.append((color != null) ? chatColors[i].toString() + imgChar : ImageChar.TRANSPARENT_CHAT.getChar());
            }
            lines[i] = line.toString() + ChatColor.RESET;

        }
        return lines;
    }

    private static double getDistance(Color color, Color color1) {
        double mean = (color.getRed() + color1.getRed()) / 2.0;

        // r - g - b
        double r = color.getRed() - color1.getRed();
        double g = color.getGreen() - color1.getGreen();
        int b = color.getBlue() - color1.getBlue();

        // weight r - g - b
        double weightR = 2 + mean / 256.0;
        double weightG = 4.0;
        double weightB = 2 + (255 - mean) / 256.0;

        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private static boolean areIdentical(Color color, Color color1) {
        return Math.abs(color.getRed() - color1.getRed()) <= 5
            && Math.abs(color.getGreen() - color1.getGreen()) <= 5
            && Math.abs(color.getBlue() - color1.getBlue()) <= 5;

    }

    public ImageMessage addText(List<String> texts) {
        for (int y = 0; y < lines.length; y++) {
            if (texts.size() > y) {
                lines[y] += " " + texts.get(y) + " ";
            }
        }
        return this;
    }

    private Object[][] toChatColorArray(BufferedImage image, int height) {
        double ratio = (double) image.getHeight() / image.getWidth();
        int width = Math.min(10, (int) (height / ratio));
        BufferedImage resized = resizeImage(image, width, height);

        int resizedWidth = resized.getWidth();
        int resizedHeight = resized.getHeight();
        Object[][] chatImg = new Object[resizedWidth][resizedHeight];

        for (int x = 0; x < resizedWidth; x++) {
            for (int y = 0; y < resizedHeight; y++) {
                int rgb = resized.getRGB(x, y);

                Object closest;

                try {
                    closest = net.md_5.bungee.api.ChatColor.of(new Color(rgb, true));
                }
                catch (NoSuchMethodError ignored) {
                    closest = getClosestNonRGBChatColor(new Color(rgb, true));
                }

                chatImg[x][y] = closest;
            }
        }
        return chatImg;
    }

    private ChatColor getClosestNonRGBChatColor(Color color) {
        int index = 0;
        double best = -1;

        if (color.getAlpha() < 128) {
            return null;
        }


        for (int i = 0; i < colors.length; i++) {
            if (areIdentical(colors[i], color)) {
                return ChatColor.values()[i];
            }
        }

        for (int i = 0; i < colors.length; i++) {
            double distance = getDistance(color, colors[i]);
            if (distance < best || best == -1) {
                best = distance;
                index = i;
            }
        }

        // Minecraft has 15 colors
        return ChatColor.values()[index];
    }

    public String[] getLines() {
        return lines;
    }

    public void send(Player player) {
        for (String line : lines) {
            player.sendMessage(line);
        }
    }
}
