package com.eternalcode.lobby.commands;

import com.eternalcode.lobby.feature.menu.skinchange.SkinChangeInventory;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "darmowyskin")
public class SkinCommand {

    private final SkinChangeInventory skinChangeInventory;

    public SkinCommand(SkinChangeInventory skinChangeInventory) {
        this.skinChangeInventory = skinChangeInventory;

    }

    @Execute
    void skinGui(Player player) {
        this.skinChangeInventory.openSkinChangeGui(player);
    }
}
