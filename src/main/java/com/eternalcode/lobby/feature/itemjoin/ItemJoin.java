package com.eternalcode.lobby.feature.itemjoin;

import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

@Contextual
public class ItemJoin {

    public String name;

    public int slot = 1;

    public Material material = Material.BARRIER;

    public List<String> lore = Collections.singletonList("lore");

    public String command;

    public ItemAction itemAction = ItemAction.NONE;
    public String texture;

    public ItemJoin(String name, int slot, Material material, List<String> lore, String command, ItemAction itemAction, String texture) {
        this.name = name;
        this.slot = slot;
        this.material = material;
        this.lore = lore;
        this.command = command;
        this.itemAction = itemAction;
        this.texture = texture;
    }

    public ItemJoin() {
    }
}
