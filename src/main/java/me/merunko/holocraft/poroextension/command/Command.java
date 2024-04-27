package me.merunko.holocraft.poroextension.command;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.commands.Commands;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.configuration.EntityLimiterConfiguration;
import me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.core.Core;
import me.merunko.holocraft.poroextension.function.fillcontainer.configuration.FillContainerConfiguration;
import me.merunko.holocraft.poroextension.function.porocommandgatcha.configuration.CommandGatchaConfiguration;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {

    private final CommandGatchaConfiguration commandgatcha;
    private final FillContainerConfiguration fillcontainer;
    private final EntityLimiterConfiguration entitylimiter;
    private final PoroColor pc = new PoroColor();
    private final Core core;

    public Command(CommandGatchaConfiguration commandgatcha, FillContainerConfiguration fillcontainer, EntityLimiterConfiguration entitylimiter, Core core) {
        this.commandgatcha = commandgatcha;
        this.fillcontainer = fillcontainer;
        this.entitylimiter = entitylimiter;
        this.core = core;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {

        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfigs(commandSender);
                    return true;
                }
                break;

            case 2:
                if (args[0].equalsIgnoreCase("fill")) {
                    me.merunko.holocraft.poroextension.function.fillcontainer.commands.Commands cmd = new me.merunko.holocraft.poroextension.function.fillcontainer.commands.Commands();
                    cmd.runFillCommand(commandSender, args, fillcontainer);
                    return true;
                }
                break;

            case 4:
                if (args[0].equalsIgnoreCase("limiter") && args[1].equalsIgnoreCase("get")) {
                    me.merunko.holocraft.poroextension.function.entitylimiter.superiorskyblock2.commands.Commands cmd = new Commands(core);
                    cmd.getEntityCount(commandSender, args);
                    return true;
                }
                break;

            case 5:
                if (args[0].equalsIgnoreCase("gatcha") && args[1].equalsIgnoreCase("command") && args[2].equalsIgnoreCase("run")) {
                    me.merunko.holocraft.poroextension.function.porocommandgatcha.commands.Commands cmd = new me.merunko.holocraft.poroextension.function.porocommandgatcha.commands.Commands();
                    cmd.runGatchaCommand(commandSender, args, commandgatcha);
                    return true;
                } else if (args[0].equalsIgnoreCase("limiter") && args[1].equalsIgnoreCase("set")) {
                    Commands cmd = new Commands(core);
                    cmd.changeEntityCount(commandSender, args);
                    return true;
                }
                break;

        }

        return false;
    }

    private void reloadConfigs(CommandSender commandSender) {
        if (commandSender.hasPermission("poro.reload")) {
            commandgatcha.load();
            fillcontainer.load();
            entitylimiter.load();
            pc.sendMessage(commandSender, "{65DB44}Reloaded all configs.");
        } else {
            pc.sendMessage(commandSender, "{E03C3C}You don't have permission to run that command!");
        }

    }

}
