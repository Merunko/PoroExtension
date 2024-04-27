package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener;

import com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;

import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandCreationEvent implements Listener {

    private final Core core;

    public IslandCreationEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onIslandCreate(IslandCreateEvent event) {

        Island island = event.getIsland();
        IslandLoad islandLoad = core.getIslandData();
        islandLoad.addNewIslandData(island);

    }
}
