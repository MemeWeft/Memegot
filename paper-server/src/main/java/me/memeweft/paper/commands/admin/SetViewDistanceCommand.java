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
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public final class SetViewDistanceCommand {

    private SetViewDistanceCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("setviewdistance")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                context.getSource().getSender().sendMessage(
                    Component.text("Usage: /setviewdistance <amount>", NamedTextColor.RED)
                );
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.argument("chunks", IntegerArgumentType.integer(2, 32))
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    int chunks = IntegerArgumentType.getInteger(context, "chunks");

                    if (Bukkit.getWorlds().stream().allMatch(w -> w.getViewDistance() == chunks)) {
                        sender.sendMessage(
                            Component.text("Error: View distance is already at ", NamedTextColor.RED)
                                .append(Component.text(String.valueOf(chunks), NamedTextColor.WHITE))
                                .append(Component.text(" chunks.", NamedTextColor.RED))
                        );
                        return Command.SINGLE_SUCCESS;
                    }

                    for (World world : Bukkit.getWorlds()) {
                        world.setViewDistance(chunks);
                    }

                    BroadcastAction.toOps(sender, BroadcastAction.adminLog(sender,
                        "Set view distance to " + chunks + " chunks"));

                    sender.sendMessage(Component.text("Manually set view distance to ", NamedTextColor.WHITE)
                        .append(Component.text(String.valueOf(chunks), NamedTextColor.RED))
                        .append(Component.text(" chunks.", NamedTextColor.WHITE)));

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
