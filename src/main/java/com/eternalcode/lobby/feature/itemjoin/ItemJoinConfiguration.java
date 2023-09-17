package com.eternalcode.lobby.feature.itemjoin;

import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import com.eternalcode.lobby.config.ReloadableConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ItemJoinConfiguration implements ReloadableConfig {

    @Description({
        " ",
        "# Item join configuration",
        "# ItemJoin Action: OPEN_LOBBY_SWITCHER, OPEN_SERVER_SELECTOR, COMMAND, NONE",
        "# Texture: {PLAYER_HEAD} - player head, none - no texture, just set material",
        "# command only working when ItemJoin Action is COMMAND",
        " "
    })

    public Settings settings = new Settings();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "item-join.yml");
    }

    @Contextual
    public static class Settings {
        public Map<Integer, ItemJoin> slots = ImmutableMap.of(
            1, new ItemJoin("<gray>Wybór trybu <dark_gray>(Kliknij prawym)", 0, Material.COMPASS, new ArrayList<>(), "", ItemAction.OPEN_SERVER_SELECTOR, "none"),
            2, new ItemJoin("<gray>Wybór Lobby <dark_gray>(Kliknij prawym)", 8, Material.NETHER_STAR, new ArrayList<>(), "", ItemAction.OPEN_LOBBY_SWITCHER, "none"));
    }
}
