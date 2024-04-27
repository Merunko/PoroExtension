package me.merunko.holocraft.poroextension.function.porocommandgatcha.commands;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.function.porocommandgatcha.configuration.CommandGatchaConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class Commands {

    private final PoroColor pc = new PoroColor();

    public void runGatchaCommand(CommandSender sender, String[] args, CommandGatchaConfiguration commandgatcha) {
        if (sender.hasPermission("poro.commandgatcha.run")) {
            String identifier = args[3];
            String playerName = args[4];
            Map<String, Integer> commandsAndChances = commandgatcha.getCommandsAndChances(identifier, playerName);

            if (commandsAndChances != null && !commandsAndChances.isEmpty()) {
                String selectedCommand = commandgatcha.selectCommand(commandsAndChances);

                Bukkit.dispatchCommand(sender, selectedCommand);

            } else {
                pc.sendMessage(sender, "{E03C3C}No commands found for the specified identifier. Identifier: &6" + identifier);
            }
        } else {
            pc.sendMessage(sender, "{E03C3C}You don't have permission to run that command!");
        }
    }

}
