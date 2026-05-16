package me.memeweft.paper.commands.admin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.memeweft.paper.utility.BroadcastAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public final class SetSlotsCommand {

    private SetSlotsCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("setslots")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                context.getSource().getSender().sendMessage(
                    Component.text("Usage: /setslots <amount>", NamedTextColor.RED)
                );
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.argument("slots", IntegerArgumentType.integer(1, 10000))
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    int slots = IntegerArgumentType.getInteger(context, "slots");

                    if (slots == Bukkit.getMaxPlayers()) {
                        sender.sendMessage(
                            Component.text("Error: Max player count is already at ", NamedTextColor.RED)
                                .append(Component.text(String.valueOf(slots), NamedTextColor.WHITE))
                                .append(Component.text(".", NamedTextColor.RED))
                        );
                        return Command.SINGLE_SUCCESS;
                    }

                    Bukkit.setMaxPlayers(slots);

                    BroadcastAction.toOps(sender, BroadcastAction.adminLog(sender,
                        "Set max players to " + slots));

                    sender.sendMessage(Component.text("Manually set max players to ", NamedTextColor.WHITE)
                        .append(Component.text(String.valueOf(slots), NamedTextColor.RED))
                        .append(Component.text(" slots.", NamedTextColor.WHITE)));

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
