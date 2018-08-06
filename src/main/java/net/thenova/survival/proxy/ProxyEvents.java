package net.thenova.survival.proxy;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.event.EventHandler;
import net.thenova.droplets.Core;
import net.thenova.droplets.droplet.Droplet;
import net.thenova.droplets.droplet.DropletHandler;
import net.thenova.droplets.proxy.event.DropletAvailableEvent;
import net.thenova.droplets.proxy.event.DropletUnavailableEvent;
import net.thenova.survival.proxy.survival.SurvivalHandler;
import net.thenova.survival.proxy.survival.SurvivalServer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
public final class ProxyEvents implements Listener {

    private final List<Execution> tasks = new ArrayList<>();

    /**
     * When a player joins the network.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        SurvivalServer server = SurvivalHandler.INSTANCE.getServer(player);
        if(server != null
                && server.getUUID().equals(player.getUniqueId())
                && !server.getName().equals(player.getName())) {
            server.setName(player.getName());
        }
    }

    /**
     * When a player switches server.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public synchronized void onSwitch(ServerSwitchEvent event) {
        Droplet droplet = DropletHandler.INSTANCE.getDroplet(event.getPlayer().getServer().getInfo().getName());
        if(droplet != null
                && droplet.getTemplate().equals(SurvivalServer.TEMPLATE)) {
            Title title = ProxyServer.getInstance().createTitle();
            title.title(TextComponent.fromLegacyText(ChatColor.WHITE + "" + ChatColor.BOLD + "Survival"));
            title.subTitle(TextComponent.fromLegacyText(ChatColor.GRAY + "A private vanilla experience."));
            title.send(event.getPlayer());
            SurvivalServer survivalServer = SurvivalHandler.INSTANCE.getServer(droplet.getIdentifier());
            tasks.stream()
                    .filter(task -> task.uuid.equals(survivalServer.getUUID()))
                    .findAny()
                    .ifPresent(task -> {
                        task.task.cancel();
                        tasks.remove(task);
                    });
        }
        for(SurvivalServer server : SurvivalHandler.INSTANCE.all()) {
            ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(server.getIdentifier());
            if(serverInfo == null
                    || serverInfo.getPlayers().isEmpty()) {
                ScheduledTask scheduledTask = ProxyServer.getInstance().getScheduler().schedule(Core.INSTANCE.getProxyCore(), () -> {
                    if(serverInfo != null
                            && serverInfo.getPlayers().isEmpty()) {
                        SurvivalHandler.INSTANCE.delete(server, true);
                    }
                }, 1, TimeUnit.MINUTES);
                Execution execution = new Execution(server.getUUID(), scheduledTask);
                tasks.add(execution);
            }
        }
    }

    /**
     * When a droplet becomes available to the proxy.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onDropletCreate(DropletAvailableEvent event) {
        Droplet droplet = event.getDroplet();
        if(!droplet.getTemplate().equals(SurvivalServer.TEMPLATE)) {
            return;
        }
        if(event.isQuery()) {
            droplet.delete();
        } else {
            SurvivalHandler.INSTANCE.available(droplet);
        }
    }

    /**
     * When a droplet becomes unavailable to the proxy.
     * @param event The event.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onDropletDelete(DropletUnavailableEvent event) {
        Droplet droplet = event.getDroplet();
        if(!droplet.getTemplate().equals(SurvivalServer.TEMPLATE)) {
            return;
        }
        UUID uuid;
        try {
            uuid = UUID.fromString(droplet.getData());
        } catch(IllegalArgumentException ignored) {
            return;
        }
        SurvivalServer server = SurvivalHandler.INSTANCE.getServer(uuid);
        if(server != null) {
            SurvivalHandler.INSTANCE.delete(server, false);
        }
    }

    /**
     * An execution wrapper.
     */
    private static final class Execution {

        private final UUID uuid;
        private final ScheduledTask task;

        /**
         * Creates a new execution.
         * @param uuid The UUID.
         * @param task The underlying task.
         */
        private Execution(UUID uuid, ScheduledTask task) {
            this.uuid = uuid;
            this.task = task;
        }
    }

}
