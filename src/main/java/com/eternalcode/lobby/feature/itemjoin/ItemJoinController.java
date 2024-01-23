package com.eternalcode.lobby.feature.itemjoin;

import com.eternalcode.lobby.feature.menu.lobbyswitcher.LobbySwitcherInventory;
import com.eternalcode.lobby.feature.menu.serverselector.ServerSelectorInventory;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.rollczi.liteskullapi.SkullData;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemJoinController implements Listener {

    private final ItemJoinConfiguration itemJoinConfig;
    private final MiniMessage miniMessage;

    private final ServerSelectorInventory serverSelectorInventory;
    private final LobbySwitcherInventory lobbySwitcherInventory;
    private final SkullAPI skullAPI;

    public ItemJoinController(ItemJoinConfiguration itemJoinConfig, MiniMessage miniMessage, ServerSelectorInventory serverSelectorInventory, LobbySwitcherInventory lobbySwitcherInventory, SkullAPI skullAPI) {
        this.itemJoinConfig = itemJoinConfig;
        this.miniMessage = miniMessage;
        this.serverSelectorInventory = serverSelectorInventory;
        this.lobbySwitcherInventory = lobbySwitcherInventory;
        this.skullAPI = skullAPI;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.giveJoinItems(player);
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        this.giveJoinItems(player);
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        for (ItemJoin itemJoin : this.itemJoinConfig.settings.slots.values()) {
            if (event.getItem() != null && event.getItem().getType() == itemJoin.material) {

                event.setCancelled(true);

                switch (ItemAction.valueOf(itemJoin.itemAction.toString())) {
                    case OPEN_SERVER_SELECTOR -> this.serverSelectorInventory.openServerSelectorGui(player);
                    case OPEN_LOBBY_SWITCHER -> this.lobbySwitcherInventory.openLobbySwitcherGui(player);
                    case COMMAND -> player.performCommand(itemJoin.command);
                }
            }
        }
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        for (ItemJoin itemJoin : this.itemJoinConfig.settings.slots.values()) {
            if (event.getBlock().getType() == itemJoin.material && event.getItemInHand().getType() == itemJoin.material) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    void onPlayerInventoryClickEvent(InventoryClickEvent event) {
        for (ItemJoin itemJoin : this.itemJoinConfig.settings.slots.values()) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == itemJoin.material && event.getSlot() == itemJoin.slot) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    void onPlayerDropItem(PlayerDropItemEvent event) {
        for (ItemJoin itemJoin : this.itemJoinConfig.settings.slots.values()) {
            if (event.getItemDrop().getItemStack().getType() == itemJoin.material) {
                event.setCancelled(true);
            }
        }
    }

    void giveJoinItems(Player player) {
        Inventory inventory = player.getInventory();
        inventory.clear();

        for (ItemJoin itemJoin : this.itemJoinConfig.settings.slots.values()) {
            List<@Nullable Component> collect = itemJoin.lore.stream().map(lore -> this.miniMessage.deserialize(PlaceholderAPI.setPlaceholders(player, lore)))
                .toList();

            Component deserialize = this.miniMessage.deserialize(itemJoin.name);

            switch (itemJoin.texture) {
                case "none" -> {
                    ItemStack item = ItemBuilder.from(itemJoin.material)
                        .amount(1)
                        .name(deserialize)
                        .lore(collect)
                        .build();

                    inventory.setItem(itemJoin.slot, item);
                }

                case "{PLAYER_HEAD}" -> {
                    SkullData skullData = this.skullAPI.awaitSkullData(player.getName(), 5, TimeUnit.SECONDS);

                    ItemStack item = ItemBuilder.skull()
                        .amount(1)
                        .name(deserialize)
                        .lore(collect)
                        .texture(skullData.getValue())
                        .build();

                    inventory.setItem(itemJoin.slot, item);
                }

                default -> {
                    ItemStack item = ItemBuilder.skull()
                        .amount(1)
                        .name(deserialize)
                        .lore(collect)
                        .texture(itemJoin.texture)
                        .build();

                    inventory.setItem(itemJoin.slot, item);
                }
            }
        }
    }

}
