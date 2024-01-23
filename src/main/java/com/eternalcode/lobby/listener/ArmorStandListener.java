package com.eternalcode.lobby.listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class ArmorStandListener implements Listener {

    private static final double KNOCKBACK_POWER = 0.5;
    private static final double KNOCKBACK_POWER_Y = 1.0;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();

        Predicate<Entity> filter = entity -> entity instanceof ArmorStand && entity.getBoundingBox().overlaps(player.getBoundingBox());

        for (Entity hitArmorStand : world.getNearbyEntities(location, 1, 2, 1, filter)) {
            Vector vector = location.toVector();
            Vector subtract = vector.subtract(hitArmorStand.getLocation().toVector());
            Vector multiply = subtract.normalize().multiply(KNOCKBACK_POWER).setY(KNOCKBACK_POWER_Y);

            player.setVelocity(multiply);
        }
    }

}
