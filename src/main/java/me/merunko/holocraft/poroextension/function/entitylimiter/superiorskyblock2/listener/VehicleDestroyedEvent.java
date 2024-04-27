package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener;

import com.bgsoftware.superiorskyblock.api.island.Island;

import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInIsland;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2.SuperiorSkyBlock2Hook;

import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class VehicleDestroyedEvent implements Listener {

    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final Core core;

    public VehicleDestroyedEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent event) {

        EntityType entityType = event.getVehicle().getType();
        Chunk vehicleDestroyedOnChunk = event.getVehicle().getChunk();
        Island island = ssbHook.getIslandByChunk(vehicleDestroyedOnChunk);

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
