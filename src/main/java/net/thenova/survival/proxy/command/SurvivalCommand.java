package net.thenova.survival.proxy.command;

import net.md_5.bungee.api.ChatColor;
import net.thenova.droplets.proxy.command.internal.AbstractCommand;
import net.thenova.droplets.proxy.command.internal.Context;
import net.thenova.droplets.proxy.command.internal.responses.ListedResponse;
import net.thenova.survival.proxy.command.sub.CreateCommand;
import net.thenova.survival.proxy.command.sub.DeleteCommand;
import net.thenova.survival.proxy.command.sub.InfoCommand;
import net.thenova.survival.proxy.command.sub.JoinCommand;

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
public final class SurvivalCommand extends AbstractCommand {

    /**
     * Creates a new survival command.
     */
    public SurvivalCommand() {
        super("survival", null, "s");
        subCommands.add(new JoinCommand());
        subCommands.add(new InfoCommand());
        subCommands.add(new CreateCommand());
        subCommands.add(new DeleteCommand());
    }

    /**
     * When the command is executed.
     * @param context The context.
     */
    @Override
    public void onCommand(Context context) {
        context.reply(new ListedResponse(
                ChatColor.WHITE + "/survival join <name> " + ChatColor.GRAY + "Joins a survival server.",
                ChatColor.WHITE + "/survival info " + ChatColor.GRAY + "Shows info for the server.",
                ChatColor.WHITE + "/survival create " + ChatColor.GRAY + "Creates your survival server.",
                ChatColor.WHITE + "/survival delete " + ChatColor.GRAY + "Deletes your survival server."
        ));
    }

}
