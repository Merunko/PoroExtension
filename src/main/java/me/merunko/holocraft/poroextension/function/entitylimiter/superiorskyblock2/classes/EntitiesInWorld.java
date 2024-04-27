package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes;

import com.bgsoftware.superiorskyblock.api.island.Island;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.core.nbtreader.mca.Chunk;
import me.merunko.holocraft.poroextension.core.nbtreader.mca.MCAFile;
import me.merunko.holocraft.poroextension.core.nbtreader.mca.MCAUtil;
import me.merunko.holocraft.poroextension.core.nbtreader.nbt.tag.CompoundTag;
import me.merunko.holocraft.poroextension.core.nbtreader.nbt.tag.ListTag;
import me.merunko.holocraft.poroextension.core.nbtreader.nbt.tag.Tag;
import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.hook.superiorskyblock2.SuperiorSkyBlock2Hook;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class EntitiesInWorld {

    private final SuperiorSkyBlock2Hook ssbHook = new SuperiorSkyBlock2Hook();
    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public void readMCA(File[] mcaFiles, String worldName, IslandLoad islandLoad) {

        if (mcaFiles != null) {
            for (File file : mcaFiles) {
                try {
                    MCAFile mca = MCAUtil.read(file);

                    for (int chunkX = 0; chunkX < 32; chunkX++) {
                        for (int chunkZ = 0; chunkZ < 32; chunkZ++) {
                            Chunk chunk = mca.getChunk(chunkX, chunkZ);
                            if (chunk != null) {
                                ListTag<CompoundTag> entitiesListTag = chunk.getEntities();
                                if (entitiesListTag != null) {
                                    for (CompoundTag entity : entitiesListTag) {
                                        if (entity.containsKey("id") && entity.containsKey("Pos")) {
                                            String entityId = entity.getString("id");
                                            ListTag<? extends Tag<?>> posList = entity.getListTag("Paper.Origin");
                                            if (posList != null && posList.size() == 3) {
                                                double x = (double) posList.get(0).getValue();
                                                double y = (double) posList.get(1).getValue();
                                                double z = (double) posList.get(2).getValue();

                                                if (!entityId.equalsIgnoreCase("minecraft:item")) {
                                                    EntityType entityType = EntityType.valueOf(idEntityTypeFormat(fixEntityType(entityId)));
                                                    Location entityLocation = new Location(Bukkit.getWorld(worldName), x, y, z);

                                                    Island island = ssbHook.getIslandByLocation(entityLocation);

                                                    if (island != null) {

                                                        EntitiesInIsland entitiesInIsland = islandLoad.getIslandData(island);
                                                        int count = entitiesInIsland.getCurrentEntityTotal(entityType);
                                                        entitiesInIsland.updateEntityTotal(entityType, count + 1);
                                                        islandLoad.updateIslandData(island, entitiesInIsland);

                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    pc.sendMessage(console, e.getMessage());
                }
            }

        }

    }

    private String idEntityTypeFormat(String entityId) {
        String[] parts = entityId.split(":");
        return parts[1].toUpperCase();
    }

    private String fixEntityType(String entityType) {
        if (entityType.equalsIgnoreCase("minecraft:hopper_minecart")) {
            return "minecraft:minecart_hopper";
        } else {
            return entityType;
        }
    }

}
