package com.eternalcode.lobby;

import com.eternalcode.lobby.feature.menu.skinchange.SkinChangeInventory;
import com.eternalcode.lobby.notification.NotificationAnnouncer;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.eternalcode.lobby.configuration.ConfigManager;
import com.eternalcode.lobby.configuration.implementation.LocationConfiguration;
import com.eternalcode.lobby.position.PositionAdapter;

import java.util.concurrent.TimeUnit;

@Route(name = "lobby")
@Permission("lobby.admin")
class LobbyCommand {

    private final ConfigManager configManager;
    private final LocationConfiguration locationsConfig;
    private final NotificationAnnouncer notificationAnnouncer;

    private static final String RELOAD_MESSAGE = "<b><gradient:#29fbff:#38b3ff>Lobby:</gradient></b> <green>Lobby configs has ben successfully reloaded in %sms";
    private static final String SET_LOBBY_MESSAGE = "<b><gradient:#29fbff:#38b3ff>Lobby:</gradient></b> <green>Lobby location has been set";

    LobbyCommand(ConfigManager configManager, LocationConfiguration locationsConfig, NotificationAnnouncer notificationAnnouncer) {
        this.configManager = configManager;
        this.locationsConfig = locationsConfig;
        this.notificationAnnouncer = notificationAnnouncer;
    }

    @Execute(route = "reload")
    void reload(CommandSender commandSender) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        this.configManager.reload();

        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        this.notificationAnnouncer.sendMessage(commandSender, String.format(RELOAD_MESSAGE, millis));
    }

    @Execute(route = "set lobby")
    void setLobby(Player player) {
        this.locationsConfig.lobby = PositionAdapter.convert(player.getLocation());
        this.configManager.save(this.locationsConfig);

        this.notificationAnnouncer.sendMessage(player, SET_LOBBY_MESSAGE);
    }
}
