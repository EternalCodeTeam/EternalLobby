package com.eternalcode.lobby.listener;

import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import panda.std.Option;
import com.eternalcode.lobby.configuration.implementation.PluginConfiguration;
import com.eternalcode.lobby.util.RandomUtil;

public class PlayerQuitListener implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final MiniMessage miniMessage;
    private final AudienceProvider audience;

    public PlayerQuitListener(PluginConfiguration pluginConfiguration, MiniMessage miniMessage, AudienceProvider audience) {
        this.pluginConfiguration = pluginConfiguration;
        this.miniMessage = miniMessage;
        this.audience = audience;
    }


    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    void onPlayerQuitSendQuitMessage(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Option<String> message = RandomUtil.randomElement(this.pluginConfiguration.leaveMessage);

        if (message.isEmpty()) {
            return;
        }

        String quitMessage = message.get()
            .replace("{PLAYER}", player.getName());

        this.audience.all().sendMessage(this.miniMessage.deserialize(quitMessage));
    }
}
