package com.eternalcode.lobby.listener;

import com.eternalcode.lobby.configuration.implementation.LocationConfiguration;
import com.eternalcode.lobby.position.Position;
import com.eternalcode.lobby.position.PositionAdapter;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panda.std.Option;
import panda.utilities.text.Formatter;
import com.eternalcode.lobby.configuration.implementation.PluginConfiguration;
import com.eternalcode.lobby.util.RandomUtil;

public class PlayerJoinListener implements Listener {

    private final PluginConfiguration pluginConfiguration;

    private final LocationConfiguration locationsConfig;
    private final AudienceProvider audience;
    private final MiniMessage miniMessage;

    public PlayerJoinListener(PluginConfiguration pluginConfiguration, LocationConfiguration locationsConfig, AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.pluginConfiguration = pluginConfiguration;
        this.locationsConfig = locationsConfig;
        this.audience = audienceProvider;
        this.miniMessage = miniMessage;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Position lobby = this.locationsConfig.lobby;

        if (lobby.isNoneWorld()) {
            return;
        }

        Location convert = PositionAdapter.convert(lobby);
        player.teleport(convert);
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

        Formatter formatter = new Formatter()
            .register("{PLAYER}", player.getName());

        if (message.isEmpty()) {
            return;
        }

        this.audience.all().sendMessage(this.miniMessage.deserialize(formatter.format(message.get())));
    }
}
