package net.thenova.survival.server.gui.guis;

import de.arraying.nexus.gui.GUI;
import net.thenova.survival.server.gui.slots.MenuOp;
import net.thenova.survival.server.gui.slots.MenuPunish;
import net.thenova.survival.server.gui.slots.MenuWhitelist;
import org.bukkit.ChatColor;

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
public final class MenuGUI extends GUI {

    /**
     * Creates the menu GUI.
     */
    public MenuGUI() {
        super(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Survival Menu", 27);
    }

    /**
     * Populates the GUI.
     */
    @Override
    public void populate() {
        registerSlot(new MenuWhitelist());
        registerSlot(new MenuPunish());
        registerSlot(new MenuOp());
    }

}
