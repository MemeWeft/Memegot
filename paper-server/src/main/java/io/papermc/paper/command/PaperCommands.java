package io.papermc.paper.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandRegistrationFlag;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.memeweft.paper.commands.admin.MemegotCommand;
import me.memeweft.paper.commands.admin.PotionCommand;
import me.memeweft.paper.commands.admin.SetSlotsCommand;
import me.memeweft.paper.commands.admin.SetViewDistanceCommand;
import me.memeweft.paper.commands.user.PingCommand;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public final class PaperCommands {

    private PaperCommands() {
    }

    private static final Map<String, Command> COMMANDS = new HashMap<>();

    public static void registerCommands(final MinecraftServer server) {
        COMMANDS.put("paper", new PaperCommand("paper"));
        COMMANDS.put("mspt", new MSPTCommand("mspt"));

        COMMANDS.forEach((s, command) -> {
            server.server.getCommandMap().register(s, "Paper", command);
        });
    }

    public static void registerCommands() {
        // Paper commands go here
        registerInternalCommand(PaperVersionCommand.create(), "bukkit", PaperVersionCommand.DESCRIPTION, List.of("ver", "about"), Set.of());
        registerInternalCommand(PaperPluginsCommand.create(), "bukkit", PaperPluginsCommand.DESCRIPTION, List.of("pl"), Set.of());

        // Memegot commands
        registerInternalCommand(MemegotCommand.create(), "memegot", "Memegot server info", List.of(), Set.of());
        registerInternalCommand(SetViewDistanceCommand.create(), "memegot", "Set view distance", List.of(), Set.of());
        registerInternalCommand(SetSlotsCommand.create(), "memegot", "Set max player slots", List.of(), Set.of());
        registerInternalCommand(PotionCommand.create(), "memegot", "Set potion physics mode", List.of(), Set.of());

        registerInternalCommand(PingCommand.create(), "memegot", "Show player ping", List.of(), Set.of());
    }

    private static void registerInternalCommand(final LiteralCommandNode<CommandSourceStack> node, final String namespace, final String description, final List<String> aliases, final Set<CommandRegistrationFlag> flags) {
        io.papermc.paper.command.brigadier.PaperCommands.INSTANCE.registerWithFlagsInternal(
            null,
            namespace,
            "Paper",
            node,
            description,
            aliases,
            flags
        );
    }
}
