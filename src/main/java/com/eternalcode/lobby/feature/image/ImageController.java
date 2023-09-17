package com.eternalcode.lobby.feature.image;

import com.eternalcode.lobby.config.impl.PluginConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import panda.utilities.text.Formatter;
import com.eternalcode.lobby.util.ChatUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ImageController implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final Plugin plugin;
    private final BukkitScheduler scheduler;

    public ImageController(PluginConfiguration pluginConfiguration, Plugin plugin) {
        this.pluginConfiguration = pluginConfiguration;
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!this.pluginConfiguration.cleanAtJoin) {
            ChatUtil.clearChat(player);
        }

        if (this.pluginConfiguration.enableWelcomeMessage && !this.pluginConfiguration.welcomeMessageHeadDisplay) {
            this.sendWelcomeMessage(player);
        }

        if (this.isEnabledWelcomeHeadMessage()) {
            this.sendWelcomeHeadMessage(player);
        }
    }

    private void sendWelcomeMessage(Player player) {
        for (String message : this.pluginConfiguration.messageAfterJoin) {
            String coloredMessage = ChatUtil.colorAndApplyPlaceholders(player, message);

            player.sendMessage(coloredMessage);
        }
    }

    private boolean isEnabledWelcomeHeadMessage() {
        return this.pluginConfiguration.enableWelcomeMessage && this.pluginConfiguration.welcomeMessageHeadDisplay;
    }

    private void sendWelcomeHeadMessage(Player player) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            ImageMessage headImage = this.getPlayerHeadImage(player);

            List<String> messagesToApplyAfterHead = this.pluginConfiguration.messageAfterJoin.stream()
                .map(message -> ChatUtil.colorAndApplyPlaceholders(player, message))
                .toList();

            headImage
                .addText(messagesToApplyAfterHead)
                .send(player);
        });
    }

    private ImageMessage getPlayerHeadImage(Player player) {
        try {
            String playerUuid = player.getUniqueId().toString();

            Formatter formatter = new Formatter()
                .register("{UUID}", playerUuid)
                .register("{PLAYER}", player.getName());

            String rawApiUrl = formatter.format(this.pluginConfiguration.apiUrl);

            URL URL = new URL(rawApiUrl);

            HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            BufferedImage BufferedImage = ImageIO.read(connection.getInputStream());

            return new ImageMessage(BufferedImage, 8, ImageChar.BLOCK.getChar());
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
