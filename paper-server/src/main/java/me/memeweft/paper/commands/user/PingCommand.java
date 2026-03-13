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
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Component.text("This command can only be used by players.", NamedTextColor.RED));
                    return Command.SINGLE_SUCCESS;
                }
                sender.sendMessage(buildMessage(player));
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.argument("target", ArgumentTypes.player())
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    List<Player> players = context.getArgument("target", PlayerSelectorArgumentResolver.class).resolve(context.getSource());
                    if (players.isEmpty()) {
                        sender.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }
                    sender.sendMessage(buildMessage(players.getFirst()));
                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }

    private static Component buildMessage(Player target) {
        int ping = target.getPing();
        NamedTextColor pingColor = pingColor(ping);

        return Component.text(target.getName() + "'s Ping: ", NamedTextColor.WHITE)
            .append(Component.text(ping + "ms", pingColor));
    }

    private static NamedTextColor pingColor(int ping) {
        if (ping <= 49) return NamedTextColor.GREEN;
        if (ping <= 70) return NamedTextColor.YELLOW;
        if (ping <= 120) return NamedTextColor.GOLD;
        return NamedTextColor.RED;
    }
}
