package me.memeweft.paper.commands.user;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class PingCommand {

    private PingCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("ping")
            .executes(context -> {
                CommandSender sender = context.getSource().getSender();
                if (!(sender instanceof Player self)) {
                    sender.sendMessage(Component.text("Only players can use /ping without a target.", NamedTextColor.RED));
                    return Command.SINGLE_SUCCESS;
                }
                sendPing(sender, self);
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.argument("target", ArgumentTypes.player())
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    List<Player> targets = context.getArgument("target", PlayerSelectorArgumentResolver.class)
                        .resolve(context.getSource());
                    if (targets.isEmpty()) {
                        sender.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }
                    sendPing(sender, targets.getFirst());
                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }

    private static void sendPing(CommandSender sender, Player target) {
        sender.sendMessage(
            Component.text(target.getName(), NamedTextColor.RED)
                .append(Component.text("'s ping", NamedTextColor.WHITE))
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(Component.text(String.valueOf(target.getPing()), NamedTextColor.RED))
                .append(Component.text(" ms", NamedTextColor.WHITE))
        );
    }
}
