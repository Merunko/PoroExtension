package me.merunko.holocraft.poroextension;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.fillcontainer.command.FCCommand;
import me.merunko.holocraft.poroextension.fillcontainer.configuration.FCConfiguration;
import me.merunko.holocraft.poroextension.porocommandgatcha.command.CGCommand;
import me.merunko.holocraft.poroextension.porocommandgatcha.configuration.CGConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class PoroExtension extends JavaPlugin {

    CGConfiguration cg;
    FCConfiguration fc;
    private final PoroColor pc = new PoroColor();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        Logger logger = getLogger();

        File file_command_gatcha = new File(getDataFolder(), "command-gatcha.yml");
        File file_fill_container = new File(getDataFolder(), "fill-container.yml");

        try {
            if (!file_command_gatcha.exists()) {
                saveResource("command-gatcha.yml", false);
                pc.sendMessage(console, "{E03C3C}Can't find command-gatcha.yml, generating command-gatcha.yml");
            }

            if (!file_fill_container.exists()) {
                saveResource("fill-container.yml", false);
                pc.sendMessage(console, "{E03C3C}Can't find fill-container.yml, generating fill-container.yml");
            }

            FileConfiguration command_gatchaConfigFile = YamlConfiguration.loadConfiguration(file_command_gatcha);
            cg = new CGConfiguration(command_gatchaConfigFile);
            pc.sendMessage(console, "{65DB44}Loaded command-gatcha.yml");
            cg.load();

            FileConfiguration fill_containerConfigFile = YamlConfiguration.loadConfiguration(file_fill_container);
            fc = new FCConfiguration(fill_containerConfigFile);
            pc.sendMessage(console, "{65DB44}Loaded fill-container.yml");
            fc.load();

        } catch (Exception e) {
            logger.info("Failed to create command-gatcha.yml");
        }

        Objects.requireNonNull(getCommand("poro")).setExecutor(new CGCommand(cg));
        Objects.requireNonNull(getCommand("poro")).setExecutor(new FCCommand(fc));
        Objects.requireNonNull(getCommand("poro")).setTabCompleter(new GlobalTabCompleter(cg, fc));
    }

    @Override
    public void onDisable() {

        pc.sendMessage(console, "{65DB44}Saving command-gatcha.yml...");
        cg.save();

        pc.sendMessage(console, "{65DB44}Saving fill-container.yml...");
        fc.save();

    }
}
