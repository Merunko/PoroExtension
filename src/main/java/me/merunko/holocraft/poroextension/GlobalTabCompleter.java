package me.merunko.holocraft.poroextension;

import me.merunko.holocraft.poroextension.fillcontainer.configuration.FCConfiguration;
import me.merunko.holocraft.poroextension.porocommandgatcha.configuration.CGConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GlobalTabCompleter implements TabCompleter {

    private final CGConfiguration commandgatcha;
    private final FCConfiguration fillcontainer;

    public GlobalTabCompleter(CGConfiguration commandgatcha, FCConfiguration fillcontainer) {
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
                break;
            case 2:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    completions.add("command");
                } else if (args[0].equalsIgnoreCase("fill")) {
                    completions.addAll(fillcontainer.getAllIdentifiers());
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    completions.add("run");
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    completions.addAll(commandgatcha.getAllIdentifiers());
                }
                break;
            case 5:
                if (args[0].equalsIgnoreCase("gatcha")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
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
