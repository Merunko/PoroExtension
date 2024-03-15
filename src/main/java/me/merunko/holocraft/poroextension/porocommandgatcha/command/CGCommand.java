package me.merunko.holocraft.poroextension.porocommandgatcha.command;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.porocommandgatcha.configuration.CGConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CGCommand implements CommandExecutor {

    private final CGConfiguration commandgatcha;
    private final PoroColor pc = new PoroColor();

    public CGCommand(CGConfiguration commandgatcha) {
        this.commandgatcha = commandgatcha;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!commandSender.hasPermission("poro.reload")) {
            pc.sendMessage(commandSender, "{E03C3C}You don't have permission to run this command!");
            return true;
        }

        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig(commandSender);
                    return true;
                }
                break;

            case 5:
                if (args[0].equalsIgnoreCase("gatcha") && args[1].equalsIgnoreCase("command") && args[2].equalsIgnoreCase("run")) {
                    runGatchaCommand(commandSender, args);
                    return true;
                }
                break;
        }

        return false;
    }

    private void reloadConfig(CommandSender commandSender) {
        commandgatcha.load();
        pc.sendMessage(commandSender, "{65DB44}Reloaded command-gatcha.yml.");
    }

    private void runGatchaCommand(CommandSender commandSender, String[] args) {
        String identifier = args[3];
        String playerName = args[4];
        Map<String, Integer> commandsAndChances = commandgatcha.getCommandsAndChances(identifier, playerName);

        if (commandsAndChances != null && !commandsAndChances.isEmpty()) {
            String selectedCommand = commandgatcha.selectCommand(commandsAndChances);

            Bukkit.dispatchCommand(commandSender, selectedCommand);

        } else {
            pc.sendMessage(commandSender, "{E03C3C}No commands found for the specified identifier. Identifier: &6" + identifier);
        }
    }
}
