package me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.configuration;

import me.merunko.holocraft.poroextension.color.PoroColor;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EntityLimiterConfiguration {

    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final FileConfiguration entityLimiter;

    public EntityLimiterConfiguration(FileConfiguration entityLimiter) {
        this.entityLimiter = entityLimiter;
    }

    public void load() {
        try {
            entityLimiter.load(new File("plugins/PoroExtension/ssb-entity-limiter.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            pc.sendMessage(console, "{E03C3C}An error occurred while loading ssb-entity-limiter.yml file.");
        }
    }

    public void save() {
        try {
            entityLimiter.save(new File("plugins/PoroExtension/ssb-entity-limiter.yml"));
        } catch (IOException e) {
            pc.sendMessage(console, "{E03C3C}An error occurred while saving ssb-entity-limiter.yml file.");
        }
    }

    public boolean limiterEnabled() {
        String key = "limiter.enabled";
        return entityLimiter.getBoolean(key);
    }

    public List<String> getAllowedWorlds() {
        String key = "limiter.allowed-worlds";
        if (entityLimiter.isList(key)) {
            return entityLimiter.getStringList(key);
        }
        return Collections.singletonList("");
    }

    public String getEntityStackingPluginProvider() {
        String key = "limiter.entity-stacking-plugin-provider";
        if (entityLimiter.isString(key)){
            String provider = entityLimiter.getString(key);
            if (Objects.requireNonNull(provider).equalsIgnoreCase("NONE")) {
                return "NONE";
            } else if (provider.equalsIgnoreCase("RoseStacker")) {
                return "RoseStacker";
            } else {
                return "NONE";
            }
        }
        return "NONE";
    }

}
