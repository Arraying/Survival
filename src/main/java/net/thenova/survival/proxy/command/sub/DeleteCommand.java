package net.thenova.survival.proxy.command.sub;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thenova.droplets.proxy.command.internal.AbstractSubCommand;
import net.thenova.droplets.proxy.command.internal.Context;
import net.thenova.droplets.proxy.command.internal.responses.MessageResponse;
import net.thenova.survival.proxy.command.SurvivalResponse;
import net.thenova.survival.proxy.survival.SurvivalHandler;
import net.thenova.survival.proxy.survival.SurvivalServer;

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
public final class DeleteCommand extends AbstractSubCommand {

    /**
     * Creates the sub-command.
     */
    public DeleteCommand() {
        super("delete", "d");
    }

    /**
     * When the sub-command is executed.
     * @param context The context.
     */
    @Override
    public void onCommand(Context context) {
        if(!(context.getSender() instanceof ProxiedPlayer)) {
            context.reply(new SurvivalResponse("Only players in-game can delete survival servers."));
            return;
        }
        SurvivalServer server = SurvivalHandler.INSTANCE.getServer((ProxiedPlayer) context.getSender());
        if(server == null) {
            context.reply(new SurvivalResponse("You do not have a server running."));
            return;
        }
        context.reply(new SurvivalResponse("Deleting server..."));
        SurvivalHandler.INSTANCE.delete(server, true);
    }
}
