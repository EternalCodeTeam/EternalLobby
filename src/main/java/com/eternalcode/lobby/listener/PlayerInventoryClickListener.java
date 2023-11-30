package com.eternalcode.lobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickListener implements Listener {

    @EventHandler
    public void onOpenInventory(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            if (player.hasPermission("lobby.bypass")) {
                event.setCancelled(false);
                return;
            }

            event.setCancelled(true);
        }
    }
}
