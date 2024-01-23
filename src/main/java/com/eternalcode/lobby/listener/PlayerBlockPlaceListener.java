package com.eternalcode.lobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceListener implements Listener {

    @EventHandler
    void onPlayerBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("lobby.place")) {
            event.setCancelled(true);
        }
    }
}
