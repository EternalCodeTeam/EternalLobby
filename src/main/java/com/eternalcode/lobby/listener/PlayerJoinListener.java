package com.eternalcode.lobby.listener;

import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panda.std.Option;
import com.eternalcode.lobby.configuration.implementation.PluginConfiguration;
import com.eternalcode.lobby.util.RandomUtil;

public class PlayerJoinListener implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final AudienceProvider audience;
    private final MiniMessage miniMessage;

    public PlayerJoinListener(PluginConfiguration pluginConfiguration, AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.pluginConfiguration = pluginConfiguration;
        this.audience = audienceProvider;
        this.miniMessage = miniMessage;
    }

    @EventHandler
    void onPlayerJoinDisableJoinMessage(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    void setGameModeOnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    void onPlayerJoinSetFlight(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("lobby.join.fly") || player.isOp()) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    void onPlayerJoinSendWelcomeMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Option<String> message = RandomUtil.randomElement(this.pluginConfiguration.joinMessage);

        if (message.isEmpty()) {
            return;
        }

        String welcomeMessage = message.get()
            .replace("{PLAYER}", player.getName());
        this.audience.all().sendMessage(this.miniMessage.deserialize(welcomeMessage));
    }
}
