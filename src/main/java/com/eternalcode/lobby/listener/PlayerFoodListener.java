package com.eternalcode.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFoodListener implements Listener {

    @EventHandler
    void onPlayerFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
