package net.thenova.survival.server.gui.guis;

import de.arraying.nexus.gui.GUI;
import net.thenova.survival.server.gui.slots.MenuPane;
import net.thenova.survival.server.gui.slots.SettingsSetting;
import org.bukkit.ChatColor;
import org.bukkit.Material;

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
public final class SettingsGUI extends GUI {

    /**
     * The slot number.
     */
    private static final int[] SLOTS = new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26
    };

    /**
     * Creates the settings GUI.
     */
    public SettingsGUI() {
        super(ChatColor.AQUA + "" + ChatColor.BOLD + "Settings", 27);
    }

    /**
     * Populates the GUI.
     */
    @Override
    public void populate() {
        for(int slot : SLOTS) {
            registerSlot(new MenuPane(slot));
        }
        registerSlot(new SettingsSetting(
                9,
                Material.BEACON,
                "Spawn Protection",
                "spawn-protection",
                0,
                64,
                false
        ));
        registerSlot(new SettingsSetting(
                10,
                Material.FEATHER,
                "Gamemode",
                "gamemode",
                0,
                1,
                false
        ));
        registerSlot(new SettingsSetting(
                11,
                Material.ROTTEN_FLESH,
                "Spawn Monsters",
                "spawn-monsters",
                true,
                false,
                false
        ));
        registerSlot(new SettingsSetting(
                12,
                Material.COOKED_CHICKEN,
                "Spawn Animals",
                "spawn-animals",
                true,
                false,
                false
        ));
        registerSlot(new SettingsSetting(
                13,
                Material.DIAMOND_SWORD,
                "Enable PvP",
                "pvp",
                true,
                false,
                false
        ));
        registerSlot(new SettingsSetting(
                14,
                Material.PAPER,
                "Max Players",
                "max-players",
                5,
                15,
                true
        ));
        registerSlot(new SettingsSetting(
                15,
                Material.GRASS,
                "World Radius",
                "max-world-size",
                10000,
                15000,
                true
        ));
        registerSlot(new SettingsSetting(
                16,
                Material.EMERALD,
                "Spawn NPCs",
                "spawn-npcs",
                true,
                false,
                false
        ));
        registerSlot(new SettingsSetting(
                17,
                Material.MOB_SPAWNER,
                "Generate Structures",
                "generate-structures",
                true,
                false,
                false
        ));
    }

}
