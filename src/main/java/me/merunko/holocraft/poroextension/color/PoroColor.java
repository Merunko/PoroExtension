package me.merunko.holocraft.poroextension.color;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.command.CommandSender;

public class PoroColor {

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(translate("{DB9444>}⟢ PoroExtension ⟣ &l| {<AEDB44}" + message));
    }

    public void sendMessageNoPluginName(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    public Component translate(String text) {
        return translationFormat(text);
    }

    private static Component translationFormat(String input) {
        String translated = transformHex(input);
        translated = transformGradient(translated);
        translated = transformMinecraftColorCode(translated);

        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(translated);
    }

    private static String transformGradient(String input) {
        return input.replaceAll("\\{([A-Fa-f0-9]{6})>}([^{]*)\\{<([A-Fa-f0-9]{6})}", "<gradient:#$1:#$3>$2</gradient>");
    }

    private static String transformHex(String input) {
        return input.replaceAll("\\{([A-Fa-f0-9]{6})\\}", "<#$1>");
    }

    private static String transformMinecraftColorCode(String input) {
        return input.replaceAll("&l", "<bold>")
                .replaceAll("&o", "<italic>")
                .replaceAll("&k", "<obfuscated>")
                .replaceAll("&n", "<underlined>")
                .replaceAll("&m", "<strikethrough>")
                .replaceAll("&r", "<reset>")
                .replaceAll("&0", "<black>")
                .replaceAll("&1", "<dark_blue>")
                .replaceAll("&2", "<dark_green>")
                .replaceAll("&3", "<dark_aqua>")
                .replaceAll("&4", "<dark_red>")
                .replaceAll("&5", "<dark_purple>")
                .replaceAll("&6", "<gold>")
                .replaceAll("&7", "<gray>")
                .replaceAll("&8", "<dark_gray>")
                .replaceAll("&9", "<blue>")
                .replaceAll("&a", "<green>")
                .replaceAll("&b", "<aqua>")
                .replaceAll("&c", "<red>")
                .replaceAll("&d", "<light_purple>")
                .replaceAll("&e", "<yellow>")
                .replaceAll("&f", "<white>");
    }
}
