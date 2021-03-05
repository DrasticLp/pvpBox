package com.drastic.plugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private static Main instance;
    public boolean start;
    public boolean started;
    public PluginRunnable runnable;
    public boolean fireworks;

    public Map<Player, Integer> kills = new HashMap<Player, Integer>();

    @Override
    public void onLoad()
    {
        instance = this;
    }

    @Override
    public void onEnable()
    {
        this.start = false;
        new ListenerManager(Main.getInstance()).registerListeners();

        this.runnable = new PluginRunnable();
        this.runnable.runTaskTimer(Main.getInstance(), 0l, 20l);
        setCommands();

        Bukkit.getServer().getConsoleSender().sendMessage("§2PvP Box by DrasticLp Enabled");
    }

    @Override
    public void onDisable()
    {
        Bukkit.getServer().getConsoleSender().sendMessage("§2PvP Box by DrasticLp Disabled");
    }

    private void setCommands()
    {
        this.getCommand("start").setExecutor(new CommandStart());
        this.getCommand("stuff").setExecutor(new CommandStuff());
    }

    public static Main getInstance()
    {
        return instance;
    }
}
