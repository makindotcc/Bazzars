/*
 * Copyright 2014 Filip.
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

package pl.filippop1.bazzars.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command {
    private final String name;
    private final String description;
    private String usage;
    private String[] aliases;
    private boolean protect;
    
    public Command(String name, String description) {
        this(name, description, null, null);
    }
    
    public Command(String name, String description, String usage) {
        this(name, description, usage, null);
    }
    
    public Command(String name, String description, String[] aliases) {
        this(name, description, null, aliases);
    }
    
    public Command(String name, String description, String usage, String[] aliases) {
        Validate.notNull(name, "name can not be null");
        Validate.notNull(description, "description can not be null");
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }
    
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (sender instanceof Player) {
            this.execute((Player) sender, args);
        } else {
            throw new CommandException("Musisz byc graczem, aby wykonac ta komende.");
        }
    }
    
    public void execute(Player player, String[] args) throws CommandException {
        throw new CommandException("Nie znaleziono metody wykonujacej komende " + this.getName() + ".");
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getUsage() {
        String us = "/bazar " + this.getName();
        if (this.usage != null) {
            us = us + " " + this.usage;
        }
        return us;
    }
    
    public String[] getAliases() {
        return this.aliases;
    }
    
    public boolean hasAliases() {
        return this.aliases != null;
    }
    
    public boolean hasCustomUsage() {
        return this.usage != null;
    }
    
    public boolean isProtected() {
        return this.protect;
    }
    
    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }
    
    public void setProtected(boolean protect) {
        this.protect = protect;
    }
    
    public void setUsage(String usage) {
        this.usage = usage;
    }
}
