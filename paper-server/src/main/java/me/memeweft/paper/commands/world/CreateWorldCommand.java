package me.memeweft.paper.commands.world;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.memeweft.paper.world.WorldService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public final class CreateWorldCommand {

    private CreateWorldCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("createworld")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                context.getSource().getSender().sendMessage(
                    Component.text("Usage: /createworld <name>", NamedTextColor.RED)
                );
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    String name = StringArgumentType.getString(context, "name");

                    if (Bukkit.getWorld(name) != null) {
                        sender.sendMessage(
                            Component.text("Error: World '", NamedTextColor.RED)
                                .append(Component.text(name, NamedTextColor.WHITE))
                                .append(Component.text("' already exists.", NamedTextColor.RED))
                        );
                        return Command.SINGLE_SUCCESS;
                    }

                    WorldService.createWorld(name);

                    sender.sendMessage(
                        Component.text("You've created world ", NamedTextColor.WHITE)
                            .append(Component.text(name, NamedTextColor.RED))
                            .append(Component.text(" successfully.", NamedTextColor.WHITE))
                    );

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
