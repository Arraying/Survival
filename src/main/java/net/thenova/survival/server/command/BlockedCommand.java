package net.thenova.survival.server.command;

import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright 2018 Arraying
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@SuppressWarnings("SpellCheckingInspection")
public final class BlockedCommand {

    private static final Set<BlockedCommand> commands = new HashSet<>();

    static {
        commands.add(new BlockedCommand("stop", new String[]{"minecraft:stop", "bukkit:stop"},
                ChatColor.GRAY + "Please use " + ChatColor.WHITE + "/survival delete" + ChatColor.GRAY + " instead."));
        commands.add(new BlockedCommand("restart", new String[]{"minecraft:restart", "bukkit:restart"},
                ChatColor.GRAY + "Please use " + ChatColor.WHITE + "/survival restart" + ChatColor.GRAY + " instead."));
        commands.add(new BlockedCommand("reload", new String[]{"reload", "minecraft:reload", "bukkit:reload"}, null));
    }

    private final String name;
    private final String[] aliases;
    private final String message;

    /**
     * Creates a blocked command.
     * @param name The name of the command.
     * @param aliases The aliases.
     * @param message The message, can be null.
     */
    private BlockedCommand(String name, String[] aliases, String message) {
        this.name = name;
        this.aliases = aliases;
        this.message = message;
    }

    /**
     * Gets all blocked commands.
     * @return A set of blocked commands.
     */
    public static Set<BlockedCommand> getCommands() {
        return commands;
    }

    /**
     * Gets the name of the command.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the aliases of the command.
     * @return The aliases.
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Gets the message of the command.
     * @return The message.
     */
    public String getMessage() {
        return message == null ? ChatColor.GRAY + "You are not allowed to do this." : message;
    }

}
