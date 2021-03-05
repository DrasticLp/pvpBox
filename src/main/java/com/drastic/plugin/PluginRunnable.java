package com.drastic.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.drastic.plugin.scoreboard.ScoreboardManager;

public class PluginRunnable extends BukkitRunnable
{
    int timer = 6;

    @Override
    public void run()
    {
        if(Main.getInstance().fireworks)
        {
            Firework firework = Bukkit.getWorld("world_the_end").spawn(new Location(Bukkit.getWorld("world"), 0, 85, 0), Firework.class);
            FireworkMeta data = (FireworkMeta)firework.getFireworkMeta();
            data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.WHITE).with(Type.STAR).withFlicker().build());
            data.setPower(1);
            firework.setFireworkMeta(data);
        }
        
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(ScoreboardManager.scoreboardGame.get(p) == null)
            {
                new ScoreboardManager(p);
                ScoreboardManager.scoreboardGame.get(p).loadScoreboard();
            }
            else
            {
                if(Main.getInstance().started)
                {
                    int pcount = 0;
                    int pNotOp = 0;

                    for(Player pl : Bukkit.getOnlinePlayers())
                    {
                        if(pl.getGameMode() == GameMode.SURVIVAL)
                            pcount++;
                        
                        if(!pl.isOp())
                            pNotOp ++;
                    } 

                                        
                    ScoreboardManager.scoreboardGame.get(p).updateLine(4, "§6Joueurs en vie: §e" + pcount + "§6/§e" + pNotOp);
                }
                else
                {
                    int pcount = 0;

                    for(Player pl : Bukkit.getOnlinePlayers())
                    {
                        if(pl.getGameMode() == GameMode.SURVIVAL)
                            pcount++;
                    }               

                    ScoreboardManager.scoreboardGame.get(p).updateLine(4, "§6Joueurs: §e" + pcount + "§6/§e" + 100);
                }
            }
            
            if(Main.getInstance().kills.get(p) == null)
            {
                Main.getInstance().kills.put(p, 0);
            }
            else
            {
                ScoreboardManager.scoreboardGame.get(p).updateLine(3, "§6Kills: §9" + Main.getInstance().kills.get(p));
            }
        }

        if(Main.getInstance().start)
        {
            if(timer != 0)
            {
                timer--;
                Bukkit.broadcastMessage("§cDébut dans §e" + timer + " §csecondes");

                for(Player p : Bukkit.getOnlinePlayers())
                {
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10f, 1f);
                    p.setLevel(timer);
                    ScoreboardManager.scoreboardGame.get(p).updateLine(1, ChatColor.WHITE + "Début dans: " + ChatColor.GREEN + this.timer + " §fsecondes");
                }
            }
            else
            {
                WorldBorder wb = Bukkit.getWorld("world").getWorldBorder();

                wb.setCenter(0, 0);
                wb.setSize(16);

                Location loc = null;

                for(int y = 255; y > 1; y--)
                {
                    Location location = new Location(Bukkit.getWorld("world"), 0, y, 0);

                    if(location.getBlock().getType() != Material.AIR)
                    {
                        location.add(0, 1, 0);

                        loc = location.clone();
                        break;
                    }
                }

                if(loc != null)
                {
                    for(Player p : Bukkit.getOnlinePlayers())
                    {
                        p.teleport(loc);
                        p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10f, 1f);
                        ScoreboardManager.scoreboardGame.get(p).updateLine(1, ChatColor.WHITE + "Phase: §aPvP");
                    }

                    Bukkit.broadcastMessage("§cDébut de la partie !");
                }
                
                Main.getInstance().started = true;
                Main.getInstance().start = false;
            }                     
        }
    }

}
