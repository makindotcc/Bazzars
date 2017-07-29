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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.command.Command;
import pl.filippop1.bazzars.command.CommandException;

public class NameCommand extends Command {
    public NameCommand() {
        super("nazwa", "ustawia nazwe bazaru", new String[] {"name", "tytul", "title"});
    }
    
    @Override
    public void execute(Player player, String[] args) throws CommandException {
        Bazar bazar = BazarManager.getBazar(player.getUniqueId());
        if (bazar == null) {
            throw new CommandException("Stworz bazar, aby zmienic jego nazwe! Aby to zrobic uzyj /bazar stworz.");
        } else if (args.length > 1 && args[1].equalsIgnoreCase("reset")) {
            this.reset(bazar, player);
            player.sendMessage(ChatColor.GREEN + "Nazwa Twojego bazaru zostala zresetowana.");
            return;
        } else if (args.length == 1) {
            throw new CommandException("Uzycie: /bazar nazwa sklep z prochem");
        }
        
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        
        String name = builder.toString().substring(0, builder.toString().length() - 1);
        if (name.length() > Bazar.MAX_NAME_LENGTH) {
            throw new CommandException("Maksymalna ilosc znakow w nazwie to " + Bazar.MAX_NAME_LENGTH + ".");
        }
        
        bazar.setName(name);
        if (bazar.isOpen() && BazzarsPlugin.getConfiguration().isHologramEnabled()) {
            bazar.getHologram().setText(name);
            bazar.getHologram().update();
        }
        player.sendMessage(ChatColor.GREEN + "Zmieniono nazwe bazaru.");
    }
    
    private void reset(Bazar bazar, Player player) {
        bazar.setName(BazzarsPlugin.getConfiguration().getBazarDefaultName(player.getName()));
    }
}
