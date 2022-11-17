package com.msicraft.entityevolution.Command;

import com.msicraft.entityevolution.EntityEvolution;
import com.msicraft.entityevolution.Inventory.EvolutionEntityInfoInv;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("entityevolution")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "/entityevolution help");
            }
            if (args.length >= 1) {
                String val = args[0];
                switch (val) {
                    case "help" -> {
                        if (args.length == 1) {
                            sender.sendMessage(ChatColor.YELLOW + "/entityevolution help : " + ChatColor.WHITE + "Show the list of commands of the [Entity Evolution] plugin");
                            sender.sendMessage(ChatColor.YELLOW + "/entityevolution reload : " + ChatColor.WHITE + "Reload config");
                        }
                    }
                    case "reload" -> {
                        if (args.length == 1) {
                            if (sender.isOp()) {
                                EntityEvolution.getPlugin().dataFilesReload();
                                sender.sendMessage(EntityEvolution.getPrefix() + ChatColor.GREEN + " Plugin config reloaded");
                            }
                        }
                    }
                    case "info" -> {
                        if (args.length == 1) {
                            if (sender instanceof Player player) {
                                if (player.isOp()) {
                                    EvolutionEntityInfoInv evolutionEntityInfoInv = new EvolutionEntityInfoInv(player);
                                    player.openInventory(evolutionEntityInfoInv.getInventory());
                                }
                            }
                        }
                    }
                    /*
                    case "setting" -> {
                        if (args.length == 1) {
                            if (sender instanceof Player player) {
                                if (player.isOp()) {
                                    EvolutionSettingInv evolutionSettingInv = new EvolutionSettingInv(player);
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.selectInv();
                                }
                            }
                        }
                    }
                     */
                }
            }
        }

        return false;
    }
}
