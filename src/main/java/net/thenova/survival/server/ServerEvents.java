package net.thenova.survival.server;

import net.thenova.survival.server.command.BlockedCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;
import java.util.UUID;

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
@SuppressWarnings("WeakerAccess")
public final class ServerEvents implements Listener {

    private final ServerCore core;

    /**
     * Sets the core.
     */
    ServerEvents() {
        this.core = ServerCore.getInstance();
    }

    /**
     * When a player executes a command.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String filter = getFilterMessage(event.getMessage());
        if(filter != null) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(filter);
        }
    }

    /**
     * When the console executes a command.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        if(event instanceof BlockCommandSender) {
            String filter = getFilterMessage(event.getCommand());
            if(filter != null) {
                event.setCommand("command-not-allowed");
                event.setCancelled(true);
                event.getSender().sendMessage(filter);
            }
        }
    }

    /**
     * When a player talks in chat.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        SurvivalRank rank = SurvivalRank.forPlayer(event.getPlayer());
        event.setFormat(rank.getColour() + rank.getPrefix() + ChatColor.DARK_GRAY + " | " +
                rank.getColour() + "%s" + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "%s");
    }

    /**
     * When a player joins the server.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if(core.getOwner() != null
                && uuid.equals(core.getOwner())) {
            event.getPlayer().setOp(true);
        }
    }

    /**
     * Calculates whether the command needs to be filtered.
     * @param raw The raw command.
     * @return Null if it does not, otherwise the filter message.
     */
    private String getFilterMessage(String raw) {
        String commandRaw = raw.toLowerCase().substring(1);
        int index = commandRaw.contains(" ") ? commandRaw.indexOf(" ") : commandRaw.length();
        String command = commandRaw.substring(0, index);
        for(BlockedCommand blockedCommand : BlockedCommand.getCommands()) {
            if(blockedCommand.getName().equals(command)
                    || Arrays.stream(blockedCommand.getAliases()).anyMatch(alias -> alias.equals(command))) {
                return blockedCommand.getMessage();
            }
        }
        return null;
    }

}
