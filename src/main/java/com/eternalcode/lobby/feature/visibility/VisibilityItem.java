package com.eternalcode.lobby.feature.visibility;

import com.eternalcode.lobby.adventure.AdventureLegacyColor;
import com.eternalcode.lobby.adventure.AdventureLegacyColorProcessor;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import com.eternalcode.lobby.util.ChatUtil;

import java.util.List;

@Contextual
public class VisibilityItem {

    @Exclude
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

    public GuiItem asGuiItem(Player player) {
        return this.asGuiItem(player, event -> {
        });
    }

    public GuiItem asGuiItem(Player player, GuiAction<InventoryClickEvent> action) {
        Component name = AdventureLegacyColor.RESET_ITEM.append(this.miniMessage.deserialize(this.name));
        List<Component> lore = this.lore.stream()
            .map(input -> this.miniMessage.deserialize(ChatUtil.colorAndApplyPlaceholders(player, input)))
            .toList();

        ItemFlag[] flags = this.itemFlags.toArray(new ItemFlag[0]);

        return ItemBuilder.from(this.material)
            .name(name)
            .lore(lore)
            .flags(flags)
            .asGuiItem(action);
    }

}
