package me.memeweft.paper.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public final class BroadcastAction {

    private BroadcastAction() {}

    public static void toOps(CommandSender executor, Component message) {
        Bukkit.getConsoleSender().sendMessage(message);
        Bukkit.getOnlinePlayers().stream()
            .filter(player -> player.isOp() && !player.equals(executor))
            .forEach(player -> player.sendMessage(message));
    }

    public static Component adminLog(CommandSender executor, String action) {
        return Component.text("[", NamedTextColor.GRAY).decorate(TextDecoration.ITALIC)
            .append(Component.text(executor.getName()))
            .append(Component.text(": "))
            .append(Component.text(action))
            .append(Component.text("]"));
    }
}
