package com.eternalcode.lobby.feature.itemjoin;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;

@Names(modifier = NameModifier.TO_LOWER_CASE, strategy = NameStrategy.HYPHEN_CASE)
public class ItemJoinConfiguration extends OkaeriConfig {

    @Comment({
        " ",
        "# Item join configuration",
        "# ItemJoin Action: OPEN_LOBBY_SWITCHER, OPEN_SERVER_SELECTOR, COMMAND, NONE",
        "# Texture: {PLAYER_HEAD} - player head, none - no texture, just set material",
        "# command only working when ItemJoin Action is COMMAND",
        " "
    })

    public Settings settings = new Settings();

    public static class Settings extends OkaeriConfig {
        public Map<Integer, ItemJoin> slots = Map.of(
            1, new ItemJoin("<gray>Wybór trybu <dark_gray>(Kliknij prawym)", 0, Material.COMPASS, new ArrayList<>(), "", ItemAction.OPEN_SERVER_SELECTOR, "none"),
            2, new ItemJoin("<gray>Wybór Lobby <dark_gray>(Kliknij prawym)", 8, Material.NETHER_STAR, new ArrayList<>(), "", ItemAction.OPEN_LOBBY_SWITCHER, "none"));
    }
}
