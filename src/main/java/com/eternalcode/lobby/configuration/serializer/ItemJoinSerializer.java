package com.eternalcode.lobby.configuration.serializer;

import com.eternalcode.lobby.feature.itemjoin.ItemAction;
import com.eternalcode.lobby.feature.itemjoin.ItemJoin;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemJoinSerializer implements ObjectSerializer<ItemJoin> {

    @Override
    public boolean supports(@NotNull Class<? super ItemJoin> type) {
        return ItemJoin.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull ItemJoin object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.name);
        data.add("slot", object.slot);
        data.add("material", object.material);
        data.add("lore", object.lore);
        data.add("command", object.command);
        data.add("itemAction", object.itemAction);
        data.add("texture", object.texture);
    }

    @Override
    public ItemJoin deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        int slot = data.get("slot", int.class);
        Material material = data.get("material", Material.class);
        List<String> lore = data.get("lore", List.class);
        String command = data.get("command", String.class);
        ItemAction itemAction = data.get("itemAction", ItemAction.class);
        String texture = data.get("texture", String.class);

        return new ItemJoin(name, slot, material, lore, command, itemAction, texture);
    }
}
