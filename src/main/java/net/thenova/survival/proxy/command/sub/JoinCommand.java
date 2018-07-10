package net.thenova.survival.proxy.command.sub;

import net.md_5.bungee.api.config.ServerInfo;
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
public final class JoinCommand extends AbstractSubCommand {

    /**
     * Creates the sub-command.
     */
    public JoinCommand() {
        super("join", "j", "send", "s");
    }

    /**
     * When the sub-command is executed.
     * @param context The context.
     */
    @Override
    public void onCommand(Context context) {
        if(!(context.getSender() instanceof ProxiedPlayer)) {
            context.reply(new SurvivalResponse("Only players in-game can join survival servers."));
            return;
        }
        if(context.getArgs().length == 0) {
            context.reply(new SurvivalResponse("Please provide a player name."));
            return;
        }
        String name = context.getArgs()[0];
        ServerInfo survivalServer = SurvivalHandler.INSTANCE.getServerForPlayerName(name);
        if(survivalServer == null) {
            context.reply(new SurvivalResponse("That server does not exist."));
            return;
        }
        context.reply(new SurvivalResponse("Connecting you to the server..."));
        ((ProxiedPlayer) context.getSender()).connect(survivalServer);
    }

}
