package net.thenova.survival.proxy.survival;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thenova.droplets.droplet.Droplet;
import net.thenova.droplets.droplet.DropletCreationData;
import net.thenova.droplets.droplet.DropletHandler;
import net.thenova.survival.common.SurvivalConstants;

import java.util.HashSet;
import java.util.Set;
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
public enum SurvivalHandler {

    /**
     * The survival handler's instance.
     */
    INSTANCE;

    private final Set<SurvivalServer> servers = new HashSet<>();
    private final Set<ProxiedPlayer> creation = new HashSet<>();

    /**
     * Gets the survival server size.
     * @return The size.
     */
    public synchronized int getServerSize() {
        return servers.size();
    }

    /**
     * Gets the survival server from a UUID.
     * @param uuid The UUID.
     * @return The server, could be null.
     */
    public synchronized SurvivalServer getServer(UUID uuid) {
        if(uuid == null) {
            return null;
        }
        return servers
                .stream()
                .filter(server -> server.getUUID().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the survival server of a player.
     * @param player The player.
     * @return The server, could be null.
     */
    public synchronized SurvivalServer getServer(ProxiedPlayer player) {
        if(player == null) {
            return null;
        }
        return getServer(player.getUniqueId());
    }

    /**
     * Gets the survival server from an identifier.
     * @param identifier The identifier.
     * @return The server, could be null.
     */
    public synchronized SurvivalServer getServer(String identifier) {
        return all()
                .stream()
                .filter(server -> server.getIdentifier().equals(identifier))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the server for a player's name.
     * @param name The name.
     * @return The server, or null.
     */
    public ServerInfo getServerForPlayerName(String name) {
        SurvivalServer server = getServer(ProxyServer.getInstance().getPlayer(name));
        if(server == null) {
            return null;
        }
        return ProxyServer.getInstance().getServerInfo(server.getIdentifier());
    }

    /**
     * Gets all servers.
     * @return A set.
     */
    public synchronized Set<SurvivalServer> all() {
        return servers;
    }

    /**
     * Creates a survival server for the player.
     * @param player The player.
     * @return True if it has been created, false if they do not own one.
     */
    public synchronized boolean create(ProxiedPlayer player) {
        if(creation.contains(player)) {
            return false;
        }
        creation.add(player);
        DropletHandler.INSTANCE.create(SurvivalServer.TEMPLATE,
                new DropletCreationData(null, player.getUniqueId().toString()));
        return true;
    }

    /**
     * Deletes the server.
     * @param server The server.
     * @param dropletDelete Whether the droplet should be deleted too.
     */
    public synchronized void delete(SurvivalServer server, boolean dropletDelete) {
        servers.remove(server);
        if(dropletDelete) {
            DropletHandler.INSTANCE.getDroplet(server.getIdentifier()).delete();
        }
    }

    /**
     * Removes a player from the creation.
     * @param player The player.
     */
    public synchronized void disconnect(ProxiedPlayer player) {
        creation.remove(player);
    }

    /**
     * When a droplet is available.
     * @param droplet The droplet.
     */
    public synchronized void available(Droplet droplet) {
        ProxyServer proxy = ProxyServer.getInstance();
        ProxiedPlayer player = proxy.getPlayer(UUID.fromString(droplet.getData()));
        if(player == null
                || !creation.contains(player)) {
            droplet.delete();
            return;
        }
        player.sendMessage(TextComponent.fromLegacyText(SurvivalConstants.PREFIX + "Your survival server is available, do " +
                ChatColor.WHITE + "/survival join " + player.getName() + ChatColor.GRAY + "."));
        SurvivalServer server = new SurvivalServer(droplet.getIdentifier(), player.getUniqueId(), player.getName());
        servers.add(server);
        creation.remove(player);
    }

}
