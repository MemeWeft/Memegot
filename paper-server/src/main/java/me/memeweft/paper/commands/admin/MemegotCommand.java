package me.memeweft.paper.commands.admin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.memeweft.paper.MemeAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

public final class MemegotCommand {

    private MemegotCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("memegot")
            .executes(context -> {
                CommandSender sender = context.getSource().getSender();

                sender.sendMessage(Component.empty());
                sender.sendMessage(Component.text("Memegot Info", NamedTextColor.RED, TextDecoration.BOLD));
                sender.sendMessage(Component.text("  Version: ", NamedTextColor.WHITE)
                    .append(Component.text(MemeAPI.VERSION, NamedTextColor.RED)));
                sender.sendMessage(Component.text("  Author: ", NamedTextColor.WHITE)
                    .append(Component.text("MemeWeft", NamedTextColor.RED)));
                sender.sendMessage(Component.empty());

                return Command.SINGLE_SUCCESS;
            })
            .build();
    }
}
