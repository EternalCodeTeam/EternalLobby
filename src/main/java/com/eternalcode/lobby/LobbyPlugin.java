package com.eternalcode.lobby;

import com.eternalcode.lobby.commands.SkinCommand;
import com.eternalcode.lobby.feature.itemjoin.ItemJoinConfiguration;
import com.eternalcode.lobby.configuration.implementation.PluginConfiguration;
import com.eternalcode.lobby.feature.menu.skinchange.SkinChangeConfiguration;
import com.eternalcode.lobby.feature.menu.skinchange.SkinChangeInventory;
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
import com.eternalcode.lobby.configuration.ConfigManager;
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

        // Setup configs
        ConfigManager configManager = new ConfigManager(this.getDataFolder());

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        LocationConfiguration locationConfiguration = new LocationConfiguration();
        LobbySwitcherConfiguration lobbySwitcherConfig = new LobbySwitcherConfiguration();
        SkinChangeConfiguration skinChangeConfiguration = new SkinChangeConfiguration();
        ItemJoinConfiguration itemJoinConfiguration = new ItemJoinConfiguration();
        ServerSelectorConfiguration serverSelectorConfiguration = new ServerSelectorConfiguration();
        VisibilityConfiguration visibilityConfiguration = new VisibilityConfiguration();

        configManager.load(pluginConfiguration);
        configManager.load(locationConfiguration);
        configManager.load(lobbySwitcherConfig);
        configManager.load(itemJoinConfiguration);
        configManager.load(serverSelectorConfiguration);
        configManager.load(skinChangeConfiguration);
        configManager.load(visibilityConfiguration);

        // Setup managers and other stuff
        this.audienceProvider = BukkitAudiences.create(this);
        MiniMessage miniMessage = MiniMessage.builder().postProcessor(new AdventureLegacyColorProcessor()).build();
        NotificationAnnouncer notificationAnnouncer = new NotificationAnnouncer(this.audienceProvider, miniMessage);

        this.skullAPI = LiteSkullFactory.builder()
            .cacheExpireAfterWrite(Duration.ofMinutes(15L))
            .bukkitScheduler(this)
            .build();

        ConnectionService connectionService = new ConnectionService(this);
        SkinChangeInventory skinChangeInventory = new SkinChangeInventory(skinChangeConfiguration, connectionService, this, server.getScheduler(), miniMessage, skullAPI);
        ServerSelectorInventory serverSelectorInventory = new ServerSelectorInventory(serverSelectorConfiguration, connectionService, miniMessage, this, skullAPI);
        LobbySwitcherInventory lobbySwitcherInventory = new LobbySwitcherInventory(lobbySwitcherConfig, connectionService, this, miniMessage, skullAPI);
        VisibilityService visibilityService = new VisibilityService(server, this, notificationAnnouncer, visibilityConfiguration);

        // Register bungee channel
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Setup LiteCommands
        this.liteCommands = LiteBukkitFactory.builder(server, "lobby-system")
            .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("This command is only available for players!"))

            .commandInstance(
                new LobbyCommand(configManager, locationConfiguration, notificationAnnouncer),
                new SkinCommand(skinChangeInventory)
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
            new PlayerJoinListener(pluginConfiguration, locationConfiguration, this.audienceProvider, miniMessage),
            new PlayerQuitListener(pluginConfiguration, miniMessage, this.audienceProvider),
            new ArmorStandListener(),
            new PlayerInventoryClickListener(),
            new PlayerPortalListener(serverSelectorInventory),

            // Image (Join head display controller)
            new ImageController(pluginConfiguration, this),

            // DoubleJump
            new DoubleJumpController(pluginConfiguration),

            // Awesome sound additions
            new SoundController(pluginConfiguration, server),

            // Anti Void Controller
            new VoidController(locationConfiguration, pluginConfiguration, notificationAnnouncer),

            // ItemJoin
            new ItemJoinController(itemJoinConfiguration, miniMessage, serverSelectorInventory, lobbySwitcherInventory, skullAPI),

            // visibility
            new VisibilityController(visibilityService, visibilityConfiguration, this)
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
