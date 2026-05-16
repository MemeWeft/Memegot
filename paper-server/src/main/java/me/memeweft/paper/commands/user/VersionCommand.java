package me.memeweft.paper.commands.user;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;

public final class VersionCommand {

    private VersionCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("version")
            .executes(context -> {
                CommandSender sender = context.getSource().getSender();
                String commitHash = io.papermc.paper.ServerBuildInfo.buildInfo().gitCommit().orElse("unknown");
                String mcVersion = io.papermc.paper.ServerBuildInfo.buildInfo().minecraftVersionId();

                sender.sendMessage(Component.empty());
                sender.sendMessage(Component.text("This server is running ", NamedTextColor.WHITE)
                    .append(Component.text("Memegot ", NamedTextColor.RED))
                    .append(Component.text("version " + commitHash, NamedTextColor.WHITE))
                    .append(Component.text(" (Implementing API version ", NamedTextColor.WHITE))
                    .append(Component.text(mcVersion + "-R0.1-SNAPSHOT", NamedTextColor.WHITE))
                    .append(Component.text(")", NamedTextColor.WHITE)));
                sender.sendMessage(Component.empty());

                return com.mojang.brigadier.Command.SINGLE_SUCCESS;
            })
            .build();
    }
}
