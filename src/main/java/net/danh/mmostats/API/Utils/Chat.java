package net.danh.mmostats.API.Utils;

import net.danh.mmostats.MMOStats;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Chat {

    public static void sendCommandSenderMessage(CommandSender c, String... msg) {
        Objects.requireNonNull(msg, Arrays.toString(msg) + " is null");
        if (c instanceof Player) {
            sendPlayerMessage((Player) c, msg);
        }
        if (c instanceof ConsoleCommandSender) {
            sendConsoleMessage((ConsoleCommandSender) c, msg);
        }
    }

    public static void sendCommandSenderMessage(CommandSender c, List<String> msg) {
        Objects.requireNonNull(msg, msg.toString() + " is null");
        if (c instanceof Player) {
            sendPlayerMessage((Player) c, msg);
        }
        if (c instanceof ConsoleCommandSender) {
            sendConsoleMessage((ConsoleCommandSender) c, msg);
        }
    }

    /**
     * @param p   Player
     * @param msg Message... send to player
     */
    public static void sendPlayerMessage(Player p, String... msg) {
        Objects.requireNonNull(msg, Arrays.toString(msg) + " is null");
        for (String string : msg) {
            p.sendMessage(Chat.colorize(string));
        }
    }

    /**
     * @param p   Player
     * @param msg List Message send to player
     */
    public static void sendPlayerMessage(Player p, List<String> msg) {
        Objects.requireNonNull(msg, msg.toString() + " is null");
        for (String string : msg) {
            p.sendMessage(Chat.colorize(string));
        }
    }

    /**
     * @param c   ConsoleCommandSender
     * @param msg Message... send to console without [DCore]
     */
    public static void sendConsoleMessage(ConsoleCommandSender c, String... msg) {
        Objects.requireNonNull(msg, Arrays.toString(msg) + " is null");
        for (String string : msg) {
            c.sendMessage(Chat.colorize("[" + MMOStats.getInstance().getName() + "] " + string));
        }
    }

    /**
     * @param c   ConsoleCommandSender
     * @param msg List Message send to console without [DCore]
     */
    public static void sendConsoleMessage(ConsoleCommandSender c, List<String> msg) {
        Objects.requireNonNull(msg, msg.toString() + " is null");
        for (String string : msg) {
            c.sendMessage(Chat.colorize("[" + MMOStats.getInstance().getName() + "] " + string));
        }
    }

    /**
     * @param p    Player
     * @param type CHAT, ACTION_BAR
     * @param msg  Message send to player
     */
    public static void sendPlayerMessageType(Player p, ChatMessageType type, String msg) {
        Objects.requireNonNull(msg, msg + " is null");
        p.spigot().sendMessage(type, new TranslatableComponent(Chat.colorize(msg)));
    }


    public static String colorize(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colorize(String... message) {
        return Arrays.stream(message).map(Chat::colorize).collect(Collectors.toList());
    }

    public static List<String> colorize(List<String> message) {
        return message.stream().map(Chat::colorize).collect(Collectors.toList());
    }
}

