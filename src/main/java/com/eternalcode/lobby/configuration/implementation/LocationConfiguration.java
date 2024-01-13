package com.eternalcode.lobby.configuration.implementation;

import com.eternalcode.lobby.position.Position;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Names(modifier = NameModifier.TO_LOWER_CASE, strategy = NameStrategy.HYPHEN_CASE)
public class LocationConfiguration extends OkaeriConfig {

    public Position lobby = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

}
