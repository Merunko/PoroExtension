package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener;

import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInIsland;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2.SuperiorSkyBlock2Hook;

import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

public class CreatureSpawningEvent implements Listener {

    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final PoroColor pc = new PoroColor();
    private final Core core;

    public CreatureSpawningEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {

        EntityType entityType = event.getEntityType();
        Chunk entitySpawnedOnChunk = event.getEntity().getChunk();
        Island island = ssbHook.getIslandByChunk(entitySpawnedOnChunk);

        if (island != null) {

            IslandLoad islandLoad = core.getIslandData();
            EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(island);

            int limit = ssbHook.checkLimit(island, entityType);
            int currentCount = entitiesInIsland.getCurrentEntityTotal(entityType);

            if (currentCount >= limit && limit != -1) {

                List<SuperiorPlayer> islandMembers = ssbHook.getAllMembers(island);
                CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
                if (spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {

                    String[] words = entityType.toString().split("_");
                    StringBuilder entityTypeTitleCaseBuilder = new StringBuilder();
                    for (String word : words) {
                        entityTypeTitleCaseBuilder.append(Character.toUpperCase(word.charAt(0)))
                                .append(word.substring(1).toLowerCase())
                                .append(" ");
                    }
                    String entityTypeTitleCase = entityTypeTitleCaseBuilder.toString().trim();
                    for (SuperiorPlayer superiorPlayer : islandMembers) {
                        if (superiorPlayer.asPlayer().isOnline()) {
                            pc.sendMessageNoPluginName(superiorPlayer.asPlayer(), "{E03C3C}Island reached max entity limit for &6" + entityTypeTitleCase + "{E03C3C}.");
                        }
                    }
                }
                event.setCancelled(true);

            } else {
                entitiesInIsland.updateEntityTotal(entityType, currentCount + 1);
                islandLoad.updateIslandData(island, entitiesInIsland);
            }

        }
    }
}