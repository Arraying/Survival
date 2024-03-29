package net.thenova.survival.server;

import de.arraying.kotys.JSONArray;
import de.arraying.nexus.Nexus;
import de.arraying.nexus.NexusConfiguration;
import de.arraying.nexus.command.NexusCommandLocalization;
import net.thenova.droplets.Core;
import net.thenova.survival.common.SurvivalConstants;
import net.thenova.survival.server.command.commands.menu.MenuCommand;
import net.thenova.survival.server.command.commands.opme.OpMeCommand;
import net.thenova.survival.server.entity.InstallablePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
@SuppressWarnings("unused")
public final class ServerCore extends Nexus {

    private static ServerCore instance;
    private UUID owner;
    private List<InstallablePlugin> installablePlugins;

    /**
     * Gets the instance.
     * @return The instance.
     */
    public static ServerCore getInstance() {
        return instance;
    }

    /**
     * Configures Nexus.
     * @param config The configuration.
     */
    @Override
    public void configure(NexusConfiguration config) {
        instance = this;
        config.setLocale(new NexusCommandLocalization(
                SurvivalConstants.PREFIX,
                ChatColor.GRAY + "You must be a player to execute this command.",
                ChatColor.GRAY + "You must be the console to execute this command.",
                ChatColor.GRAY + "You do not have permission for this command.",
                ChatColor.GRAY+ "That sub-command does not exist."
        ));
        config.getCommands().add(new MenuCommand());
        config.getCommands().add(new OpMeCommand());
        config.getListeners().add(new ServerEvents());
    }

    /**
     * When the plugin enables.
     */
    @Override
    public void onStartup() {
        Core.INSTANCE.getServerCore().registerFinalizer(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all"));
        String uuidRaw = Core.INSTANCE.getConfiguration().getJSON().string(SurvivalConstants.UUID);
        if(uuidRaw != null) {
            Core.INSTANCE.getLogger().info("Using owner UUID " + uuidRaw + ".");
            owner = UUID.fromString(uuidRaw);
        }
        installablePlugins = new ArrayList<>();
        JSONArray plugins = Core.INSTANCE.getConfiguration().getJSON().array("plugins");
        if(plugins != null) {
            try {
                InstallablePlugin[] installablePluginArray = plugins.marshal(InstallablePlugin.class);
                for(InstallablePlugin plugin : installablePluginArray) {
                    if(plugin.isValid()) {
                        installablePlugins.add(plugin);
                    } else {
                        getLogger().warning("Invalid plugin " + plugin + ".");
                    }
                }
            } catch(IllegalArgumentException | IllegalStateException exception) {
                exception.printStackTrace();
            }
        } else {
            getLogger().severe("Plugins array nonexistant.");
        }
    }

    /**
     * Whether or not the survival server is premium.
     * @return True if it is, false otherwise.
     */
    public boolean isPremium() {
        return new File(new File(SurvivalConstants.FILE_META), SurvivalConstants.FILE_PREMIUM).exists();
    }

    /**
     * Gets all installable plugins.
     * @return A list of plugins.
     */
    public List<InstallablePlugin> getInstallablePlugins() {
        return installablePlugins;
    }

    /**
     * Gets the owner of the survival server.
     * @return The owner.
     */
    UUID getOwner() {
        return owner;
    }

}
