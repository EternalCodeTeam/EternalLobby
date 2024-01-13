package com.eternalcode.lobby.feature.menu.serverselector;

import com.eternalcode.lobby.feature.menu.ItemServerConfiguration;
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
public class ServerSelectorConfiguration extends OkaeriConfig {

    @Comment({
        "# ",
        "# Server selector configuration",
        "# "
    })
    public Settings settings = new Settings();

    public static class Settings extends OkaeriConfig {
        public FillSettings fill = new FillSettings();

        @Comment({
            "# Title of the lobby server selector"
        })
        public String guiTitle = "Wybór trybu:";

        @Comment({
            " ",
            "# Rows of the lobby server selector"
        })
        public int guiRows = 3;

        @Comment({
            " ",
            "# Items of the lobby server selector"
        })
        public Map<String, ItemServerConfiguration> items = Map.of(
            "survival", new ItemServerConfiguration("Survival", 11, Material.GRASS_BLOCK, new ArrayList<>(), "survival", true, "none"),
            "creative", new ItemServerConfiguration("Creative", 13, Material.BRICK, new ArrayList<>(), "creative", true, "none"),
            "spectator", new ItemServerConfiguration("Spectator", 15, Material.DRAGON_EGG, new ArrayList<>(), "budowlany", true, "none"));

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
