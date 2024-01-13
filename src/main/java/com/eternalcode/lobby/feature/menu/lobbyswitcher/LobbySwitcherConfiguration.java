package com.eternalcode.lobby.feature.menu.lobbyswitcher;

import com.eternalcode.lobby.feature.menu.ItemServerConfiguration;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Names(modifier = NameModifier.TO_LOWER_CASE, strategy = NameStrategy.HYPHEN_CASE)
public class LobbySwitcherConfiguration extends OkaeriConfig {

    @Comment({
        "# ",
        "# LobbySwitcher configuration",
        "# "
    })
    public Settings settings = new Settings();


    public static class Settings extends OkaeriConfig {
        public FillSettings fill = new FillSettings();

        @Comment({
            "# Title of the lobby switcher menu"
        })
        public String guiTitle = "Wybór lobby:";

        @Comment({
            " ",
            "# Rows of the lobby switcher menu"
        })
        public int guiRows = 3;

        @Comment({
            " ",
            "# Items of the lobby switcher menu"
        })
        public Map<Integer, ItemServerConfiguration> items = ImmutableMap.of(
            1, new ItemServerConfiguration("Lobby #0", 10, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby0", true, "none"),
            2, new ItemServerConfiguration("Lobby #1", 11, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby1", true, "none"),
            3, new ItemServerConfiguration("Lobby #2", 12, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby2", true, "none"),
            4, new ItemServerConfiguration("Lobby #3", 13, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby3", true, "none"),
            5, new ItemServerConfiguration("Lobby #4", 14, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby4", true, "none"),
            6, new ItemServerConfiguration("Lobby #5", 15, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby5", true, "none"),
            7, new ItemServerConfiguration("Lobby #6", 16, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby6", true, "none"));


        public static class FillSettings extends OkaeriConfig {
            @Comment({
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
