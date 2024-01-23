package com.eternalcode.lobby.configuration.serializer;

import com.eternalcode.lobby.feature.visibility.VisibilityItem;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VisibilityItemSerializer implements ObjectSerializer<VisibilityItem> {

    @Override
    public boolean supports(@NotNull Class<? super VisibilityItem> type) {
        return VisibilityItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull VisibilityItem object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.name);
        data.add("lore", object.lore);
        data.add("itemFlags", object.itemFlags);
        data.add("material", object.material);
    }

    @Override
    public VisibilityItem deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        List<String> lore = data.get("lore", List.class);
        List<ItemFlag> itemFlags = data.get("itemFlags", List.class);
        Material material = data.get("material", Material.class);

        return new VisibilityItem(name, lore, itemFlags, material);
    }

}
