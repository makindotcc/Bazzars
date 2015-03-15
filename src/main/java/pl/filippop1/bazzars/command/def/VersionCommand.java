/*
 * Copyright 2015 Filip.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.filippop1.bazzars.command.def;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.command.Command;
import pl.filippop1.bazzars.command.CommandException;

public class VersionCommand extends Command {
    public VersionCommand() {
        super("wersja", "przydatne informacje oraz linki",
                new String[] {"ver", "about", "author", "autor", "authors", "autorzy"});
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        String git = BazzarsPlugin.SOURCE_URL;
        sender.sendMessage(ChatColor.GOLD + "Bazzars wersja " + ChatColor.YELLOW + BazzarsPlugin.getVersion()
                + ChatColor.GOLD + " by " + ChatColor.YELLOW + BazzarsPlugin.AUTHORS);
        sender.sendMessage(ChatColor.GOLD + "Otwarty kod: " + ChatColor.YELLOW + git);
        sender.sendMessage(ChatColor.GOLD + "Bledy oraz propozycje: " + ChatColor.YELLOW + git + "/issues");
    }
}
