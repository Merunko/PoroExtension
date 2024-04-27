package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener;

import com.bgsoftware.superiorskyblock.api.island.Island;

import dev.rosewood.rosestacker.event.EntityUnstackEvent;

import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInIsland;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2.SuperiorSkyBlock2Hook;

import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RoseStackerEntityUnstackEvent implements Listener {

    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final Core core;

    public RoseStackerEntityUnstackEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onEntityUnstackEvent(EntityUnstackEvent event) {
        EntityType entityType = event.getResult().getEntity().getType();
        Chunk entityUnstackOnChunk = event.getResult().getEntity().getChunk();
        Island island = ssbHook.getIslandByChunk(entityUnstackOnChunk);

        if (island != null) {

            IslandLoad islandLoad = core.getIslandData();
            EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(island);

            int limit = ssbHook.checkLimit(island, entityType);
            int currentCount = entitiesInIsland.getCurrentEntityTotal(entityType);

            if (currentCount >= limit && limit != -1) {
                event.setCancelled(true);

            } else {
                entitiesInIsland.updateEntityTotal(entityType, currentCount + 1);
                islandLoad.updateIslandData(island, entitiesInIsland);
            }

        }
    }
}