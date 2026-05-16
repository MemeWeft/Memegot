package me.memeweft.paper.commands.world;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

public final class ListWorldsCommand {

    private ListWorldsCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("listworlds")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                List<World> worlds = Bukkit.getWorlds();
                World defaultWorld = worlds.getFirst();

                context.getSource().getSender().sendMessage(Component.empty());

                context.getSource().getSender().sendMessage(
                    Component.text("Existing Worlds ", NamedTextColor.RED, TextDecoration.BOLD)
                        .append(Component.text("(" + worlds.size() + ")", NamedTextColor.GRAY)
                            .decoration(TextDecoration.BOLD, false))
                );

                for (World world : worlds) {
                    boolean isDefault = world.equals(defaultWorld);

                    Component line = Component.text(" ● ", NamedTextColor.WHITE, TextDecoration.BOLD)
                        .append(Component.text(world.getName(), NamedTextColor.WHITE)
                            .decoration(TextDecoration.BOLD, false))
                        .append(isDefault
                            ? Component.text(" (DEFAULT)", NamedTextColor.RED)
                              .decoration(TextDecoration.BOLD, false)
                            : Component.empty());

                    context.getSource().getSender().sendMessage(line);
                }

                context.getSource().getSender().sendMessage(Component.empty());

                return Command.SINGLE_SUCCESS;
            })
            .build();
    }
}
