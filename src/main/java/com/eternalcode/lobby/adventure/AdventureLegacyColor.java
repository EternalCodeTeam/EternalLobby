package com.eternalcode.lobby.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import panda.std.stream.PandaStream;

import java.util.List;

public final class AdventureLegacyColor {

    public static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();

    public static final TextComponent RESET_ITEM = Component.text().decoration(TextDecoration.ITALIC, false).build();

    public static final LegacyComponentSerializer SECTION_SERIALIZER = LegacyComponentSerializer.builder()
        .character('§')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();

    private static final LegacyComponentSerializer AMPERSAND_SERIALIZER = LegacyComponentSerializer.builder()
        .character('&')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();

    private AdventureLegacyColor() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Component component(String text) {
        return AMPERSAND_SERIALIZER.deserialize(text);
    }

    public static List<Component> component(Iterable<String> texts) {
        return PandaStream.of(texts).map(AdventureLegacyColor::component).toList();
    }
}
