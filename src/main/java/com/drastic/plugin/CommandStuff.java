package com.drastic.plugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandStuff implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3)
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(!p.isOp())
            {
                p.setGameMode(GameMode.SURVIVAL);

                p.getInventory().clear();

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

        return true;
    }

}
