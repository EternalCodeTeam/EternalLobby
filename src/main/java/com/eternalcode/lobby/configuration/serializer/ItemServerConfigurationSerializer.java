package com.eternalcode.lobby.configuration.serializer;

import com.eternalcode.lobby.feature.menu.ItemServerConfiguration;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemServerConfigurationSerializer implements ObjectSerializer<ItemServerConfiguration> {
    @Override
    public boolean supports(@NotNull Class<? super ItemServerConfiguration> type) {
        return ItemServerConfiguration.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull ItemServerConfiguration object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.name);
        data.add("slot", object.slot);
        data.add("material", object.material);
        data.add("lore", object.lore);
        data.add("server", object.server);
        data.add("glowItem", object.glowItem);
        data.add("texture", object.texture);
    }

    @Override
    public ItemServerConfiguration deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        int slot = data.get("slot", int.class);
        Material material = data.get("material", Material.class);
        List<String> lore = data.get("lore", List.class);
        String server = data.get("server", String.class);
        boolean glowItem = data.get("glowItem", boolean.class);
        String texture = data.get("texture", String.class);

        return new ItemServerConfiguration(name, slot, material, lore, server, glowItem, texture);
    }

}
