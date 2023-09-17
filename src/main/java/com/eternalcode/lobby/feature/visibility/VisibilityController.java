package com.eternalcode.lobby.feature.visibility;

import com.eternalcode.lobby.delay.Delay;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.time.Duration;
import java.util.UUID;

public class VisibilityController implements Listener {

    private final VisibilityService visibilityService;
    private final VisibilityConfiguration visibilityConfiguration;

    private final NamespacedKey shownItemKey;
    private final NamespacedKey hiddenItemKey;

    private final Delay<UUID> delay;

    public VisibilityController(VisibilityService visibilityService, VisibilityConfiguration visibilityConfiguration, Plugin plugin) {
        this.visibilityService = visibilityService;
        this.visibilityConfiguration = visibilityConfiguration;

        this.shownItemKey = new NamespacedKey(plugin, "unique-visiblity-shown-key");
        this.hiddenItemKey = new NamespacedKey(plugin, "unique-visiblity-hidden-key");

        this.delay = new Delay<>(this.visibilityConfiguration.visibility);
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.setHiddenItem(player);

        this.visibilityService.playerJoin(player);
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.visibilityService.playerQuit(player);
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        this.setHiddenItem(player);
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        if (this.isHiddenOrShownItem(currentItem)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onPlayerItemDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (this.isHiddenOrShownItem(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInHand = inventory.getItemInMainHand();

        if (this.isHiddenOrShownItem(itemInHand)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        EquipmentSlot hand = event.getHand();

        if (this.delay.hasDelay(player.getUniqueId())) {
            return;
        }

        if (hand != EquipmentSlot.HAND) {
            return;
        }

        if (item == null) {
            return;
        }

        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (container.has(this.hiddenItemKey, PersistentDataType.BYTE)) {
            this.visibilityService.hidePlayers(player);
            this.setShownItem(player);
        }

        if (container.has(this.shownItemKey, PersistentDataType.BYTE)) {
            this.visibilityService.showPlayers(player);
            this.setHiddenItem(player);
        }

        this.delay.markDelay(player.getUniqueId(), Duration.ofSeconds(3));
    }

    void setHiddenItem(Player player) {
        PlayerInventory inventory = player.getInventory();

        VisibilityItem hideItem = this.visibilityConfiguration.visibility.hideItem;;

        ItemStack itemStack = hideItem.asGuiItem(player).getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(this.hiddenItemKey, PersistentDataType.BYTE, (byte) 1);
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(this.visibilityConfiguration.visibility.slot, itemStack);
    }

    void setShownItem(Player player) {
        PlayerInventory inventory = player.getInventory();

        VisibilityItem showItem = this.visibilityConfiguration.visibility.showItem;

        ItemStack itemStack = showItem.asGuiItem(player).getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(this.shownItemKey, PersistentDataType.BYTE, (byte) 1);
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(this.visibilityConfiguration.visibility.slot, itemStack);
    }

    boolean isHiddenOrShownItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return false;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        return container.has(this.shownItemKey, PersistentDataType.BYTE) || container.has(this.hiddenItemKey, PersistentDataType.BYTE);
    }

}
