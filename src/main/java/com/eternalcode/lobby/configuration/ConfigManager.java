package com.eternalcode.lobby.configuration;

import com.eternalcode.lobby.configuration.composer.DurationComposer;
import com.eternalcode.lobby.configuration.composer.PositionComposer;
import com.eternalcode.lobby.position.Position;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Position.class, new PositionComposer())
        .withComposer(Duration.class, new DurationComposer())
        .build();

    private final Set<ReloadableConfig> configs = new HashSet<>();
    private final File dataFolder;

    public ConfigManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T extends ReloadableConfig> void load(T config) {
        this.cdn.load(config.resource(this.dataFolder), config)
            .orThrow(RuntimeException::new);

        this.cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);

        this.configs.add(config);
    }

    public <T extends ReloadableConfig> void save(T config) {
        this.cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);
    }

    public void reload() {
        for (ReloadableConfig config : this.configs) {
            load(config);
        }
    }

}
