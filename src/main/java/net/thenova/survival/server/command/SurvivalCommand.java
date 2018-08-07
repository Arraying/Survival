package net.thenova.survival.server.command;

import de.arraying.nexus.command.NexusCommand;
import de.arraying.nexus.command.NexusCommandContext;
import net.thenova.survival.server.SurvivalRank;
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
public abstract class SurvivalCommand extends NexusCommand {

    private final SurvivalRank rank;

    /**
     * Creates a new survival command.
     * @param name The name.
     * @param rank The rank required.
     * @param aliases The aliases.
     */
    protected SurvivalCommand(String name, SurvivalRank rank, String... aliases) {
        super(name, "", Target.PLAYER, aliases);
        this.rank = rank;
    }

    /**
     * When the command gets executed.
     * @param context The context.
     */
    protected abstract void invoke(NexusCommandContext context);

    /**
     * When the command gets executed.
     * @param context The context.
     */
    @Override
    public final void onCommand(NexusCommandContext context) {
        Player player = (Player) context.getSender();
        SurvivalRank rank = SurvivalRank.forPlayer(player);
        if(!rank.isAtLeast(this.rank)) {
            context.reply("This command requires the rank " + this.rank.getPrefix() + " (you are " + rank.getPrefix() + ").");
            return;
        }
        invoke(context);
    }

}
