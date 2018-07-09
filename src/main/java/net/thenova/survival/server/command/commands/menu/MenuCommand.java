package net.thenova.survival.server.command.commands.menu;

import de.arraying.nexus.command.NexusCommandContext;
import de.arraying.nexus.gui.GUIManager;
import net.thenova.survival.common.SurvivalConstants;
import net.thenova.survival.server.SurvivalRank;
import net.thenova.survival.server.command.SurvivalCommand;
import net.thenova.survival.server.gui.guis.MenuGUI;
import org.bukkit.Bukkit;
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
public final class MenuCommand extends SurvivalCommand {

    /**
     * Creates a new survival command.
     */
    public MenuCommand() {
        super("menu", SurvivalRank.HOST, "m");
    }

    /**
     * When the command is executed.
     * @param context The context.
     */
    @Override
    protected void invoke(NexusCommandContext context) {
        GUIManager.INSTANCE.openGUI((Player) context.getSender(), new MenuGUI());
    }

    /**
     * The util class.
     */
    public static final class Util {

        /**
         * Whether or not an action is allowed. If not, it will return an error.
         * @param name The name of the player the action is being invoked on.
         * @return True if the action may continue, false otherwise.
         */
        public static boolean allowAction(String name) {
            Player player = Bukkit.getPlayer(name);
            if(player == null) {
                return true;
            }
            SurvivalRank rank = SurvivalRank.forPlayer(player);
            if(rank == SurvivalRank.HOST) {
                player.sendMessage(SurvivalConstants.PREFIX + "You cannot do this to yourself.");
                return false;
            }
            return true;
        }

    }

}
