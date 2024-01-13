package com.eternalcode.lobby;

import com.eternalcode.lobby.configuration.ConfigurationService;
import com.eternalcode.lobby.feature.itemjoin.ItemJoinConfiguration;
import com.eternalcode.lobby.configuration.implementation.PluginConfiguration;
import com.eternalcode.lobby.listener.*;
import com.eternalcode.lobby.feature.menu.serverselector.ServerSelectorConfiguration;
import com.eternalcode.lobby.feature.doublejump.DoubleJumpController;
import com.eternalcode.lobby.feature.itemjoin.ItemJoinController;
import com.eternalcode.lobby.notification.NotificationAnnouncer;
import com.eternalcode.lobby.voidteleport.VoidController;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.eternalcode.lobby.feature.menu.lobbyswitcher.LobbySwitcherConfiguration;
import com.eternalcode.lobby.configuration.implementation.LocationConfiguration;
import com.eternalcode.lobby.feature.image.ImageController;
import com.eternalcode.lobby.feature.menu.ConnectionService;
import com.eternalcode.lobby.feature.menu.lobbyswitcher.LobbySwitcherInventory;
import com.eternalcode.lobby.feature.menu.serverselector.ServerSelectorInventory;
import com.eternalcode.lobby.feature.sound.SoundController;
import com.eternalcode.lobby.adventure.AdventureLegacyColorProcessor;
import com.eternalcode.lobby.feature.visibility.VisibilityConfiguration;
import com.eternalcode.lobby.feature.visibility.VisibilityController;
import com.eternalcode.lobby.feature.visibility.VisibilityService;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class LobbyPlugin extends JavaPlugin {

    private LiteCommands<CommandSender> liteCommands;
    private AudienceProvider audienceProvider;
    private SkullAPI skullAPI;

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();
        Server server = this.getServer();

        ConfigurationService configurationService = new ConfigurationService();
        File dataFolder = this.getDataFolder();
        PluginConfiguration pluginConfig = configurationService.create(PluginConfiguration.class, new File(dataFolder, "config.yml"));
        LocationConfiguration locationConfig = configurationService.create(LocationConfiguration.class, new File(dataFolder, "locations.yml"));
        LobbySwitcherConfiguration lobbySwitcherConfig = configurationService.create(LobbySwitcherConfiguration.class, new File(dataFolder, "lobby-switcher.yml"));
        ItemJoinConfiguration itemJoinConfig = configurationService.create(ItemJoinConfiguration.class, new File(dataFolder, "item-join.yml"));
        ServerSelectorConfiguration serverSelectorConfig = configurationService.create(ServerSelectorConfiguration.class, new File(dataFolder, "server-selector.yml"));
        VisibilityConfiguration visibilityConfig = configurationService.create(VisibilityConfiguration.class, new File(dataFolder, "visibility.yml"));

        // Setup managers and other stuff
        this.audienceProvider = BukkitAudiences.create(this);
        MiniMessage miniMessage = MiniMessage.builder().postProcessor(new AdventureLegacyColorProcessor()).build();
        NotificationAnnouncer notificationAnnouncer = new NotificationAnnouncer(this.audienceProvider, miniMessage);

        this.skullAPI = LiteSkullFactory.builder()
            .cacheExpireAfterWrite(Duration.ofMinutes(15L))
            .bukkitScheduler(this)
            .build();

        ConnectionService connectionService = new ConnectionService(this);
        ServerSelectorInventory serverSelectorInventory = new ServerSelectorInventory(serverSelectorConfig, connectionService, miniMessage, this, skullAPI);
        LobbySwitcherInventory lobbySwitcherInventory = new LobbySwitcherInventory(lobbySwitcherConfig, connectionService, this, miniMessage, skullAPI);
        VisibilityService visibilityService = new VisibilityService(server, this, notificationAnnouncer, visibilityConfig);

        // Register bungee channel
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Setup LiteCommands
        this.liteCommands = LiteBukkitFactory.builder(server, "lobby-system")
            .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("This command is only available for players!"))

            .commandInstance(
                new LobbyCommand(configurationService, locationConfig, notificationAnnouncer)
            )
            .register();

        // Setup listeners
        Stream.of(
            // Listeners
            new PlayerBlockBreakListener(),
            new PlayerBlockPlaceListener(),
            new PlayerDamageListener(),
            new PlayerDeathListener(),
            new PlayerFoodListener(),
            new PlayerJoinListener(pluginConfig, this.audienceProvider, miniMessage),
            new PlayerQuitListener(pluginConfig, miniMessage, this.audienceProvider),
            new ArmorStandListener(),
            new PlayerPortalListener(serverSelectorInventory),

            // Image (Join head display controller)
            new ImageController(pluginConfig, this),

            // DoubleJump
            new DoubleJumpController(pluginConfig),

            // Awesome sound additions
            new SoundController(pluginConfig, server),

            // Anti Void Controller
            new VoidController(locationConfig, pluginConfig, notificationAnnouncer),

            // ItemJoin
            new ItemJoinController(itemJoinConfig, miniMessage, serverSelectorInventory, lobbySwitcherInventory, skullAPI),

            // visibility
            new VisibilityController(visibilityService, visibilityConfig, this)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));


        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("EternalLobby enabled in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.getPlatform().unregisterAll();
        }

        if (this.audienceProvider != null) {
            this.audienceProvider.close();
        }

        if (this.skullAPI != null) {
            this.skullAPI.shutdown();
        }

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}