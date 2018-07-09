package net.thenova.survival.server.command.commands.opme;

import de.arraying.nexus.command.NexusCommandContext;
import net.thenova.survival.server.SurvivalRank;
import net.thenova.survival.server.command.SurvivalCommand;

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
public final class OpMeCommand extends SurvivalCommand {

    /**
     * Creates the command.
     */
    public OpMeCommand() {
        super("opme", SurvivalRank.HOST);
    }

    /**
     * Invokes the command.
     * @param context The context.
     */
    @Override
    protected void invoke(NexusCommandContext context) {
        context.getSender().setOp(true);
        context.reply("You have been opped.");
    }

}
