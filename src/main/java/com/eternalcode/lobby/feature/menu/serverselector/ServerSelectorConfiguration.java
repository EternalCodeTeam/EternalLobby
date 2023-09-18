package com.eternalcode.lobby.feature.menu.serverselector;

import com.eternalcode.lobby.configuration.ReloadableConfig;
import com.eternalcode.lobby.feature.menu.ItemServerConfig;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerSelectorConfiguration implements ReloadableConfig {

    @Description({
        "# ",
        "# Server selector configuration",
        "# "
    })
    public Settings settings = new Settings();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "server-selector.yml");
    }

    @Contextual
    public static class Settings {
        public FillSettings fill = new FillSettings();

        @Description({
            "# Title of the lobby server selector"
        })
        public String guiTitle = "Wybór trybu:";

        @Description({
            " ",
            "# Rows of the lobby server selector"
        })
        public int guiRows = 3;

        @Description({
            " ",
            "# Items of the lobby server selector"
        })
        public Map<String, ItemServerConfig> items = Map.of(
            "survival", new ItemServerConfig("Survival", 11, Material.GRASS_BLOCK, new ArrayList<>(), "survival", true, "none"),
            "creative", new ItemServerConfig("Creative", 13, Material.BRICK, new ArrayList<>(), "creative", true, "none"),
            "spectator", new ItemServerConfig("Spectator", 15, Material.DRAGON_EGG, new ArrayList<>(), "budowlany", true, "none"));

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
