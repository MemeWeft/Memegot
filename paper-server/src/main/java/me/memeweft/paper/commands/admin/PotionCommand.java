package me.memeweft.paper.commands.admin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.memeweft.paper.Memegot;
import me.memeweft.paper.enums.CombatMode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PotionCommand {

    private PotionCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("potion")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                sendHelp(context.getSource().getSender());
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.literal("legacy")
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage(Component.text("This command can only be used by players.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }

                    Memegot.potionEngine().set(player.getUniqueId(), CombatMode.LEGACY);
                    sender.sendMessage(Component.text("Potion mode set to ", NamedTextColor.WHITE)
                        .append(Component.text("LEGACY", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(".", NamedTextColor.WHITE)));

                    return Command.SINGLE_SUCCESS;
                }))
            .then(Commands.literal("modern")
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage(Component.text("This command can only be used by players.", NamedTextColor.RED));
                        return Command.SINGLE_SUCCESS;
                    }

                    Memegot.potionEngine().set(player.getUniqueId(), CombatMode.MODERN);
                    sender.sendMessage(Component.text("Potion mode set to ", NamedTextColor.WHITE)
                        .append(Component.text("MODERN", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(".", NamedTextColor.WHITE)));

                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }

    private static void sendHelp(CommandSender sender) {
        sender.sendMessage(Component.empty());
        sender.sendMessage(Component.text("Potion Commands", NamedTextColor.RED, TextDecoration.BOLD));
        sender.sendMessage(Component.text("  /potion legacy", NamedTextColor.RED)
            .append(Component.text(" — switch to 1.7 potion physics", NamedTextColor.WHITE)));
        sender.sendMessage(Component.text("  /potion modern", NamedTextColor.RED)
            .append(Component.text(" — switch to modern potion physics", NamedTextColor.WHITE)));
        sender.sendMessage(Component.empty());
    }
}
