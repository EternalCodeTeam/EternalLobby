package com.eternalcode.lobby.feature.menu.serverselector;

import com.eternalcode.lobby.feature.menu.ConnectionManager;
import com.eternalcode.lobby.feature.menu.ItemServerConfig;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ServerSelectorGui {

    private final ServerSelectorConfiguration selectorConfig;
    private final ConnectionManager connectionManager;
    private final MiniMessage miniMessage;
    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final SkullAPI skullAPI;

    public ServerSelectorGui(ServerSelectorConfiguration serverSelectorConfiguration, ConnectionManager connectionManager, MiniMessage miniMessage, Plugin plugin, SkullAPI skullAPI) {
        this.selectorConfig = serverSelectorConfiguration;
        this.connectionManager = connectionManager;
        this.miniMessage = miniMessage;
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
        this.skullAPI = skullAPI;
    }

    public void openServerSelectorGui(Player player) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            Gui gui = Gui.gui()
                .rows(this.selectorConfig.settings.guiRows)
                .title(this.miniMessage.deserialize(this.selectorConfig.settings.guiTitle))
                .disableAllInteractions()
                .create();

            for (ItemServerConfig item : this.selectorConfig.settings.items.values()) {
                GuiItem guiItem = item.asGuiItem(player, skullAPI, event -> {
                    if (item.server == null) {
                        return;
                    }

                    if (item.server.equals("--close")) {
                        gui.close(player);
                        return;
                    }

                    this.connectionManager.connect(player, item.server);
                    gui.close(player);
                });

                gui.setItem(item.slot, guiItem);
            }

            if (this.selectorConfig.settings.fill.enableFillItems) {
                gui.getFiller().fill(this.selectorConfig.settings.fill.fillItems.stream()
                    .map(ItemBuilder::from)
                    .map(ItemBuilder::asGuiItem)
                    .toList());
            }

            scheduler.runTask(plugin, () -> gui.open(player));
        });

    }
}
