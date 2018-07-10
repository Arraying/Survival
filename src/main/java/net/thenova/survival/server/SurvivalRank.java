package net.thenova.survival.server;

import org.bukkit.ChatColor;
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
public enum SurvivalRank {

    /**
     * A player.
     */
    PLAYER("Player", ChatColor.DARK_GRAY, 1),

    /**
     * An opped player.
     */
    CO_HOST("Co-Host", ChatColor.GRAY, 2),

    /**
     * The owner.
     */
    HOST("Host", ChatColor.WHITE, 3);

    private final String prefix;
    private final ChatColor colour;
    private final int hierarchy;

    /**
     * Creates a new survival rank.
     * @param prefix The prefix of the rank.
     * @param colour The colour.
     * @param hierarchy The hierarchy of the rank.
     */
    SurvivalRank(String prefix, ChatColor colour, int hierarchy) {
        this.prefix = prefix;
        this.colour = colour;
        this.hierarchy = hierarchy;
    }

    /**
     * Gets the survival rank for the player.
     * @param player The player.
     * @return The rank.
     */
    public static SurvivalRank forPlayer(Player player) {
        ServerCore core = ServerCore.getInstance();
        SurvivalRank rank;
        if(player.isOp()) {
            if(core.getOwner() != null
                    && core.getOwner().equals(player.getUniqueId())) {
                rank = SurvivalRank.HOST;
            } else {
                rank = SurvivalRank.CO_HOST;
            }
        } else {
            rank = SurvivalRank.PLAYER;
        }
        return rank;
    }

    /**
     * Gets the prefix of the rank.
     * @return The prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Gets the colour of the rank.
     * @return The colour.
     */
    public ChatColor getColour() {
        return colour;
    }

    /**
     * Whether or not the survival rank is at least another survival rank.
     * @param rank The rank.
     * @return True if it is, false otherwise.
     */
    public boolean isAtLeast(SurvivalRank rank) {
        return this.hierarchy >= rank.hierarchy;
    }

}
