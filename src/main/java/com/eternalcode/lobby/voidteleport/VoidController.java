package com.eternalcode.lobby.voidteleport;

import com.eternalcode.lobby.config.impl.LocationConfiguration;
import com.eternalcode.lobby.config.impl.PluginConfiguration;
import com.eternalcode.lobby.notification.NotificationAnnouncer;
import com.eternalcode.lobby.position.Position;
import com.eternalcode.lobby.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidController implements Listener {

    private final LocationConfiguration locationsConfig;
    private final PluginConfiguration pluginConfig;

    private final NotificationAnnouncer notificationAnnouncer;

    public VoidController(LocationConfiguration locationsConfig, PluginConfiguration pluginConfig, NotificationAnnouncer notificationAnnouncer) {
        this.locationsConfig = locationsConfig;
        this.pluginConfig = pluginConfig;
        this.notificationAnnouncer = notificationAnnouncer;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getTo().getBlockY() > this.pluginConfig.voidTeleportHeight) {
            return;
        }

        Position lobby = this.locationsConfig.lobby;

        if (lobby.isNoneWorld()) {
            return;
        }

        Location convert = PositionAdapter.convert(lobby);
        player.teleport(convert);

        this.notificationAnnouncer.sendMessage(player, this.pluginConfig.voidTeleportMessage);
        player.setFallDistance(0.0f);
    }
}