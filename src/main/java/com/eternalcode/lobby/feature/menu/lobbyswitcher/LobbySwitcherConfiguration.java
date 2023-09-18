package com.eternalcode.lobby.feature.menu.lobbyswitcher;

import com.eternalcode.lobby.feature.menu.ItemServerConfig;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import com.eternalcode.lobby.configuration.ReloadableConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbySwitcherConfiguration implements ReloadableConfig {

    @Description({
        "# ",
        "# LobbySwitcher configuration",
        "# "
    })
    public Settings settings = new Settings();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lobby-switcher.yml");
    }

    @Contextual
    public static class Settings {
        public FillSettings fill = new FillSettings();

        @Description({
            "# Title of the lobby switcher menu"
        })
        public String guiTitle = "Wybór lobby:";

        @Description({
            " ",
            "# Rows of the lobby switcher menu"
        })
        public int guiRows = 3;

        @Description({
            " ",
            "# Items of the lobby switcher menu"
        })
        public Map<Integer, ItemServerConfig> items = ImmutableMap.of(
            1, new ItemServerConfig("Lobby #0", 10, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby0", true, "none"),
            2, new ItemServerConfig("Lobby #1", 11, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby1", true, "none"),
            3, new ItemServerConfig("Lobby #2", 12, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby2", true, "none"),
            4, new ItemServerConfig("Lobby #3", 13, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby3", true, "none"),
            5, new ItemServerConfig("Lobby #4", 14, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby4", true, "none"),
            6, new ItemServerConfig("Lobby #5", 15, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby5", true, "none"),
            7, new ItemServerConfig("Lobby #6", 16, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby6", true, "none"));

        @Contextual
        public static class FillSettings {
            @Description({
                " ",
                "# Fill item options"
            })
            public boolean enableFillItems = true;

            public List<Material> fillItems = List.of(
                Material.PURPLE_STAINED_GLASS_PANE,
                Material.PINK_STAINED_GLASS_PANE
            );
        }
    }
}
