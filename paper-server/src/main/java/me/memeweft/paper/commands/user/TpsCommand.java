package me.memeweft.paper.commands.user;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;

public final class TpsCommand {

    private static final ThreadLocal<DecimalFormat> ONE_DECIMAL = ThreadLocal.withInitial(() -> new DecimalFormat("########0.0"));

    private TpsCommand() {}

    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("tps")
            .executes(context -> {
                sendTps(context.getSource().getSender(), false);
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.literal("mem")
                .executes(context -> {
                    sendTps(context.getSource().getSender(), true);
                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }

    private static void sendTps(CommandSender sender, boolean showMemory) {
        double[] tps = Bukkit.getTPS();
        double mspt = Bukkit.getAverageTickTime();

        sender.sendMessage(
            Component.text("TPS from last ", NamedTextColor.WHITE)
                .append(Component.text("1m", NamedTextColor.RED))
                .append(Component.text(", ", NamedTextColor.WHITE))
                .append(Component.text("5m", NamedTextColor.RED))
                .append(Component.text(", ", NamedTextColor.WHITE))
                .append(Component.text("15m", NamedTextColor.RED))
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(formatTps(tps[0]))
                .append(Component.text(", ", NamedTextColor.WHITE))
                .append(formatTps(tps[1]))
                .append(Component.text(", ", NamedTextColor.WHITE))
                .append(formatTps(tps[2]))
        );

        sender.sendMessage(
            Component.text("MSPT", NamedTextColor.WHITE)
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(formatMspt(mspt))
                .append(Component.text(" ms", NamedTextColor.WHITE))
        );

        if (showMemory) {
            long used  = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
            long total = Runtime.getRuntime().totalMemory() / (1024 * 1024);
            long max   = Runtime.getRuntime().maxMemory()   / (1024 * 1024);

            sender.sendMessage(
                Component.text("Memory", NamedTextColor.WHITE)
                    .append(Component.text(": ", NamedTextColor.GRAY))
                    .append(Component.text(used + " mb", NamedTextColor.RED))
                    .append(Component.text(" / ", NamedTextColor.GRAY))
                    .append(Component.text(total + " mb", NamedTextColor.WHITE))
                    .append(Component.text("  max ", NamedTextColor.GRAY))
                    .append(Component.text(max + " mb", NamedTextColor.WHITE))
            );
        }
    }

    private static Component formatTps(double tps) {
        TextColor color = tps > 18.0 ? NamedTextColor.GREEN : tps > 16.0 ? NamedTextColor.YELLOW : NamedTextColor.RED;
        return Component.text(ONE_DECIMAL.get().format(Math.min(tps, 20.0)), color);
    }

    private static Component formatMspt(double mspt) {
        TextColor color = mspt < 25.0 ? NamedTextColor.GREEN : mspt < 50.0 ? NamedTextColor.YELLOW : NamedTextColor.RED;
        return Component.text(ONE_DECIMAL.get().format(mspt), color);
    }
}
