package com.eternalcode.lobby.feature.menu.skinchange;

import com.eternalcode.lobby.feature.menu.ConnectionService;
import com.eternalcode.lobby.feature.menu.ItemServerConfiguration;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SkinChangeInventory {

    private final SkinChangeConfiguration configuration;
    private final ConnectionService connectionService;

    private final Plugin plugin;
    private final BukkitScheduler scheduler;

    private final MiniMessage miniMessage;

    private final SkullAPI skullAPI;

    public SkinChangeInventory(SkinChangeConfiguration configuration, ConnectionService connectionService, Plugin plugin, BukkitScheduler scheduler, MiniMessage miniMessage, SkullAPI skullAPI) {
        this.configuration = configuration;
        this.connectionService = connectionService;
        this.plugin = plugin;
        this.scheduler = scheduler;
        this.miniMessage = miniMessage;
        this.skullAPI = skullAPI;
    }

    public void openSkinChangeGui(Player player) {
        scheduler.runTaskAsynchronously(plugin, () -> {
           Gui gui = Gui.gui()
               .rows(this.configuration.settings.guiRows)
               .title(this.miniMessage.deserialize(this.configuration.settings.guiTitle))
               .disableOtherActions()
               .disableItemPlace()
               .disableItemSwap()
               .disableItemTake()
               .disableItemDrop()
               .disableAllInteractions()
               .create();

            for (ItemServerConfiguration item : this.configuration.settings.items.values()) {
                GuiItem guiItem = item.asGuiItem(player, skullAPI, event -> {
                    if (item.server == null) {
                        return;
                    }

                    if (item.server.equals("--close")) {
                        gui.close(player);
                        return;
                    }

                    if (!item.command.isBlank()) {
                        Bukkit.dispatchCommand(player, item.command);
                        return;
                    }

                    this.connectionService.connect(player, item.server);
                    gui.close(player);
                });

                gui.setItem(item.slot, guiItem);
            }

            if (this.configuration.settings.fill.enableFillItems) {
                gui.getFiller().fill(this.configuration.settings.fill.fillItems.stream()
                    .map(ItemBuilder::from)
                    .map(ItemBuilder::asGuiItem)
                    .toList());
            }

            scheduler.runTask(plugin, () -> gui.open(player));
        });
    }
}
