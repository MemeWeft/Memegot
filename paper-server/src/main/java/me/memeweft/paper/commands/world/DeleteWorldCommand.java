package me.memeweft.paper.commands.world;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.memeweft.paper.utility.BroadcastAction;
import me.memeweft.paper.world.WorldService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class DeleteWorldCommand {

    private DeleteWorldCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("deleteworld")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                context.getSource().getSender().sendMessage(
                    Component.text("Usage: /deleteworld <world>", NamedTextColor.RED)
                );
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.argument("world", StringArgumentType.word())
                .suggests((context, builder) -> {
                    World defaultWorld = Bukkit.getWorlds().getFirst();
                    Bukkit.getWorlds().stream()
                        .filter(world -> !world.equals(defaultWorld))
                        .forEach(world -> builder.suggest(world.getName()));
                    return builder.buildFuture();
                })
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    String worldName = StringArgumentType.getString(context, "world");

                    World defaultWorld = Bukkit.getWorlds().getFirst();
                    World target = Bukkit.getWorld(worldName);

                    if (target == null) {
                        sender.sendMessage(Component.text("Error: Couldn't find that world.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }

                    if (target.equals(defaultWorld)) {
                        sender.sendMessage(
                            Component.text("Error: Cannot delete the default world '", NamedTextColor.RED)
                                .append(Component.text(defaultWorld.getName(), NamedTextColor.WHITE))
                                .append(Component.text("'.", NamedTextColor.RED))
                        );
                        return Command.SINGLE_SUCCESS;
                    }

                    for (Player p : target.getPlayers()) {
                        p.teleport(defaultWorld.getSpawnLocation());
                    }

                    BroadcastAction.toOps(sender, BroadcastAction.adminLog(sender, "Started world deletion for '" + worldName + "'"));

                    WorldService.deleteWorld(target);

                    sender.sendMessage(
                        Component.text("You've deleted world ", NamedTextColor.WHITE)
                            .append(Component.text(worldName, NamedTextColor.RED))
                            .append(Component.text(".", NamedTextColor.WHITE))
                    );

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
