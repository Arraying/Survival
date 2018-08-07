package net.thenova.survival.server.gui.guis;

import de.arraying.nexus.gui.impl.PaginatedGUI;
import de.arraying.nexus.gui.slot.GUIClick;
import de.arraying.nexus.gui.slot.GUISlot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
abstract class BaseListGUI extends PaginatedGUI {

    /**
     * Creates a base GUI.
     * @param name The name.
     * @param left The left action.
     * @param right The right action.
     */
    BaseListGUI(String name, List<? extends GUISlot> staticSlots, Action left, Action right) {
        super(name, 27, staticSlots, getSlots(left, right), new Sorter());
    }

    /**
     * Gets the slots for all the players.
     * @param left The left click.
     * @param right The right click.
     * @return The slots.
     */
    private static List<GUISlot> getSlots(Action left, Action right) {
        List<GUISlot> list = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            String name = player.getName();
            ClickAction leftClickAction = new ClickAction(left, name);
            ClickAction rightClickAction = new ClickAction(right, name);
            list.add(new GUISlot(0, getSkull(name, left.description, right.description), leftClickAction, rightClickAction));
        }
        return list;
    }

    /**
     * Gets the skull of a player, Nexus doesn't support this.
     * @param name The name.
     * @param left The left click action description.
     * @param right The right click action description.
     * @return The item.
     */
    private static ItemStack getSkull(String name, String left, String right) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(name);
        meta.setDisplayName(ChatColor.AQUA + name);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Left click to " + left);
        lore.add(ChatColor.GRAY + "Right click to " + right);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * A click action implementation.
     */
    static class Action {

        private final String description;
        private final Consumer<String> handle;

        /**
         * Creates a new click action.
         * @param description The description.
         * @param handle The handler.
         */
        Action(String description, Consumer<String> handle) {
            this.description = description;
            this.handle = handle;
        }

    }

    /**
     * A click action.
     */
    private static final class ClickAction implements GUIClick {

        private final Action action;
        private final String name;

        /**
         * Creates a click action.
         * @param action The action.
         * @param name The name.
         */
        private ClickAction(Action action, String name) {
            this.action = action;
            this.name = name;
        }

        /**
         * When the slot is clicked.
         * @param player The player.
         */
        @Override
        public void onClick(Player player) {
            player.closeInventory();
            action.handle.accept(name);
        }

    }

    /**
     * The sorting algorithm.
     */
    private static final class Sorter implements SortingAlgorithm {

        private final List<Integer> slots = new ArrayList<>();

        /**
         * Adds the slots.
         */
        private Sorter() {
            for(int i = 11; i <= 15; i++) {
                slots.add(i);
            }
        }

        /**
         * Gets all slots.
         * @return A list of slots.
         */
        @NotNull
        @Override
        public List<Integer> getSlots() {
            return slots;
        }

    }

}
