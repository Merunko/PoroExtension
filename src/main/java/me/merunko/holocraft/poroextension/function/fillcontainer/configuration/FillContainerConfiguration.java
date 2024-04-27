package me.merunko.holocraft.poroextension.function.fillcontainer.configuration;

import me.merunko.holocraft.poroextension.color.PoroColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FillContainerConfiguration {

    private final FileConfiguration fillcontainer;
    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public FillContainerConfiguration(FileConfiguration fillcontainer) {
        this.fillcontainer = fillcontainer;
    }

    public void load() {
        try {
            fillcontainer.load(new File("plugins/PoroExtension/fill-container.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            pc.sendMessage(console, "{{E03C3C}An error occurred while loading fill-container.yml file.");
        }
    }

    public void save() {
        try {
            fillcontainer.save(new File("plugins/PoroExtension/fill-container.yml"));
        } catch (IOException e) {
            pc.sendMessage(console, "{E03C3C}An error occurred while saving fill-container.yml file.");
        }
    }

    public List<String> getAllIdentifiers() {
        Set<String> identifierSet = Objects.requireNonNull(fillcontainer.getConfigurationSection("identifiers")).getKeys(false);
        return new ArrayList<>(identifierSet);
    }

    public Map<String, Map<String, Map<String, Object>>> getIdentifiers() {
        Map<String, Map<String, Map<String, Object>>> identifiers = new HashMap<>();
        ConfigurationSection identifiersSection = fillcontainer.getConfigurationSection("identifiers");
        if (identifiersSection != null) {
            for (String identifierKey : identifiersSection.getKeys(false)) {
                ConfigurationSection slotSection = identifiersSection.getConfigurationSection(identifierKey + ".slot");
                if (slotSection != null) {
                    Map<String, Map<String, Object>> slots = new HashMap<>();
                    for (String slotKey : slotSection.getKeys(false)) {
                        ConfigurationSection slotValuesSection = slotSection.getConfigurationSection(slotKey);
                        if (slotValuesSection != null) {
                            Map<String, Object> slotValues = slotValuesSection.getValues(false);

                            String item = (String) slotValues.get("item");
                            Material material = (item != null) ? Material.getMaterial(item) : Material.AIR;
                            int amount = (slotValues.get("amount") != null) ? (int) slotValues.get("amount") : 0;

                            int slot = Integer.parseInt(slotKey);
                            if (slot >= 0 && slot <= 53) {

                                Map<String, Object> managedSlotValues = new HashMap<>();
                                managedSlotValues.put("item", material);
                                managedSlotValues.put("amount", amount);

                                slots.put(slotKey, managedSlotValues);
                            } else {
                                pc.sendMessage(console, "{E03C3C}Invalid slot number: &6" + slot + ".{E03C3C}Slot number must be between 0 to 53!");
                            }
                        }
                    }
                    identifiers.put(identifierKey, slots);
                }
            }
        }
        return identifiers;
    }


}
