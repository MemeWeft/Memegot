package me.memeweft.paper.commands.world;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TpWorldCommand {

    private TpWorldCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("tpworld")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                context.getSource().getSender().sendMessage(
                    Component.text("Usage: /tpworld <world>", NamedTextColor.RED)
                );
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.argument("world", StringArgumentType.word())
                .suggests((context, builder) -> {
                    Bukkit.getWorlds().forEach(world -> builder.suggest(world.getName()));
                    return builder.buildFuture();
                })
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }

                    String worldName = StringArgumentType.getString(context, "world");
                    World target = Bukkit.getWorld(worldName);

                    if (target == null) {
                        sender.sendMessage(Component.text("Error: Couldn't find that world.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }

                    if (player.getWorld().equals(target)) {
                        sender.sendMessage(
                            Component.text("Error: You're already in world '", NamedTextColor.RED)
                                .append(Component.text(worldName, NamedTextColor.WHITE))
                                .append(Component.text("'.", NamedTextColor.RED))
                        );
                        return Command.SINGLE_SUCCESS;
                    }

                    String oldWorld = player.getWorld().getName();
                    player.teleportAsync(target.getSpawnLocation());

                    sender.sendMessage(
                        Component.text("You've teleported from ", NamedTextColor.WHITE)
                            .append(Component.text(oldWorld, NamedTextColor.RED))
                            .append(Component.text(" to ", NamedTextColor.WHITE))
                            .append(Component.text(worldName, NamedTextColor.GREEN))
                            .append(Component.text(".", NamedTextColor.WHITE))
                    );

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
