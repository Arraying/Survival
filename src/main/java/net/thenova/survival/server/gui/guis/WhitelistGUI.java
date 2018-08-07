package net.thenova.survival.server.gui.guis;

import de.arraying.nexus.gui.slot.GUISlot;
import de.arraying.nexus.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
public final class WhitelistGUI extends BaseListGUI {

    private static final List<GUISlot> staticSlots = new ArrayList<>();

    static {
        staticSlots.add(new GUISlot(21,
                new ItemBuilder(Material.EMERALD)
                        .name(ChatColor.GREEN + "" + ChatColor.BOLD + "On")
                        .lore("Click to turn on the whitelist.")
                        .build(),
                player -> whitelist(true, player)
        ));
        staticSlots.add(new GUISlot(22,
                new ItemBuilder(Material.PAPER)
                        .name(ChatColor.WHITE + "" + ChatColor.BOLD + "All Commands")
                        .lore("/whitelist on",
                                "/whitelist off",
                                "/whitelist add <player>",
                                "/whitelist remove <player>")
                        .build()
        ));
        staticSlots.add(new GUISlot(23,
                new ItemBuilder(Material.REDSTONE_BLOCK)
                        .name(ChatColor.RED + "" + ChatColor.BOLD + "Off")
                        .lore("Click to turn off the whitelist.")
                        .build(),
                player -> whitelist(false, player)
        ));
    }

    /**
     * Creates the whitelist GUI.
     */
    public WhitelistGUI() {
        super(
                ChatColor.WHITE + "" + ChatColor.BOLD + "Whitelist",
                staticSlots,
                new Action("whitelist the player.",
                        player -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + player)),
                new Action("unwhitelist the player.",
                        player -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + player))
        );
    }

    /**
     * Changes the whitelist. Uses a command so OPs are notified.
     * @param on True to turn it on, false to turn it off.
     * @param player The player, to close the GUI.
     */
    private static void whitelist(boolean on, Player player) {
        player.closeInventory();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist " + (on ? "on" : "off"));
    }

}
