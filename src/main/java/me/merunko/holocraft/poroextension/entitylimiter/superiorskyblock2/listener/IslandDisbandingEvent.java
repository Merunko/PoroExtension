package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener;

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;

import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandDisbandingEvent implements Listener {

    private final Core core;

    public IslandDisbandingEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onIslandDisband(IslandDisbandEvent event) {

        Island disbandingIsland = event.getIsland();
        IslandLoad islandLoad = core.getIslandData();
        islandLoad.removeIslandData(disbandingIsland);

    }
}
