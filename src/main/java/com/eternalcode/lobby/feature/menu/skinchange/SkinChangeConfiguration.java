package com.eternalcode.lobby.feature.menu.skinchange;

import com.eternalcode.lobby.configuration.ReloadableConfig;
import com.eternalcode.lobby.feature.menu.ItemServerConfiguration;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkinChangeConfiguration implements ReloadableConfig {


    @Description({
        "# ",
        "# SkinChange configuration",
        "# "
    })
    public Settings settings = new Settings();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "skin-change.yml");
    }

    @Contextual
    public static class Settings {
        public FillSettings fill = new FillSettings();

        @Description({
            "# Title of the skin change menu"
        })
        public String guiTitle = "Darmowe skiny";

        @Description({
            " ",
            "# Rows of the skin changer menu"
        })
        public int guiRows = 5;

        @Description({
            " ",
            "# Items of the skin change menu"
        })
        public Map<Integer, ItemServerConfiguration> items = ImmutableMap.of(
            1, new ItemServerConfiguration("Skin #1", 10, Material.PLAYER_HEAD, new ArrayList<>(), "none", false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGZmZTIyZjBlOTc2ZDkwMmJlYTc4ZGUyMjVkN2I1NjEzOTE2ZmRkYjliMzYzZTQyY2JiZThkOGRlYTVmNjlmMCJ9fX0=", ""),
            7, new ItemServerConfiguration("Skin #2", 11, Material.PLAYER_HEAD, new ArrayList<>(), "none", false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGZmZTIyZjBlOTc2ZDkwMmJlYTc4ZGUyMjVkN2I1NjEzOTE2ZmRkYjliMzYzZTQyY2JiZThkOGRlYTVmNjlmMCJ9fX0=", ""));

        @Contextual
        public static class FillSettings {
            @Description({
                " ",
                "# Fill item options"
            })
            public boolean enableFillItems = false;

            public List<Material> fillItems = List.of(
                Material.PURPLE_STAINED_GLASS_PANE,
                Material.PINK_STAINED_GLASS_PANE
            );
        }
    }
}
