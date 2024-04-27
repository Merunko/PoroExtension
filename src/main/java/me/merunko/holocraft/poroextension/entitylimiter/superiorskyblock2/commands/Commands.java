package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.commands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInIsland;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Commands {

    private final PoroColor pc = new PoroColor();
    private final Core core;

    public Commands(Core core) {
        this.core = core;
    }

    public void getEntityCount(CommandSender sender, String[] args) {

        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(args[2]);
        EntityType entityType = EntityType.valueOf(args[3].toUpperCase());

        IslandLoad islandLoad = core.getIslandData();
        EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(superiorPlayer.getIsland());

        pc.sendMessage(sender, "{65DB44}" + entityType + ": &6" + entitiesInIsland.getCurrentEntityTotal(entityType));

    }

    public void changeEntityCount(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            pc.sendMessage(sender, "{E03C3C}This command can only be executed by a player.");
            return;
        }

        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(args[2]);
        EntityType entityType = EntityType.valueOf(args[3].toUpperCase());
        int count = Integer.parseInt(args[4]);

        IslandLoad islandLoad = core.getIslandData();
        EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(superiorPlayer.getIsland());

        entitiesInIsland.updateEntityTotal(entityType, count);
        islandLoad.updateIslandData(superiorPlayer.getIsland(), entitiesInIsland);
        pc.sendMessage(sender, "{65DB44}Changed current entity for &6" + entityType + " {65DB44}to &6" + count + " {65DB44}for &6" + superiorPlayer.getName() + " {65DB44}island.");

    }

}
