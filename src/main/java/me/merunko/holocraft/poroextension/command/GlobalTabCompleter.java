package me.merunko.holocraft.poroextension.command;

import me.merunko.holocraft.poroextension.function.fillcontainer.configuration.FillContainerConfiguration;
import me.merunko.holocraft.poroextension.function.porocommandgatcha.configuration.CommandGatchaConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GlobalTabCompleter implements TabCompleter {

    private final CommandGatchaConfiguration commandgatcha;
    private final FillContainerConfiguration fillcontainer;

    public GlobalTabCompleter(CommandGatchaConfiguration commandgatcha, FillContainerConfiguration fillcontainer) {
        this.commandgatcha = commandgatcha;
        this.fillcontainer = fillcontainer;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1:
                completions.add("reload");
                completions.add("gatcha");
                completions.add("fill");
                completions.add("limiter");
                break;
            case 2:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    completions.add("command");
                } else if (args[0].equalsIgnoreCase("fill")) {
                    completions.addAll(fillcontainer.getAllIdentifiers());
                } else if (args[0].equalsIgnoreCase("limiter")) {
                    completions.add("get");
                    completions.add("set");
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    completions.add("run");
                } else if (args[0].equalsIgnoreCase("limiter")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    completions.addAll(commandgatcha.getAllIdentifiers());
                } else if (args[0].equalsIgnoreCase("limiter") && ( args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("get") ) ) {
                    for (EntityType entityType : EntityType.values()) {
                        completions.add(String.valueOf(entityType));
                    }
                }
                break;
            case 5:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                } else if (args[0].equalsIgnoreCase("limiter") && args[1].equalsIgnoreCase("set")) {
                    for (int i = 0; i<10 ; i++) {
                        completions.add(String.valueOf(i));
                    }
                }
                break;
        }

        String input = args[args.length - 1].toLowerCase();
        List<String> filteredCompletions = new ArrayList<>();
        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(input)) {
                filteredCompletions.add(completion);
            }
        }

        return filteredCompletions;
    }
}
