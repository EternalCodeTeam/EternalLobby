package com.eternalcode.lobby.listener;

import com.eternalcode.lobby.feature.menu.serverselector.ServerSelectorGui;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerPortalListener implements Listener {

    private static final double KNOCKBACK_POWER = 2.5;
    private static final Location TARGET_LOCATION = new Location(null, -31, 70, 0); // wpisz tu docelowe koordynaty

    public final ServerSelectorGui serverSelectorGui;

    public PlayerPortalListener(ServerSelectorGui serverSelectorGui) {
        this.serverSelectorGui = serverSelectorGui;
    }

    @EventHandler
    void onPortalApproach(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (location.getBlock().getType() == Material.NETHER_PORTAL) {
            Vector direction = TARGET_LOCATION.toVector().subtract(player.getLocation().toVector()).normalize();
            player.setVelocity(direction.multiply(1).multiply(KNOCKBACK_POWER).setY(0.5));

            this.serverSelectorGui.openServerSelectorGui(player);

        }
    }
}
