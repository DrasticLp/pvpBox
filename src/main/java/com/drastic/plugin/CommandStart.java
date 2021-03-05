package com.drastic.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandStart implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3)
    {
        if(!Main.getInstance().start)
        {
            Main.getInstance().start = true;         
        }

        return true;
    }

}
