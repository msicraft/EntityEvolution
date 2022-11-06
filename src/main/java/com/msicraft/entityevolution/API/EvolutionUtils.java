package com.msicraft.entityevolution.API;

import com.msicraft.entityevolution.API.CustomEvent.EntityEvolutionEvent;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class EvolutionUtils {

    public ArrayList<String> getBlackList() {
        ArrayList<String> list = new ArrayList<>();
        for (String a : EntityEvolution.getPlugin().getConfig().getStringList("Setting.BlackList")) {
            list.add(a.toUpperCase());
        }
        return list;
    }

    public boolean hasNearbyPlayer(Location location) {
        boolean check = false;
        World world = location.getWorld();
        if (world != null) {
            double value = EntityEvolution.getPlugin().getConfig().getDouble("Setting.Check-Nearby-Player.Radius");
            for (Entity entity : world.getNearbyEntities(location, value, value, value)) {
                if (entity instanceof Player) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public void registerBaseEntityData(LivingEntity livingEntity) {
        String type = livingEntity.getType().toString().toUpperCase();
        double baseHealth = getBaseHealth(livingEntity);
        double baseDamage = getBaseDamage(livingEntity);
        double baseArmor = getBaseArmor(livingEntity);
        double baseArmorToughness = getBaseArmorToughness(livingEntity);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseHealth", baseHealth);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseDamage", baseDamage);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseArmor", baseArmor);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseArmoToughness", baseArmorToughness);
        EntityEvolution.entityData.saveConfig();
    }

    public void addEvolutionCount(LivingEntity livingEntity, int count) {
        String type = livingEntity.getType().toString().toUpperCase();
        int nextCount = count + 1;
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".Count", nextCount);
        EntityEvolution.entityData.saveConfig();
        Bukkit.getPluginManager().callEvent(new EntityEvolutionEvent(livingEntity, count, nextCount, true));
    }

    public int getEvolutionCount(LivingEntity livingEntity) {
        String type = livingEntity.getType().toString().toUpperCase();
        return EntityEvolution.entityData.getConfig().contains("Entity." + type + ".Count") ? EntityEvolution.entityData.getConfig().getInt("Entity." + type + ".Count") : 0;
    }

    public double getBaseHealth(LivingEntity livingEntity) {
        double value = 20;
        AttributeInstance instance = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (instance != null) {
            double base = instance.getBaseValue();
            value = (Math.round(base*100)/100.0);
        }
        return value;
    }

    public double getBaseDamage(LivingEntity livingEntity) {
        double value = 0;
        AttributeInstance instance = livingEntity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (instance != null) {
            double base = instance.getBaseValue();
            value = (Math.round(base*100)/100.0);
        }
        return value;
    }

    public double getBaseArmor(LivingEntity livingEntity) {
        double value = 1;
        AttributeInstance instance = livingEntity.getAttribute(Attribute.GENERIC_ARMOR);
        if (instance != null) {
            double base = instance.getBaseValue();
            value = (Math.round(base*100)/100.0);
        }
        return value;
    }

    public double getBaseArmorToughness(LivingEntity livingEntity) {
        double value = 0;
        AttributeInstance instance = livingEntity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        if (instance != null) {
            double base = instance.getBaseValue();
            value = (Math.round(base*100)/100.0);
        }
        return value;
    }

    public void setAttribute(LivingEntity livingEntity, String attributeName, String type, int evolutionCount, double value, boolean check) {
        Bukkit.getScheduler().runTaskLater(EntityEvolution.getPlugin(), ()-> {
            if (check) {
                AttributeInstance instance = livingEntity.getAttribute(Attribute.valueOf(attributeName));
                if (instance != null) {
                    double cal = value * evolutionCount;
                    double roundCal = (Math.round(cal*100)/100.0);
                    AttributeModifier modifier = new AttributeModifier(UUID.randomUUID().toString(), roundCal, AttributeModifier.Operation.valueOf(type));
                    instance.addModifier(modifier);
                    //Bukkit.getServer().broadcastMessage("test2: " + attributeName + " | " + type + " | " + instance.getValue() + " Tag: " + livingEntity.getPersistentDataContainer().get(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING));
                }
            }
        }, 5L);
    }

    public void setEvolutionTag(LivingEntity livingEntity, int count) {
        PersistentDataContainer data = livingEntity.getPersistentDataContainer();
        if (!data.has(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING, String.valueOf(count));
        }
    }

}
