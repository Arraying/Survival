package net.thenova.survival.server.gui.guis;

import de.arraying.nexus.gui.impl.PaginatedGUI;
import de.arraying.nexus.gui.slot.GUISlot;
import net.thenova.survival.server.ServerCore;
import net.thenova.survival.server.entity.InstallablePlugin;
import net.thenova.survival.server.gui.slots.MenuPane;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public final class PluginGUI extends PaginatedGUI {

    /**
     * Creates a plugin GUI.
     */
    public PluginGUI() {
        super(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Plugins",
                27,
                getStatic(),
                ServerCore.getInstance().getInstallablePlugins().stream()
                        .map(InstallablePlugin::getSlot)
                        .collect(Collectors.toList()),
                new Sorter());
    }

    /**
     * Gets all static slots.
     * @return A list of slots.
     */
    private static List<GUISlot> getStatic() {
        List<GUISlot> slots = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            slots.add(new MenuPane(i));
        }
        for(int i = 19; i < 26; i++) {
            slots.add(new MenuPane(i));
        }
        return slots;
    }

    /**
     * The sorting algorithm implementation.
     */
    private static final class Sorter implements SortingAlgorithm {

        /**
         * Gets the slots.
         * @return A list of slots.
         */
        @NotNull
        @Override
        public List<Integer> getSlots() {
            List<Integer> slots = new ArrayList<>();
            for(int i = 9; i < 17; i ++) {
                slots.add(i);
            }
            return slots;
        }

    }

}
