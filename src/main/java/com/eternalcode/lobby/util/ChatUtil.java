package com.eternalcode.lobby.util;

import com.eternalcode.lobby.adventure.AdventureLegacyColor;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public final class ChatUtil {

    private final static MiniMessage miniMessage = MiniMessage.miniMessage();

    public static String colorAndApplyPlaceholders(Player player, String text) {
        return AdventureLegacyColor.SECTION_SERIALIZER.serialize(miniMessage.deserialize(PlaceholderAPI.setPlaceholders(player, text)));
    }
}
