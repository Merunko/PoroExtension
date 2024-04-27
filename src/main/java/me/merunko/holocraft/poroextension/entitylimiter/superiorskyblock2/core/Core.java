package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.core.superiorskyblock2.island.IslandLoad;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.classes.EntitiesInWorld;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.configuration.EntityLimiterConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;

public class Core {

    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final EntityLimiterConfiguration entityLimiter;
    private final IslandLoad islandLoad = new IslandLoad();

    public Core(EntityLimiterConfiguration entityLimiter) {
        this.entityLimiter = entityLimiter;
    }

    public void onStart() {

        loadIsland();
        readMCAData();

    }

    public IslandLoad getIslandData() {
        return this.islandLoad;
    }

    private void loadIsland() {
        islandLoad.collectAllIslandsData();

        pc.sendMessage(console, "{65DB44}Loaded SuperiorSkyBlock2 islands.");
    }

    private void readMCAData() {

        EntitiesInWorld entitiesInWorld = new EntitiesInWorld();

        for (String worldName : entityLimiter.getAllowedWorlds()) {
            String folderPath = worldName + "/entities";
            File directory = new File(folderPath);

            if (!directory.exists()) {
                String[] alternateFolderPath = {worldName + "/DIM-1/entities", worldName + "/DIM1/entities"};
                for (String path : alternateFolderPath) {
                    File alternateDirectory = new File(path);
                    if (alternateDirectory.exists() && alternateDirectory.isDirectory()) {
                        File[] mcaFiles = alternateDirectory.listFiles((dir, name) -> name.endsWith(".mca"));

                        entitiesInWorld.readMCA(mcaFiles, worldName, islandLoad);
                    }
                }

            } else if (directory.exists() && directory.isDirectory()) {

                File[] mcaFiles = directory.listFiles((dir, name) -> name.endsWith(".mca"));
                entitiesInWorld.readMCA(mcaFiles, worldName, islandLoad);

            }
        }

        pc.sendMessage(console, "{65DB44}Loaded islands entities.");
    }

}
