package com.eternalcode.lobby.feature.visibility;

import com.eternalcode.lobby.delay.DelaySettings;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.time.Duration;
import java.util.List;

@Names(modifier = NameModifier.TO_LOWER_CASE, strategy = NameStrategy.HYPHEN_CASE)
public class VisibilityConfiguration extends OkaeriConfig {

    public VisibilitySection visibility = new VisibilitySection();

    public static class VisibilitySection extends OkaeriConfig implements DelaySettings {

        public String hidePlayers = "<gradient:#FF0000:#FF6600>You have successfully hidden players!";
        public String showPlayers = "<gradient:#92ff33:#00FF00>You have successfully shown players!</gradient>";
        public Duration delay = Duration.ofSeconds(3);

        @Comment({ " ", "# Visibility item slot" })
        public int slot = 6;

        @Comment({ " ", "# Items to hide/show players" })
        public VisibilityItem showItem = new VisibilityItem("<gradient:#92ff33:#00FF00>Show players</gradient>",
            List.of("<gray>Click to show players"),
            List.of(ItemFlag.HIDE_ATTRIBUTES),
            Material.LIME_DYE
        );

        public VisibilityItem hideItem = new VisibilityItem("<gradient:#FF0000:#FF6600>Hide players",
            List.of("<gray>Click to hide players"),
            List.of(ItemFlag.HIDE_ATTRIBUTES),
            Material.GRAY_DYE
        );

        @Override
        public Duration delay() {
            return delay;
        }
    }
}