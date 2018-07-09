package net.thenova.survival.server.gui.guis;

import net.thenova.survival.server.command.commands.menu.MenuCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

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
public final class PunishGUI extends BaseListGUI {

    /**
     * Creates the punish GUI.
     */
    public PunishGUI() {
        super(
                ChatColor.RED + "" + ChatColor.BOLD + "Punish",
                new ArrayList<>(),
                new Action("kick the player.",
                        player -> {
                            if(MenuCommand.Util.allowAction(player)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + player);
                            }
                        }),
                new Action("ban the player.",
                        player -> {
                            if(MenuCommand.Util.allowAction(player)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player);
                            }
                        })
        );
    }
}
