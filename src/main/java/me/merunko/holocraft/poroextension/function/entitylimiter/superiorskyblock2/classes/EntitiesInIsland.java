package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes;

import com.bgsoftware.superiorskyblock.api.island.Island;

import org.bukkit.entity.EntityType;

import java.util.concurrent.ConcurrentHashMap;

public class EntitiesInIsland {

    private Island island;
    private final ConcurrentHashMap<EntityType, Integer> entitiesInIsland;

    public EntitiesInIsland() {
        this.island = null;
        this.entitiesInIsland = new ConcurrentHashMap<>();
    }

    public EntitiesInIsland(Island island, ConcurrentHashMap<EntityType, Integer> entitiesInIsland) {
        this.island = island;
        this.entitiesInIsland = entitiesInIsland;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public ConcurrentHashMap<EntityType, Integer> getEntitiesInIsland(Island island) {
        if (island == this.island) {
            return entitiesInIsland;
        }
        return null;
    }

    public void updateEntityTotal(EntityType entityType, int total) {
        entitiesInIsland.put(entityType, total);
    }

    public int getCurrentEntityTotal(EntityType entityType) {
        return entitiesInIsland.getOrDefault(entityType, 0);
    }

}
