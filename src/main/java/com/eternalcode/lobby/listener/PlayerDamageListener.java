package com.eternalcode.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    void onPlayerDamageByBlock(EntityDamageByBlockEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onPlayerItemDamage(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }
}
