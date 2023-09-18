package com.eternalcode.lobby.feature.visibility;

import com.eternalcode.lobby.configuration.ReloadableConfig;
import com.eternalcode.lobby.delay.DelaySettings;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class VisibilityConfiguration implements ReloadableConfig {

    public VisibilitySection visibility = new VisibilitySection();

    @Contextual
    public static class VisibilitySection implements DelaySettings {

        public String hidePlayers = "<red>You have successfully hidden players!";
        public String showPlayers = "<red>You have successfully shown players!";
        public Duration delay = Duration.ofSeconds(3);

        @Description({ " ", "# Visibility item slot" })
        public int slot = 6;

        @Description({ " ", "# Items to hide/show players" })
        public VisibilityItem showItem = new VisibilityItem("<green>Show players",
            List.of("<gray>Click to show players"),
            List.of(ItemFlag.HIDE_ATTRIBUTES),
            Material.LIME_DYE
        );

        public VisibilityItem hideItem = new VisibilityItem("<red>Hide players",
            List.of("<gray>Click to hide players"),
            List.of(ItemFlag.HIDE_ATTRIBUTES),
            Material.GRAY_DYE
        );

        @Override
        public Duration delay() {
            return delay;
        }
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "visibility.yml");
    }
}
