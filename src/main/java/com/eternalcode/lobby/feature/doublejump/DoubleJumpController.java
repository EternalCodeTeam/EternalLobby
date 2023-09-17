package com.eternalcode.lobby.feature.doublejump;

import com.eternalcode.lobby.config.impl.PluginConfiguration;
import com.eternalcode.lobby.delay.Delay;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.UUID;

public class DoubleJumpController implements Listener {

    private final PluginConfiguration pluginConfig;
    private final Delay<UUID> delay;

    public DoubleJumpController(PluginConfiguration pluginConfig) {
        this.pluginConfig = pluginConfig;

        this.delay = new Delay<>(this.pluginConfig.doubleJump);
    }

    @EventHandler
    void onPlayerFlightToggle(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (this.delay.hasDelay(player.getUniqueId())) {
            event.setCancelled(true);

            return;
        }

        if (this.pluginConfig.doubleJump.doubleJumpEnabled) {
            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR || !event.isFlying()) {
                return;
            }

            event.setCancelled(true);

            player.setVelocity(player.getLocation().getDirection().multiply(this.pluginConfig.doubleJump.doubleJumpPower).setY(this.pluginConfig.doubleJump.doubleJumpPowerY));
            player.playSound(player.getLocation(), this.pluginConfig.doubleJump.doubleJumpSound, this.pluginConfig.doubleJump.doubleJumpVolume, this.pluginConfig.doubleJump.doubleJumpPitch);
        }

        this.delay.markDelay(player.getUniqueId());
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!this.pluginConfig.doubleJump.doubleJumpEnabled) {
            return;
        }

        player.setAllowFlight(true);
    }

    @EventHandler
    void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if (!this.pluginConfig.doubleJump.doubleJumpEnabled) {
            return;
        }

        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (!this.pluginConfig.doubleJump.doubleJumpEnabled) {
            return;
        }

        player.setAllowFlight(true);
    }

    @EventHandler
    void blockDamage(EntityDamageByBlockEvent event) {
        if (!this.pluginConfig.doubleJump.doubleJumpEnabled) {
            return;
        }

        event.setCancelled(true);
    }
}
