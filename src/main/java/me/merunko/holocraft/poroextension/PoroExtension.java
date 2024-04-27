package me.merunko.holocraft.poroextension;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.command.GlobalTabCompleter;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.configuration.EntityLimiterConfiguration;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.listener.*;
import me.merunko.holocraft.poroextension.function.fillcontainer.configuration.FillContainerConfiguration;
import me.merunko.holocraft.poroextension.command.Command;
import me.merunko.holocraft.poroextension.function.porocommandgatcha.configuration.CommandGatchaConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class PoroExtension extends JavaPlugin {

    private static CommandGatchaConfiguration COMMAND_GATCHA_CONFIG;
    private static FillContainerConfiguration FILL_CONTAINER_CONFIG;
    private static EntityLimiterConfiguration ENTITY_LIMITER_CONFIG;
    private static PoroExtension instance;
    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static PoroExtension getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        loadConfig("command-gatcha.yml");
        loadConfig("fill-container.yml");
        loadConfig("ssb-entity-limiter.yml");

        Core core = new Core(ENTITY_LIMITER_CONFIG);

        registerCommands(core);

        registerEvents(core);

        methodsOnPluginStart(core);
    }

    @Override
    public void onDisable() {

        methodsOnPluginOff();

        pc.sendMessage(console, "{65DB44}Saving command-gatcha.yml...");
        COMMAND_GATCHA_CONFIG.save();

        pc.sendMessage(console, "{65DB44}Saving fill-container.yml...");
        FILL_CONTAINER_CONFIG.save();

        pc.sendMessage(console, "{65DB44}Saving ssb-entity-limiter.yml...");
        ENTITY_LIMITER_CONFIG.save();

    }

    private void loadConfig(String fileName) {
        File configFile = new File(getDataFolder(), fileName);

        if (!configFile.exists()) {
            saveResource(fileName, false);
            pc.sendMessage(console, "{FB7D7D}Can't find {FBAE48}" + fileName + "{FB7D7D}, generating {FBAE48}" + fileName + "{FB7D7D}.");
        }

        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(configFile);

        if (fileName.equalsIgnoreCase("command-gatcha.yml")) {
            COMMAND_GATCHA_CONFIG = new CommandGatchaConfiguration(fileConfig);
            COMMAND_GATCHA_CONFIG.load();
            pc.sendMessage(console, "{65DB44}Loaded command-gatcha.yml");
        }

        else if (fileName.equalsIgnoreCase("fill-container.yml")) {
            FILL_CONTAINER_CONFIG = new FillContainerConfiguration(fileConfig);
            FILL_CONTAINER_CONFIG.load();
            pc.sendMessage(console, "{65DB44}Loaded fill-container.yml");
        }

        else if (fileName.equalsIgnoreCase("ssb-entity-limiter.yml")) {
            ENTITY_LIMITER_CONFIG = new EntityLimiterConfiguration(fileConfig);
            ENTITY_LIMITER_CONFIG.load();
            pc.sendMessage(console, "{65DB44}Loaded ssb-entity-limiter.yml");
        }
    }

    private void methodsOnPluginStart(Core core) {
        core.onStart();
    }

    private void methodsOnPluginOff() {

    }

    private void registerCommands(Core core) {
        Objects.requireNonNull(getCommand("poro")).setExecutor(new Command(COMMAND_GATCHA_CONFIG, FILL_CONTAINER_CONFIG, ENTITY_LIMITER_CONFIG, core));
        Objects.requireNonNull(getCommand("poro")).setTabCompleter(new GlobalTabCompleter(COMMAND_GATCHA_CONFIG, FILL_CONTAINER_CONFIG));
    }

    private void registerEvents(Core core) {

        if (ENTITY_LIMITER_CONFIG.limiterEnabled()) {

            getServer().getPluginManager().registerEvents(new CreatureSpawningEvent(core), this);
            getServer().getPluginManager().registerEvents(new CreatureDieEvent(ENTITY_LIMITER_CONFIG, core), this);
            getServer().getPluginManager().registerEvents(new VehicleCreatingEvent(core), this);
            getServer().getPluginManager().registerEvents(new VehicleDestroyedEvent(core), this);

            if (ENTITY_LIMITER_CONFIG.getEntityStackingPluginProvider().equalsIgnoreCase("RoseStacker")) {
                getServer().getPluginManager().registerEvents(new RoseStackerEntityStackEvent(core), this);
                getServer().getPluginManager().registerEvents(new RoseStackerEntityUnstackEvent(core), this);
                getServer().getPluginManager().registerEvents(new RoseStackerEntityStackClearEvent(core), this);
                pc.sendMessage(console, "{65DB44}Using &6RoseStacker {65DB44}as entity stacking provider.");
            }

            getServer().getPluginManager().registerEvents(new IslandCreationEvent(core), this);
            getServer().getPluginManager().registerEvents(new IslandDisbandingEvent(core), this);

            getServer().getPluginManager().registerEvents(new HangingPlacingEvent(core), this);
            getServer().getPluginManager().registerEvents(new HangingBrokeEvent(core), this);

        }
    }

}
