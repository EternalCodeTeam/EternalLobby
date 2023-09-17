package com.eternalcode.lobby.feature.visibility;

import com.eternalcode.lobby.notification.NotificationAnnouncer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VisibilityService {

    private final Set<UUID> hidden = new HashSet<>();
    private final NotificationAnnouncer notificationAnnouncer;
    private final VisibilityConfiguration visibilityConfiguration;

    private final Server server;
    private final Plugin plugin;

    public VisibilityService(Server server, Plugin plugin, NotificationAnnouncer notificationAnnouncer, VisibilityConfiguration visibilityConfiguration) {
        this.server = server;
        this.plugin = plugin;
        this.notificationAnnouncer = notificationAnnouncer;
        this.visibilityConfiguration = visibilityConfiguration;
    }

    public void hidePlayers(Player player) {
        UUID uuid = player.getUniqueId();

        for (Player onlinePlayer : this.server.getOnlinePlayers()) {
            player.hidePlayer(this.plugin, onlinePlayer);
        }

        this.hidden.add(uuid);
        this.notificationAnnouncer.sendMessage(player, this.visibilityConfiguration.visibility.hidePlayers);
    }

    public void showPlayers(Player player) {
        UUID uuid = player.getUniqueId();

        for (Player onlinePlayer : this.server.getOnlinePlayers()) {
            player.showPlayer(this.plugin, onlinePlayer);
        }

        this.hidden.remove(uuid);
        this.notificationAnnouncer.sendMessage(player, this.visibilityConfiguration.visibility.showPlayers);
    }

    public void playerQuit(Player player) {
        UUID uuid = player.getUniqueId();

        if (!this.hidden.contains(uuid)) {
            return;
        }

        for (Player onlinePlayer : this.server.getOnlinePlayers()) {
            player.showPlayer(this.plugin, onlinePlayer);
        }

        this.hidden.remove(uuid);
    }

    public void playerJoin(Player player) {
        for (UUID uuid : this.hidden) {
            Player hiddenPlayer = this.server.getPlayer(uuid);

            if (hiddenPlayer == null) {
                continue;
            }

            hiddenPlayer.hidePlayer(this.plugin, player);
        }
    }

    public Set<UUID> getHidden() {
        return Collections.unmodifiableSet(this.hidden);
    }
}
