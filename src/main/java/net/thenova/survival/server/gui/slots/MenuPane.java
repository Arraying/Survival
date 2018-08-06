package net.thenova.survival.server.gui.slots;

import de.arraying.nexus.gui.slot.GUISlot;
import de.arraying.nexus.item.ItemBuilder;
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
public final class MenuPane extends GUISlot  {

    /**
     * Creates the pane item.
     * @param slot The slot.
     */
    public MenuPane(int slot) {
        super(slot, new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .name(ChatColor.BOLD + " ")
                    .build());
    }

}
