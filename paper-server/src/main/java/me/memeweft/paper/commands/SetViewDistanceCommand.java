package me.memeweft.paper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
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
            .then(Commands.argument("chunks", IntegerArgumentType.integer(2, 32))
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    int chunks = IntegerArgumentType.getInteger(context, "chunks");

                    for (World world : Bukkit.getWorlds()) {
                        world.setViewDistance(chunks);
                    }

                    sender.sendMessage(Component.text("Manually set view distance to ", NamedTextColor.WHITE)
                        .append(Component.text(String.valueOf(chunks), NamedTextColor.RED))
                        .append(Component.text(" chunks.", NamedTextColor.WHITE)));

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
