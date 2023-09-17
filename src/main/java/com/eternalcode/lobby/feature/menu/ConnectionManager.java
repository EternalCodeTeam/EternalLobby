package com.eternalcode.lobby.feature.menu;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ConnectionManager {

    private final Plugin plugin;

    public ConnectionManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void connect(Player target, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Connect");
        output.writeUTF(server);

        target.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }
}
