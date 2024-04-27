package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener;

import com.bgsoftware.superiorskyblock.api.island.Island;

import dev.rosewood.rosestacker.event.EntityStackClearEvent;
import dev.rosewood.rosestacker.stack.StackedEntity;

import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInIsland;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2.SuperiorSkyBlock2Hook;

import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class RoseStackerEntityStackClearEvent implements Listener {

    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final Core core;

    public RoseStackerEntityStackClearEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onEntityStackClearEvent(EntityStackClearEvent event) {
        List<StackedEntity> stackedEntities = event.getStacks();

        for (StackedEntity stackedEntity : stackedEntities) {

            EntityType entityType = stackedEntity.getEntity().getType();
            Chunk entityStackClearedOnChunk = stackedEntity.getEntity().getChunk();
            Island island = ssbHook.getIslandByChunk(entityStackClearedOnChunk);

            if (island != null) {

                IslandLoad islandLoad = core.getIslandData();
                EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(island);

                int currentCount = entitiesInIsland.getCurrentEntityTotal(entityType);

                if (currentCount != 0) {
                    entitiesInIsland.updateEntityTotal(entityType, currentCount - 1);
                    islandLoad.updateIslandData(island, entitiesInIsland);
                }

            }
        }
    }
}
