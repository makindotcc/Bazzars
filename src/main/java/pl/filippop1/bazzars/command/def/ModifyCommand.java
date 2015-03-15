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
import org.bukkit.entity.Player;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.command.Command;
import pl.filippop1.bazzars.command.CommandException;

public class ModifyCommand extends Command {
    public ModifyCommand() {
        super("zmien", "edytuje oferte", new String[] {"modify"});
    }
    
    @Override
    public void execute(Player player, String[] args) throws CommandException {
        Bazar bazar = BazarManager.getBazar(player.getName());
        if (bazar == null) {
            throw new CommandException("Nie posiadasz bazaru! Aby go stworzyc uzyj /bazar stworz.");
        } else if (!bazar.isOpen()) {
            throw new CommandException("Twoj bazar nie jest otwarty! Od teraz mozesz modyfikowac swoja oferte.");
        }
        
        bazar.setOpen(false);
        bazar.getHologram().delete();
        player.sendMessage(ChatColor.GREEN + "Mozesz juz edytowac swoja oferte! Twoj bazar zostal zamkniety.");
    }
}
