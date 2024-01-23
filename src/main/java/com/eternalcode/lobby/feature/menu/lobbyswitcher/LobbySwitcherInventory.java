package com.eternalcode.lobby.feature.menu.lobbyswitcher;

import com.eternalcode.lobby.feature.menu.ConnectionService;
import com.eternalcode.lobby.feature.menu.ItemServerConfiguration;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class LobbySwitcherInventory {

    private final LobbySwitcherConfiguration lobbyConfig;
    private final ConnectionService connectionService;

    private final Plugin plugin;
    private final BukkitScheduler scheduler;

    private final MiniMessage miniMessage;

    private final SkullAPI skullAPI;

    public LobbySwitcherInventory(LobbySwitcherConfiguration lobbySwitcherConfig, ConnectionService connectionService, Plugin plugin, MiniMessage miniMessage, SkullAPI skullAPI) {
        this.lobbyConfig = lobbySwitcherConfig;
        this.connectionService = connectionService;
        this.plugin = plugin;
        this.miniMessage = miniMessage;
        this.scheduler = plugin.getServer().getScheduler();
        this.skullAPI = skullAPI;
    }

    public void openLobbySwitcherGui(Player player) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            Gui gui = Gui.gui()
                .rows(this.lobbyConfig.settings.guiRows)
                .title(this.miniMessage.deserialize(this.lobbyConfig.settings.guiTitle))
                .disableAllInteractions()
                .create();

            for (ItemServerConfiguration item : this.lobbyConfig.settings.items.values()) {
                GuiItem guiItem = item.asGuiItem(player, skullAPI, event -> {
                    if (item.server == null) {
                        return;
                    }

                    if (item.server.equals("--close")) {
                        gui.close(player);
                        return;
                    }

                    this.connectionService.connect(player, item.server);
                    gui.close(player);
                });

                gui.setItem(item.slot, guiItem);
            }

            if (this.lobbyConfig.settings.fill.enableFillItems) {
                gui.getFiller().fill(this.lobbyConfig.settings.fill.fillItems.stream()
                    .map(ItemBuilder::from)
                    .map(ItemBuilder::asGuiItem)
                    .toList());
            }

            scheduler.runTask(plugin, () -> gui.open(player));
        });
    }
}
