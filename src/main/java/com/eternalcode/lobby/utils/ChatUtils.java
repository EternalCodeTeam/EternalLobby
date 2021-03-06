package com.eternalcode.lobby.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatUtils {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    private ChatUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Component component(String text) {
        return SERIALIZER.deserialize(text);
    }
}
