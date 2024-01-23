package com.eternalcode.lobby.feature.visibility;

import com.eternalcode.lobby.adventure.AdventureLegacyColor;
import com.eternalcode.lobby.adventure.AdventureLegacyColorProcessor;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class VisibilityItem {


    private final MiniMessage miniMessage = MiniMessage.builder()
        .postProcessor(new AdventureLegacyColorProcessor())
        .build();

    public String name;

    public List<String> lore;

    public List<ItemFlag> itemFlags;

    public Material material;

    public VisibilityItem() {
    }

    public VisibilityItem(String name, List<String> lore, List<ItemFlag> itemFlags, Material material) {
        this.name = name;
        this.lore = lore;
        this.itemFlags = itemFlags;
        this.material = material;
    }

    public GuiItem asGuiItem() {
        return this.asGuiItem(event -> {});
    }

    public GuiItem asGuiItem(GuiAction<InventoryClickEvent> action) {
        Component name = AdventureLegacyColor.RESET_ITEM.append(this.miniMessage.deserialize(this.name));

        List<Component> lore = new ArrayList<>();

        for (String string : this.lore) {
            Component deserialize = this.miniMessage.deserialize(string);
            lore.add(deserialize);
        }

        ItemFlag[] flags = this.itemFlags.toArray(new ItemFlag[0]);

        return ItemBuilder.from(this.material)
            .name(name)
            .lore(lore)
            .flags(flags)
            .asGuiItem(action);
    }

}
