package com.eternalcode.lobby.util;

import com.eternalcode.lobby.util.legacy.Legacy;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import panda.utilities.StringUtils;

public final class ChatUtil {

    private final static int COUNT_LINE_TO_CLEAR = 255;

    private final static MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void clearChat(Player player) {
        for (int lineIndex = 0; lineIndex < COUNT_LINE_TO_CLEAR; lineIndex++) {
            player.sendMessage(StringUtils.EMPTY);
        }
    }

    public static String fixColor(String plainContent) {
        return plainContent.replace('&', '§');
    }

    public static String colorAndApplyPlaceholders(Player player, String text) {
        return Legacy.SECTION_SERIALIZER.serialize(miniMessage.deserialize(PlaceholderAPI.setPlaceholders(player, text)));
    }
}
