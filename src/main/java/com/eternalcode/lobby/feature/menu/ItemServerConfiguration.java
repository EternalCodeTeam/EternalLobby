package com.eternalcode.lobby.feature.menu;

import com.eternalcode.lobby.adventure.AdventureLegacyColorProcessor;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.rollczi.liteskullapi.SkullData;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemServerConfiguration {

    private final MiniMessage miniMessage = MiniMessage.builder()
        .postProcessor(new AdventureLegacyColorProcessor())
        .build();

    public Material material;

    public String name;

    public List<String> lore;

    public boolean glowItem;

    public String texture = "none";

    public int slot = 1;

    public String server;

    public ItemServerConfiguration(String name, int slot, Material material, List<String> lore, String server, boolean glowItem, String texture) {
        this.name = name;
        this.slot = slot;
        this.material = material;
        this.lore = lore;
        this.server = server;
        this.texture = texture;
        this.glowItem = glowItem;
    }

    public ItemServerConfiguration() {
    }

    public GuiItem asGuiItem(Player player, SkullAPI skullAPI, GuiAction<InventoryClickEvent> action) {
        List<@Nullable Component> collect = this.lore.stream().map(input -> this.miniMessage.deserialize(PlaceholderAPI.setPlaceholders(player, input)))
            .toList();

        Component deserialize = this.miniMessage.deserialize(this.name);

        switch (this.texture) {
            case "{PLAYER_HEAD}" -> {
                SkullData skullData = skullAPI.awaitSkullData(player.getName(), 5, TimeUnit.SECONDS);
                return ItemBuilder.skull()
                    .name(deserialize)
                    .lore(collect)
                    .glow(this.glowItem)
                    .texture(skullData.getValue())
                    .asGuiItem(action);
            }

            case "none" -> {
                return ItemBuilder.from(this.material)
                    .name(deserialize)
                    .lore(collect)
                    .glow(this.glowItem)
                    .asGuiItem(action);
            }

            default -> {
                return ItemBuilder.skull()
                    .name(deserialize)
                    .lore(collect)
                    .glow(this.glowItem)
                    .texture(this.texture)
                    .asGuiItem(action);
            }
        }
    }

}
