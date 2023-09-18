package com.eternalcode.lobby.configuration.implementation;

import com.eternalcode.lobby.configuration.ReloadableConfig;
import com.eternalcode.lobby.delay.DelaySettings;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Sound;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class PluginConfiguration implements ReloadableConfig {

    public DoubleJumpSettings doubleJump = new DoubleJumpSettings();

    @Contextual
    public static class DoubleJumpSettings implements DelaySettings {
        @Description({
            " ",
            "# Double jump settings",
        })
        public boolean doubleJumpEnabled = true;
        public Sound doubleJumpSound = Sound.ENTITY_BAT_TAKEOFF;
        public Float doubleJumpPower = 1.3F;
        public Float doubleJumpPowerY = 2.3F;
        public Float doubleJumpVolume = 1.8F;
        public Float doubleJumpPitch = 1.0F;

        @Description({ " ", "# Additional double jump settings", })
        public Duration doubleJumpDelay = Duration.ofSeconds(1);

        @Override
        public Duration delay() {
            return this.doubleJumpDelay;
        }
    }

    @Description({
        " ",
        "# URL To the api returning the player's head, in case of failure you can change",
        "# Variables: {PLAYER}, {UUID}",
    })
    public String apiUrl = "https://minepic.org/avatar/8/{UUID}";
    @Description({
        " ",
        "# The height at which a player is teleported to spawn",
    })
    public int voidTeleportHeight = -5;

    public String voidTeleportMessage = "<red>Wyszedłeś poza granicę mapy!";
    @Description({
        " ",
        "# Sends the audio below to everyone on the server when someone sends something in chat",
    })
    public boolean playerChatSoundEnabled = true;
    public Float playerChatVolume = 1.8F;
    public Float playerChatPitch = 1.0F;

    public Sound playerChatSound = Sound.ENTITY_ITEM_PICKUP;
    @Description({
        " ",
        "# Sends a sound when the player changes the slot on the taskbar",
    })
    public boolean playerHeldItemEnabled = true;
    public Float playerHeldItemVolume = 1.8F;
    public Float playerHeldItemPitch = 1.0F;

    public Sound playerHeldItemSound = Sound.BLOCK_NOTE_BLOCK_HAT;

    @Description({
        " ",
        "# Options for the message displayed when entering the server",
    })
    public boolean enableWelcomeMessage = true;
    public boolean welcomeMessageHeadDisplay = true;
    public boolean cleanAtJoin = true;

    public List<String> messageAfterJoin = List.of(
        "Exampelo Configu messagu",
        "Exampela masaga from configa message 2"
    );

    @Description({
        " ",
        "# Join message settings",
    })

    public List<String> joinMessage = Arrays.asList(
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>wpadł do nas!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>przypłynął do na nas!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>przywlekł się do nas!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>przyczołgał się do nas!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>wskoczył do nas!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>wspiął się do nas na serwer!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>doskoczył do nas na serwer!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>zameldował się!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>wkradł się na serwer!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>przeteleportował się do nas!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>przyleciał!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>przyjechał do nas na serwer!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>zawitał!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>przyfrunął!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>wylądował u nas na serwerze!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>zabalował i wyładował na <gradient:#00fffb:white>eternalmc!</gradient></gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>zabalował i wyładował w naszych skromnych progach</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>zawitał w naszych skromnych progach!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>odwiedził nas ponownie!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>dołączył na serwer!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>wcisnął się do nas!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>elooo!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>pojawił się, przywitajmy go wszyscy!</gray>",
        "<dark_gray>[<b><color:#0dff00>+</color></b>]</dark_gray> <color:#0dff00>{PLAYER}</color> <gray>zajrzał do nas na serwer!</gray>"
    );

    @Description({
        " ",
        "# Leave message settings",
    })

    public List<String> leaveMessage = Arrays.asList(
        "Goodbye {PLAYER}",
        "Bye {PLAYER}"
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "configuration.yml");
    }
}
