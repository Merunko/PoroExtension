package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener;

import com.bgsoftware.superiorskyblock.api.island.Island;

import dev.rosewood.rosestacker.stack.StackedEntity;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInIsland;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.configuration.EntityLimiterConfiguration;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.rosestacker.RoseStackerHook;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2.SuperiorSkyBlock2Hook;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class CreatureDieEvent implements Listener {

    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final RoseStackerHook rsHook = new RoseStackerHook();
    private final EntityLimiterConfiguration entityLimiter;
    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final Core core;

    public CreatureDieEvent(EntityLimiterConfiguration entityLimiter, Core core) {
        this.entityLimiter = entityLimiter;
        this.core = core;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        EntityType entityType = event.getEntityType();
        Chunk entityDiedOnChunk = event.getEntity().getChunk();
        Island island = ssbHook.getIslandByChunk(entityDiedOnChunk);

        if (island != null) {

            if (entityLimiter.getEntityStackingPluginProvider().equalsIgnoreCase("NONE")) {
                pc.sendMessage(console, "none");

                IslandLoad islandLoad = core.getIslandData();
                EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(island);

                int currentCount = entitiesInIsland.getCurrentEntityTotal(entityType);

                if (currentCount != 0) {
                    entitiesInIsland.updateEntityTotal(entityType, currentCount - 1);
                    islandLoad.updateIslandData(island, entitiesInIsland);
                }

            } else if (entityLimiter.getEntityStackingPluginProvider().equalsIgnoreCase("RoseStacker")) {

                StackedEntity stackedEntity = rsHook.getAPI().getStackedEntity(event.getEntity());

                if (stackedEntity != null && stackedEntity.getStackSize() > 1) {
                    return;
                }

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
