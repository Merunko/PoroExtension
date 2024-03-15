package me.merunko.holocraft.poroextension.fillcontainer.command;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.fillcontainer.configuration.FCConfiguration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FCCommand implements CommandExecutor {

    private final FCConfiguration fillcontainer;
    private final PoroColor pc = new PoroColor();

    public FCCommand(FCConfiguration fillcontainer) {
        this.fillcontainer = fillcontainer;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

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

            case 2:
                if (args[0].equalsIgnoreCase("fill")) {
                    runFillCommand(commandSender, args);
                    return true;
                }
                break;
        }

        return false;
    }

    private void reloadConfig(CommandSender commandSender) {
        fillcontainer.load();
        pc.sendMessage(commandSender, "{65DB44}Reloaded fill-container.yml.");
    }

    private void runFillCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            pc.sendMessage(commandSender, "{E03C3C}This command can only be executed by a player.");
            return;
        }

        Player player = (Player) commandSender;
        Block targetBlock = player.getTargetBlock(null, 10);

        if (!(targetBlock.getState() instanceof Chest)) {
            pc.sendMessage(commandSender, "{E03C3C}You are not looking at a chest.");
            return;
        }

        Chest chest = (Chest) targetBlock.getState();
        Inventory chestInventory = chest.getInventory();

        String identifier = args[1];
        Map<String, Map<String, Object>> identifierSlots = fillcontainer.getIdentifiers().get(identifier);

        if (identifierSlots != null) {
            for (String slotKey : identifierSlots.keySet()) {
                Map<String, Object> slotValues = identifierSlots.get(slotKey);
                Material itemMaterial = (Material) slotValues.get("item");
                int amount = (int) slotValues.get("amount");
                ItemStack itemStack = new ItemStack(itemMaterial, amount);

                chestInventory.setItem(Integer.parseInt(slotKey), itemStack);
            }
            pc.sendMessage(commandSender, "{65DB44}Chest filled with items from the '&6" + identifier + "' {65DB44}identifier.");
        } else {
            pc.sendMessage(commandSender, "{E03C3C}Identifier '&6" + identifier + "' {E03C3C}not found in configuration.");
        }
    }

}
