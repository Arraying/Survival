package net.thenova.survival.server.gui.slots;

import de.arraying.nexus.gui.GUIManager;
import de.arraying.nexus.gui.slot.GUIClick;
import de.arraying.nexus.gui.slot.GUISlot;
import de.arraying.nexus.item.ItemBuilder;
import net.thenova.survival.server.gui.guis.WhitelistGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
public final class MenuWhitelist extends GUISlot {

    /**
     * Creates the whitelist item.
     */
    public MenuWhitelist() {
        super(11,
                new ItemBuilder(Material.PAPER)
                    .name(ChatColor.WHITE + "" + ChatColor.BOLD + "Whitelist")
                    .lore("Click to manage the whitelist.")
                    .build(),
                new Click());
    }

    /**
     * The click implementation.
     */
    public static final class Click implements GUIClick {

        /**
         * When a player clicks the item.
         * @param player The player.
         */
        @Override
        public void onClick(Player player) {
            GUIManager.INSTANCE.openGUI(player, new WhitelistGUI());
        }

    }

}
