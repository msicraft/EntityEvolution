package com.msicraft.entityevolution.Event;

import com.msicraft.entityevolution.API.CustomEvent.EvolutionEntitySpawnEvent;
import com.msicraft.entityevolution.API.EvolutionUtils;
import com.msicraft.entityevolution.AttributeUtils;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class EvolutionEntitySpawn implements Listener {

    private EvolutionUtils utils = new EvolutionUtils();
    private AttributeUtils attributeUtils = new AttributeUtils();

    private final ArrayList<String> evolutionTypeList = new ArrayList<>(Arrays.asList("ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"));
    private final ArrayList<String> evolutionAttribute = new ArrayList<>(Arrays.asList("GENERIC_MAX_HEALTH", "GENERIC_ATTACK_DAMAGE", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS",
            "GENERIC_MOVEMENT_SPEED", "GENERIC_KNOCKBACK_RESISTANCE"));

    @EventHandler
    public void onEvolutionEntitySpawn(CreatureSpawnEvent e) {
        boolean enabledCheck = EntityEvolution.getPlugin().getConfig().getBoolean("Setting.Enabled");
        if (enabledCheck) {
            ArrayList<String> registerEntityList = new ArrayList<>(EntityEvolution.getPlugin().getRegisterEntityList());
            LivingEntity livingEntity = e.getEntity();
            String entityType = e.getEntityType().name().toUpperCase();
            if (registerEntityList.contains(entityType)) {
                int count = utils.getEvolutionCount(livingEntity);
                if (utils.getBlackList().contains(entityType)) {
                    Bukkit.getPluginManager().callEvent(new EvolutionEntitySpawnEvent(livingEntity, count, e.getSpawnReason(),false));
                } else {
                    for(String attributeType : EntityEvolution.getPlugin().getEvolutionAttributes()) {
                        boolean check = false;
                        String type = null;
                        int attributeCount = -1;
                        double value = 0;
                        switch (attributeType) {
                            case "Health" -> {
                                attributeCount = 0;
                                type = EntityEvolution.getPlugin().getConfig().getString("Evolution-Setting." + attributeType + ".Type");
                                check = EntityEvolution.getPlugin().getConfig().getBoolean("Evolution-Setting." + attributeType + ".Enabled");
                                value = EntityEvolution.getPlugin().getConfig().getDouble("Evolution-Setting." + attributeType + ".Value");
                            }
                            case "Damage" -> {
                                attributeCount = 1;
                                type = EntityEvolution.getPlugin().getConfig().getString("Evolution-Setting." + attributeType + ".Type");
                                check = EntityEvolution.getPlugin().getConfig().getBoolean("Evolution-Setting." + attributeType + ".Enabled");
                                value = EntityEvolution.getPlugin().getConfig().getDouble("Evolution-Setting." + attributeType + ".Value");
                            }
                            case "Armor" -> {
                                attributeCount = 2;
                                type = EntityEvolution.getPlugin().getConfig().getString("Evolution-Setting." + attributeType + ".Type");
                                check = EntityEvolution.getPlugin().getConfig().getBoolean("Evolution-Setting." + attributeType + ".Enabled");
                                value = EntityEvolution.getPlugin().getConfig().getDouble("Evolution-Setting." + attributeType + ".Value");
                            }
                            case "ArmorToughness" -> {
                                attributeCount = 3;
                                type = EntityEvolution.getPlugin().getConfig().getString("Evolution-Setting." + attributeType + ".Type");
                                check = EntityEvolution.getPlugin().getConfig().getBoolean("Evolution-Setting." + attributeType + ".Enabled");
                                value = EntityEvolution.getPlugin().getConfig().getDouble("Evolution-Setting." + attributeType + ".Value");
                            }
                            case "MovementSpeed" -> {
                                attributeCount = 4;
                                type = EntityEvolution.getPlugin().getConfig().getString("Evolution-Setting." + attributeType + ".Type");
                                check = EntityEvolution.getPlugin().getConfig().getBoolean("Evolution-Setting." + attributeType + ".Enabled");
                                value = EntityEvolution.getPlugin().getConfig().getDouble("Evolution-Setting." + attributeType + ".Value");
                            }
                            case "KnockbackResistance" -> {
                                attributeCount = 5;
                                type = EntityEvolution.getPlugin().getConfig().getString("Evolution-Setting." + attributeType + ".Type");
                                check = EntityEvolution.getPlugin().getConfig().getBoolean("Evolution-Setting." + attributeType + ".Enabled");
                                value = EntityEvolution.getPlugin().getConfig().getDouble("Evolution-Setting." + attributeType + ".Value");
                            }
                        }
                        if (attributeCount == -1) {
                            Bukkit.getConsoleSender().sendMessage(EntityEvolution.getPrefix() + ChatColor.RED + " Invalid config attribute: " + ChatColor.GREEN + " Attribute: " + attributeType + " | " +attributeCount);
                            continue;
                        }
                        if (type == null || !evolutionTypeList.contains(type)) {
                            type = "ADD_NUMBER";
                        }
                        String attributeName = evolutionAttribute.get(attributeCount);
                        utils.setEvolutionTag(livingEntity, count);
                        utils.setAttribute(livingEntity, attributeName, type, count, value, check);
                    }
                    Bukkit.getPluginManager().callEvent(new EvolutionEntitySpawnEvent(livingEntity, count, e.getSpawnReason(), true));
                }
            }
        }
    }

}
