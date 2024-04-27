package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class SuperiorSkyBlock2Hook {

    public List<Island> getAllIsland() {
        return SuperiorSkyblockAPI.getGrid().getIslands();
    }

    public Island getIslandByChunk(Chunk chunk) {
        return SuperiorSkyblockAPI.getGrid().getIslandAt(chunk);
    }

    public Island getIslandByLocation(Location location) {
        return SuperiorSkyblockAPI.getIslandAt(location);
    }

    public int checkLimit(Island island, EntityType entityType) {
        return island.getEntityLimit(entityType);
    }

    public List<SuperiorPlayer> getAllMembers(Island island) {
        return island.getIslandMembers(true);
    }

}