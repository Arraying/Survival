package net.thenova.survival.proxy.command.sub;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thenova.droplets.proxy.command.internal.AbstractSubCommand;
import net.thenova.droplets.proxy.command.internal.Context;
import net.thenova.droplets.proxy.command.internal.responses.MessageResponse;
import net.thenova.survival.proxy.command.SurvivalResponse;
import net.thenova.survival.proxy.survival.SurvivalHandler;

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
public final class CreateCommand extends AbstractSubCommand {

    /**
     * Creates the sub-command.
     */
    public CreateCommand() {
        super("create", "c");
    }

    /**
     * When the sub-command is executed.
     * @param context The context.
     */
    @Override
    public void onCommand(Context context) {
        if(!(context.getSender() instanceof ProxiedPlayer)) {
            context.reply(new SurvivalResponse("Only players in-game can create survival servers."));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) context.getSender();
        if(!player.hasPermission("survival.create")) {
            context.reply(new SurvivalResponse("You do not have permission to create a server."));
            return;
        }
        if(SurvivalHandler.INSTANCE.getServer(player) != null) {
            context.reply(new SurvivalResponse("You cannot create another server."));
            return;
        }
        if(!SurvivalHandler.INSTANCE.create(player)) {
            context.reply(new SurvivalResponse("You are already creating a server."));
            return;
        }
        if(SurvivalHandler.INSTANCE.getServerSize() > 10) {
            context.reply(new SurvivalResponse("Too many survival servers online, space is limited, try again soon."));
            return;
        }
        context.reply(new SurvivalResponse("Creating server... this may take some time."));
    }

}
