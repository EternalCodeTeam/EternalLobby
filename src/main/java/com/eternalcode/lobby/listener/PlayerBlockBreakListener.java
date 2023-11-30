package com.eternalcode.lobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockBreakListener implements Listener {

    @EventHandler
    void onPlayerBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("lobby.bypass")) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }
}
