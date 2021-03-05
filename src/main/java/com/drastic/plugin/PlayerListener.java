package com.drastic.plugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        e.setJoinMessage("§9" + e.getPlayer().getDisplayName() + " §6s'est connecté");

        if(!e.getPlayer().isOp())
        {
            e.getPlayer().getInventory().clear();
        }

        if(e.getPlayer().getGameMode() == GameMode.SURVIVAL && !e.getPlayer().isOp())
        {
            Player p = e.getPlayer();
            
            ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
            ItemMeta metaHelmet = helmet.getItemMeta();
            metaHelmet.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            helmet.setItemMeta(metaHelmet);

            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta metaChestplate = chestplate.getItemMeta();
            metaChestplate.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            chestplate.setItemMeta(metaChestplate);

            ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
            ItemMeta metaLeggings = leggings.getItemMeta();
            metaLeggings.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            leggings.setItemMeta(metaLeggings);

            ItemStack boots = new ItemStack(Material.IRON_BOOTS);
            ItemMeta metaBoots = boots.getItemMeta();
            metaBoots.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            boots.setItemMeta(metaBoots);

            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta metaSword = sword.getItemMeta();
            metaSword.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
            sword.setItemMeta(metaSword);

            ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta metaPick = pick.getItemMeta();
            metaPick.addEnchant(Enchantment.DIG_SPEED, 3, true);
            pick.setItemMeta(metaPick);

            ItemStack bow = new ItemStack(Material.BOW);
            ItemMeta metaBow = bow.getItemMeta();
            metaBow.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
            bow.setItemMeta(metaBow);
            
            ItemStack gapples = new ItemStack(Material.GOLDEN_APPLE, 4);
            ItemStack blocks = new ItemStack(Material.COBBLESTONE, 64);
            ItemStack steaks = new ItemStack(Material.COOKED_BEEF, 64);
            ItemStack water = new ItemStack(Material.WATER_BUCKET, 1);
            ItemStack arrow = new ItemStack(Material.ARROW, 64);

            p.getInventory().setHelmet(helmet);
            p.getInventory().setChestplate(chestplate);
            p.getInventory().setLeggings(leggings);
            p.getInventory().setBoots(boots);

            p.getInventory().addItem(sword);
            p.getInventory().addItem(pick);
            p.getInventory().addItem(gapples);
            p.getInventory().addItem(steaks);
            p.getInventory().addItem(blocks);
            p.getInventory().addItem(blocks);
            p.getInventory().addItem(water);
            p.getInventory().addItem(bow);
            p.getInventory().addItem(arrow);
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(!Main.getInstance().started)
        {
            if(e.getItem().getType() != Material.COOKED_BEEF)
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent e)
    {
        if(!Main.getInstance().started)
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e)
    {
        if(!Main.getInstance().started)
        {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        if(Main.getInstance().started)
        {
            if(!e.getPlayer().isOp())
            {
                e.getPlayer().setGameMode(GameMode.SPECTATOR);

                int remainingPlayers = 0;

                for(Player players : Bukkit.getOnlinePlayers())
                {
                    if(players.getGameMode() == GameMode.SURVIVAL)
                    {
                        remainingPlayers++;
                    }
                }

                Player p = null;

                if(remainingPlayers == 1)
                {
                    for(Player pl : Bukkit.getOnlinePlayers())
                    {
                        if(pl.getGameMode() == GameMode.SURVIVAL)
                        {
                            p = pl;
                        }
                    }

                    Main.getInstance().started = false;

                    p.setGameMode(GameMode.SPECTATOR);

                    Bukkit.broadcastMessage("§6Bravo à §c" + p.getDisplayName());

                    for(Player pls : Bukkit.getOnlinePlayers())
                    {
                        pls.sendTitle("§2Fin de Partie", "");
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onEntityKill(PlayerDeathEvent e)
    {
        if(Main.getInstance().started)
        {
            e.getEntity().setGameMode(GameMode.SPECTATOR);

            if(e.getEntity().getKiller() != null)
            {
                e.setDeathMessage("§9" + e.getEntity().getDisplayName() + " §6s'est fait fumer par §9" + e.getEntity().getKiller().getName());

                Player p = e.getEntity().getKiller();

                if(p.getHealth() <= 14)
                {
                    p.setHealth(p.getHealth() + 6);
                }
                else
                {
                    p.setHealth(20);
                }

                int i = Main.getInstance().kills.get(p) + 1;

                Main.getInstance().kills.remove(p);
                Main.getInstance().kills.put(p, i);

                p.getInventory().setHelmet(repair(p.getInventory().getHelmet()));
                p.getInventory().setChestplate(repair(p.getInventory().getChestplate()));
                p.getInventory().setLeggings(repair(p.getInventory().getLeggings()));
                p.getInventory().setBoots(repair(p.getInventory().getBoots()));

                p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                p.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 32));

                int remainingPlayers = 0;

                for(Player players : Bukkit.getOnlinePlayers())
                {
                    if(players.getGameMode() == GameMode.SURVIVAL)
                    {
                        remainingPlayers++;
                    }
                }

                if(remainingPlayers == 1)
                {
                    Main.getInstance().started = false;

                    p.setGameMode(GameMode.SPECTATOR);

                    Bukkit.broadcastMessage("§6Bravo à §c" + p.getDisplayName());

                    for(Player pls : Bukkit.getOnlinePlayers())
                    {
                        pls.sendTitle("§2Fin de Partie", "");
                    }
                }
            }
            else
            {
                e.setDeathMessage("§9" + e.getEntity().getDisplayName() + " §6est mort comme un §mcon§f §6génie");

                int remainingPlayers = 0;

                for(Player players : Bukkit.getOnlinePlayers())
                {
                    if(players.getGameMode() == GameMode.SURVIVAL)
                    {
                        remainingPlayers++;
                    }
                }

                if(remainingPlayers == 1)
                {
                    Main.getInstance().started = false;

                    Player winner = null;

                    for(Player pls : Bukkit.getOnlinePlayers())
                    {
                        if(pls.getGameMode() == GameMode.SURVIVAL)
                        {
                            pls.setGameMode(GameMode.SPECTATOR);
                            winner = pls;
                        }

                        pls.sendTitle("§2Fin de Partie", "");
                    }

                    Bukkit.broadcastMessage("§6Bravo à §c" + winner.getDisplayName());
                }
            }

            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        if(!e.getPlayer().isOp())
            e.setCancelled(true);
    }
    
    private ItemStack repair(ItemStack s1)
    {
        ItemStack stack = s1.clone();

        if(stack.getDurability() <= stack.getType().getMaxDurability() - 70)
        {
            stack.setDurability((short)(stack.getDurability() - 70));
        }
        else
        {
            stack.setDurability(stack.getType().getMaxDurability());
        }

        return stack;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if(!Main.getInstance().started)
            e.setCancelled(true);
    }
}
