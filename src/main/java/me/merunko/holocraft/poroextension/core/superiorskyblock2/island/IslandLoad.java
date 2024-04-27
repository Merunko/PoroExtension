package me.merunko.holocraft.poroextension.core.superiorskyblock2.island;

import com.bgsoftware.superiorskyblock.api.island.Island;

import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInIsland;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2.SuperiorSkyBlock2Hook;

import java.util.concurrent.ConcurrentHashMap;

public class IslandLoad {

    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final ConcurrentHashMap<Island, EntitiesInIsland> islandsEntitiesData;

    public IslandLoad() {
        this.islandsEntitiesData = new ConcurrentHashMap<>();
    }

    public void collectAllIslandsData() {
        for (Island island : ssbHook.getAllIsland()) {
            this.islandsEntitiesData.put(island, new EntitiesInIsland(island, new ConcurrentHashMap<>()));
        }
    }

    public void addNewIslandData(Island island) {
        this.islandsEntitiesData.put(island, new EntitiesInIsland(island, new ConcurrentHashMap<>()));
    }

    public void updateIslandData(Island island, EntitiesInIsland entitiesInIsland) {
        this.islandsEntitiesData.put(island, entitiesInIsland);
    }

    public void removeIslandData(Island island) {
        this.islandsEntitiesData.remove(island);
    }

    public EntitiesInIsland getIslandData(Island island) {
        return this.islandsEntitiesData.get(island);
    }

}
