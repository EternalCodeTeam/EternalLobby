package com.eternalcode.lobby.configuration.implementation;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import com.eternalcode.lobby.configuration.ReloadableConfig;
import com.eternalcode.lobby.position.Position;

import java.io.File;

public class LocationConfiguration implements ReloadableConfig {

    public Position lobby = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    public Position personalCharacter = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "locations.yml");
    }
}
