package me.memeweft.paper.commands.admin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.memeweft.paper.utility.BroadcastAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public final class ClearEntitiesCommand {

    private ClearEntitiesCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("clearentities")
            .requires(source -> source.getSender().hasPermission("memegot.admin"))
            .executes(context -> {
                CommandSender sender = context.getSource().getSender();
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
                    return Command.SINGLE_SUCCESS;
                }

                List<Entity> entities = player.getWorld().getEntities().stream()
                    .filter(e -> e.getType() != EntityType.PLAYER)
                    .toList();

                if (entities.isEmpty()) {
                    sender.sendMessage(Component.text("Error: There are no entities to clear.", NamedTextColor.RED));
                    return Command.SINGLE_SUCCESS;
                }

                entities.forEach(Entity::remove);

                BroadcastAction.toOps(sender, BroadcastAction.adminLog(sender,
                    "Cleared " + entities.size() + " entities in '" + player.getWorld().getName() + "'"));

                sender.sendMessage(
                    Component.text("You've cleared ", NamedTextColor.WHITE)
                        .append(Component.text(String.valueOf(entities.size()), NamedTextColor.RED))
                        .append(Component.text(" entities.", NamedTextColor.WHITE))
                );

                return Command.SINGLE_SUCCESS;
            })
            .build();
    }
}
