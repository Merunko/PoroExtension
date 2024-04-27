package me.merunko.holocraft.poroextension.function.fillcontainer.commands;

import me.merunko.holocraft.poroextension.color.PoroColor;
import me.merunko.holocraft.poroextension.function.fillcontainer.configuration.FillContainerConfiguration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Commands {

    private final PoroColor pc = new PoroColor();

    public void runFillCommand(CommandSender commandSender, String[] args, FillContainerConfiguration fillContainer) {
        if (commandSender.hasPermission("poro.fill.container")) {

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
            Map<String, Map<String, Object>> identifierSlots = fillContainer.getIdentifiers().get(identifier);

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
}
