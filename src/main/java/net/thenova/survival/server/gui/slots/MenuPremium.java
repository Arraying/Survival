package net.thenova.survival.server.gui.slots;

import de.arraying.nexus.gui.slot.GUIClick;
import de.arraying.nexus.gui.slot.GUISlot;
import de.arraying.nexus.item.ItemBuilder;
import net.thenova.droplets.Core;
import net.thenova.survival.server.ServerCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import static net.thenova.survival.common.SurvivalConstants.*;

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
public final class MenuPremium extends GUISlot {

    /**
     * Creates the premium item.
     */
    public MenuPremium() {
        super(15,
                new ItemBuilder(Material.GOLD_BLOCK)
                    .name(ChatColor.GOLD + "" + ChatColor.BOLD + "Go Premium")
                    .lore(getLore())
                .build(),
                new Click(),
                new Click());
    }

    /**
     * Gets the lore.
     * @return The lore.
     */
    private static String[] getLore() {
        return ServerCore.getInstance().isPremium() ?
                new String[] {
                        "You are already Premium, thank you for your support."
                } :
                new String[] {
                        "Click this to make this server a Premium server.",
                        "",
                        "Below is a comparison between normal",
                        "and " + ChatColor.YELLOW + "Premium" + ChatColor.GRAY + " perks.",
                        "",
                        getFormattedData("Slots", PLAYERS_NORMAL, PLAYERS_PREMIUM),
                        getFormattedData("RAM", RAM_NORMAL, RAM_PREMIUM),
                        getFormattedData("World Radius", WORLD_NORMAL, WORLD_PREMIUM),
                        "",
                        "In order to upgrade you must have the " + ChatColor.GOLD + "Supernova" + ChatColor.GRAY + " rank."
                };
    }

    /**
     * Gets data already formatted.
     * @param title The title.
     * @param normal The normal value.
     * @param premium The Premium value.
     * @return The data.
     */
    private static String getFormattedData(String title, Object normal, Object premium) {
        return ChatColor.GOLD + title + ": " + ChatColor.GRAY + normal + ChatColor.DARK_GRAY + "/" + ChatColor.YELLOW + premium;
    }

    private static final class Click implements GUIClick {

        /**
         * When the slot is clicked.
         * @param player The player.
         */
        @Override
        public void onClick(Player player) {
            player.closeInventory();
            if(ServerCore.getInstance().isPremium()) {
                player.sendMessage(PREFIX + "You are already Premium, you cannot upgrade again.");
                return;
            }
            if(!hasSupernova(player)) {
                player.sendMessage(PREFIX + "You need to be Supernova to do this, which can be purchased at store.thenova.net.");
                return;
            }
            try {
                //noinspection ResultOfMethodCallIgnored
                new File(new File(FILE_META), FILE_PREMIUM).createNewFile();
                player.sendMessage(PREFIX + "Congrats on Premium! Please run " + ChatColor.WHITE + "/survival restart" + ChatColor.GRAY + " to update.");
            } catch(IOException exception) {
                error(player, exception);
            }
        }

        /**
         * Whether or not the player has Supernova.
         * @param player The player.
         * @return The rank.
         */
        private boolean hasSupernova(Player player) {
            String jdbc = Core.INSTANCE.getConfiguration().getJSON().string("jdbc");
            if(jdbc == null) {
                error(player, new Exception("JDBC URL is null"));
                return false;
            }
            try {
                Connection connection = DriverManager.getConnection(jdbc);
                //noinspection SqlNoDataSourceInspection,SqlResolve
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM `memberships` WHERE `member`=? AND `group_id`=?");
                statement.setString(1, player.getUniqueId().toString().replace("-", ""));
                statement.setInt(2, SUPERNOVA);
                ResultSet result = statement.executeQuery();
                boolean status = result.first();
                connection.close();
                return status;
            } catch(SQLException exception) {
                error(player, new Exception("SQL error", exception));
            }
            return false;
        }

        /**
         * When an error occurs.
         * @param player The player to send an error to.
         * @param exception The exception.
         */
        private void error(Player player, Exception exception) {
            exception.printStackTrace();
            player.sendMessage(PREFIX + "Oh no! An error occurred. Please report the following to a developer: " + exception.getMessage() + ".");
        }

    }

}
