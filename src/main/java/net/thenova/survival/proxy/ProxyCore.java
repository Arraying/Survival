package net.thenova.survival.proxy;

import net.md_5.bungee.api.plugin.Plugin;
import net.thenova.droplets.droplet.Droplet;
import net.thenova.droplets.droplet.DropletHandler;
import net.thenova.survival.common.SurvivalConstants;
import net.thenova.survival.proxy.command.SurvivalCommand;

import java.util.List;
import java.util.stream.Collectors;

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
@SuppressWarnings("unused")
public final class ProxyCore extends Plugin {

    /**
     * When the plugin is enabled.
     */
    @Override
    public void onEnable() {
        List<Droplet> toPurge = DropletHandler.INSTANCE.getAll()
                .stream()
                .filter(droplet -> droplet.getTemplate().equals(SurvivalConstants.UUID))
                .collect(Collectors.toList());
        for(Droplet droplet : toPurge) {
            droplet.delete();
        }
        getProxy().getPluginManager().registerListener(this, new ProxyEvents());
        getProxy().getPluginManager().registerCommand(this, new SurvivalCommand());
    }

}