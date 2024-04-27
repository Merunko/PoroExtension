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
import org.bukkit.event.vehicle.VehicleCreateEvent;

import java.util.List;

public class VehicleCreatingEvent implements Listener {

    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final PoroColor pc = new PoroColor();
    private final Core core;

    public VehicleCreatingEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onVehicleCreate(VehicleCreateEvent event) {

        EntityType entityType = event.getVehicle().getType();
        Chunk vehicleCreatedOnChunk = event.getVehicle().getChunk();
        Island island = ssbHook.getIslandByChunk(vehicleCreatedOnChunk);

        if (island != null) {

            IslandLoad islandLoad = core.getIslandData();
            EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(island);

            int limit = ssbHook.checkLimit(island, entityType);
            int currentCount = entitiesInIsland.getCurrentEntityTotal(entityType);

            if (currentCount >= limit && limit != -1) {

                CreatureSpawnEvent.SpawnReason spawnReason = event.getVehicle().getEntitySpawnReason();
                if (spawnReason == CreatureSpawnEvent.SpawnReason.DEFAULT) {

                    List<SuperiorPlayer> islandMembers = ssbHook.getAllMembers(island);

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
