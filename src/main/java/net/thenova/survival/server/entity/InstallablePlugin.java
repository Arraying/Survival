package net.thenova.survival.server.entity;

import de.arraying.kotys.JSONField;
import de.arraying.nexus.gui.slot.GUIClick;
import de.arraying.nexus.gui.slot.GUISlot;
import de.arraying.nexus.item.ItemBuilder;
import net.thenova.survival.common.SurvivalConstants;
import net.thenova.survival.server.ServerCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
@SuppressWarnings("ResultOfMethodCallIgnored")
public final class InstallablePlugin {

    @JSONField(key = "name") private String name;
    @JSONField(key = "description") private String description;
    @JSONField(key = "dependencies") private String[] dependencies;

    /**
     * Whether or not the plugin is valid.
     * @return The plugin name.
     */
    public boolean isValid() {
        return name != null && !name.isEmpty()
                && description != null && !description.isEmpty()
                && dependencies != null;
    }

    /**
     * Gets the GUI slot.
     * @return The slot.
     */
    public GUISlot getSlot() {
        return new GUISlot(-1, new ItemBuilder(Material.BOOK_AND_QUILL)
                .name(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + name)
                .lore(description,
                        "",
                        "Left-click to install",
                        "Right-click to uninstall")
                .build(),
                new Click(this, true),
                new Click(this, false));
    }

    /**
     * Pretty prints the plugin.
     * @return The string.
     */
    @Override
    public String toString() {
        return name + "<" + description + ">";
    }

    /**
     * Whether or not the plugin is installed.
     * @return True if it is, false otherwise.
     */
    private boolean isInstalled() {
        return getMetaEntry(name).exists();
    }

    /**
     * Installs the plugin.
     */
    private void install() {
        try {
            getMetaEntry(name).createNewFile();
            for(String dependency : dependencies) {
                getMetaEntry(dependency).createNewFile();
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Uninstalls the plugin.
     */
    private void uninstall() {
        getMetaEntry(name).delete();
        outer:for(String dependency : dependencies) {
            for(InstallablePlugin plugin : ServerCore.getInstance().getInstallablePlugins()) {
                if(!plugin.isInstalled()) {
                    continue;
                }
                if(Arrays.stream(plugin.dependencies).anyMatch(it -> it.equals(dependency))) {
                    continue outer;
                }
            }
            getMetaEntry(dependency).delete();
        }
    }

    /**
     * Gets the meta entry file.
     * @param name The plugin name.
     * @return The file, may or may not exist.
     */
    private File getMetaEntry(String name) {
        return new File(new File(SurvivalConstants.FILE_META), "plugin_" + name);
    }

    private static final class Click implements GUIClick {

        private final InstallablePlugin plugin;
        private final boolean install;

        /**
         * Creates a new GUI click.
         * @param plugin The plugin.
         * @param install Whether or not to install the plugin.
         */
        private Click(InstallablePlugin plugin, boolean install) {
            this.plugin = plugin;
            this.install = install;
        }

        /**
         * When the plugin is clicked.
         * @param player The player.
         */
        @Override
        public void onClick(Player player) {
            if(install) {
                if(!plugin.isInstalled()) {
                    plugin.install();
                }
            } else {
                if(plugin.isInstalled()) {
                    plugin.uninstall();
                }
            }
            player.closeInventory();
            player.sendMessage(SurvivalConstants.PREFIX + "The plugin has been " + (install ? "" : "un") + "installed. It will be effective after the next restart.");
        }

    }

}
