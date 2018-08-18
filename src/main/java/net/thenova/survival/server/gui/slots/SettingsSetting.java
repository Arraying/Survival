package net.thenova.survival.server.gui.slots;

import de.arraying.kotys.JSON;
import de.arraying.nexus.gui.slot.GUIClick;
import de.arraying.nexus.gui.slot.GUISlot;
import de.arraying.nexus.item.ItemBuilder;
import net.thenova.survival.common.SurvivalConstants;
import net.thenova.survival.server.ServerCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
public final class SettingsSetting extends GUISlot {

    /**
     * Creates a new setting.
     * @param slot The slot.
     * @param material The material.
     * @param title The item title.
     * @param setting The setting name in server.properties.
     * @param left The left click value.
     * @param right The right click value.
     * @param premium If the option is premium only.
     */
    public SettingsSetting(int slot, Material material, String title, String setting, Object left, Object right, boolean premium) {
        super(slot,
                new ItemBuilder(material)
                        .name(ChatColor.WHITE + "" + ChatColor.BOLD + title)
                        .lore("Left click to set to " + ChatColor.WHITE + left,
                                "Right click to set to " + ChatColor.WHITE + right,
                                premium ? ChatColor.GOLD + "Premium only setting." : "")
                        .build(),
                new Click(setting, left, premium),
                new Click(setting, right, premium)
        );
    }

    /**
     * The click implementation.
     */
    private static final class Click implements GUIClick {

        private final String setting;
        private final Object value;
        private final boolean premium;

        /**
         * Creates a new click.
         * @param setting The setting name in server.properties.
         * @param value The value.
         * @param premium If the option is premium only.
         */
        private Click(String setting, Object value, boolean premium) {
            this.setting = setting;
            this.value = value;
            this.premium = premium;
        }

        /**
         * When a setting is clicked.
         * @param player The player.
         */
        @Override
        public void onClick(Player player) {
            if(premium
                    && !ServerCore.getInstance().isPremium()) {
                player.closeInventory();
                player.sendMessage(SurvivalConstants.PREFIX + "This setting is Premium only, and this server is not Premium. To learn more about Premium, do "
                        + ChatColor.WHITE + "/menu" + ChatColor.GRAY + ".");
                return;
            }
            update();
            player.closeInventory();
            player.sendMessage(SurvivalConstants.PREFIX + "The setting has been updated. It will become effective at the next restart." +
                    "This can be done using " + ChatColor.WHITE + "/survival restart" + ChatColor.GRAY + ".");
        }

        /**
         * Updates the value.
         */
        private void update() {
            try {
                File file = new File(SurvivalConstants.FILE_PROPS);
                JSON json = new JSON(file);
                json.put(setting, value.toString());
                Files.write(file.toPath(), json.marshal().getBytes());
            } catch(IOException exception) {
                exception.printStackTrace();
            }
        }

    }

}
