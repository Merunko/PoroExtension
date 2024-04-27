package me.merunko.holocraft.poroextension.function.porocommandgatcha.configuration;

import me.merunko.holocraft.poroextension.color.PoroColor;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommandGatchaConfiguration {

    private final FileConfiguration commandgatcha;
    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public CommandGatchaConfiguration(FileConfiguration config) {
        this.commandgatcha = config;
    }

    public void load() {
        try {
            commandgatcha.load(new File("plugins/PoroExtension/command-gatcha.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            pc.sendMessage(console, "{E03C3C}An error occurred while loading command-gatcha.yml file.");
        }
    }

    public void save() {
        try {
            commandgatcha.save(new File("plugins/PoroExtension/command-gatcha.yml"));
        } catch (IOException e) {
            pc.sendMessage(console, "{E03C3C}An error occurred while loading command-gatcha.yml file.");
        }
    }

    public Map<String, Integer> getCommandsAndChances(String identifier, String playerName) {
        ConfigurationSection commandsSection = commandgatcha.getConfigurationSection("identifiers." + identifier + ".commands");

        if (commandsSection != null) {
            return commandsSection.getKeys(false).stream()
                    .collect(HashMap::new,
                            (map, key) -> {
                                String command = Objects.requireNonNull(commandsSection.getString(key + ".cmd")).replace("%player%", playerName);
                                int chance = commandsSection.getInt(key + ".chance");
                                map.put(command, chance);
                            },
                            HashMap::putAll);
        }

        return null;
    }

    public String selectCommand(Map<String, Integer> commandsAndChances) {
        int totalChances = commandsAndChances.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = new Random().nextInt(totalChances) + 1;

        int currentSum = 0;
        for (Map.Entry<String, Integer> entry : commandsAndChances.entrySet()) {
            currentSum += entry.getValue();
            if (randomValue <= currentSum) {
                return entry.getKey();
            }
        }

        return selectCommand(commandsAndChances);
    }

    public List<String> getAllIdentifiers() {
        Set<String> identifierSet = Objects.requireNonNull(commandgatcha.getConfigurationSection("identifiers")).getKeys(false);
        return new ArrayList<>(identifierSet);
    }

}
