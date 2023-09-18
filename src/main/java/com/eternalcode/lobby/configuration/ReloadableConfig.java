package com.eternalcode.lobby.configuration;

import net.dzikoysk.cdn.source.Resource;

import java.io.File;

public interface ReloadableConfig {

    Resource resource(File folder);

}
