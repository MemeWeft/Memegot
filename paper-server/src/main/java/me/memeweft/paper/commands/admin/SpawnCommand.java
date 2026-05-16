package me.memeweft.paper.commands.admin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.memeweft.paper.config.ConfigService;
import me.memeweft.paper.utility.BroadcastAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SpawnCommand {

    private SpawnCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("spawn")
            .executes(context -> {
                CommandSender sender = context.getSource().getSender();
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
                    return Command.SINGLE_SUCCESS;
                }

                Location spawn = ConfigService.getSpawn();
                if (spawn == null) {
                    sender.sendMessage(Component.text("Error: Spawn has not been set.", NamedTextColor.RED));
                    return Command.SINGLE_SUCCESS;
                }

                player.teleport(spawn);
                player.sendMessage(Component.text("You've been teleported to spawn.", NamedTextColor.WHITE));

                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.literal("set")
                .requires(source -> source.getSender().hasPermission("memegot.admin"))
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }

                    Location location = player.getLocation();
                    ConfigService.setSpawn(location);

                    player.sendMessage(
                        Component.text("Spawn set to ", NamedTextColor.WHITE)
                            .append(Component.text(location.getWorld().getName(), NamedTextColor.RED))
                            .append(Component.text(" (", NamedTextColor.GRAY))
                            .append(Component.text(
                                String.format("%.1f, %.1f, %.1f", location.getX(), location.getY(), location.getZ()),
                                NamedTextColor.WHITE))
                            .append(Component.text(")", NamedTextColor.GRAY))
                    );

                    BroadcastAction.toOps(sender, BroadcastAction.adminLog(sender,
                        "Set spawn in '" + location.getWorld().getName() + "'"));

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
