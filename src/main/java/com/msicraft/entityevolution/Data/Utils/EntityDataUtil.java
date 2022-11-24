package com.msicraft.entityevolution.Data.Utils;

import com.msicraft.entityevolution.API.EvolutionUtils;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class EntityDataUtil {

    //private EvolutionUtils evolutionUtils = new EvolutionUtils();

    public void loadYamlToHashMap() {
        ConfigurationSection section = EntityEvolution.entityData.getConfig().getConfigurationSection("Entity");
        if (section == null) {
            //Bukkit.getConsoleSender().sendMessage(EntityEvolution.getPrefix() + ChatColor.RED + " Invalid EntityData.yml");
            return;
        }
        for (String entityName : section.getKeys(false)) {
            String lastDataEncode = EntityEvolution.entityData.getConfig().getString("Entity." + entityName + ".LastData");
            if (lastDataEncode != null) {
                EntityEvolution.getPlugin().getVanillaMobLastData().put(entityName, lastDataEncode);
            }
        }
    }

    public void saveHashMapToYaml() {
        for (String entityName : EntityEvolution.getPlugin().getVanillaMobLastData().keySet()) {
            if (EntityEvolution.entityData.getConfig().contains("Entity." + entityName)) {
                String lastDataEncode = EntityEvolution.getPlugin().getVanillaMobLastData().get(entityName);
                EntityEvolution.entityData.getConfig().set("Entity." + entityName + ".LastData", lastDataEncode);
                EntityEvolution.entityData.saveConfig();
            }
        }
    }

    public double getEntityBaseHealth(String evolutionEntityName) {
        double value = 20;
        if (EntityEvolution.entityData.getConfig().contains("Entity." + evolutionEntityName)) {
            double baseHealth = EntityEvolution.entityData.getConfig().getDouble("Entity." + evolutionEntityName + ".BaseHealth");
            value = (Math.round(baseHealth*100)/100.0);
        }
        return value;
    }

    public double getEntityBaseDamage(String evolutionEntityName) {
        double value = 0;
        if (EntityEvolution.entityData.getConfig().contains("Entity." + evolutionEntityName)) {
            double baseHealth = EntityEvolution.entityData.getConfig().getDouble("Entity." + evolutionEntityName + ".BaseDamage");
            value = (Math.round(baseHealth*100)/100.0);
        }
        return value;
    }

    public double getEntityBaseArmor(String evolutionEntityName) {
        double value = 0;
        if (EntityEvolution.entityData.getConfig().contains("Entity." + evolutionEntityName)) {
            double baseHealth = EntityEvolution.entityData.getConfig().getDouble("Entity." + evolutionEntityName + ".BaseArmor");
            value = (Math.round(baseHealth*100)/100.0);
        }
        return value;
    }

    public double getEntityBaseArmorToughness(String evolutionEntityName) {
        double value = 0;
        if (EntityEvolution.entityData.getConfig().contains("Entity." + evolutionEntityName)) {
            double baseHealth = EntityEvolution.entityData.getConfig().getDouble("Entity." + evolutionEntityName + ".BaseArmorToughness");
            value = (Math.round(baseHealth*100)/100.0);
        }
        return value;
    }

    public String getEntityTag(String evolutionEntityName) {
        String tag = "VanillaEntity";
        if (EntityEvolution.entityData.getConfig().contains("Entity." + evolutionEntityName)) {
            tag = EntityEvolution.entityData.getConfig().getString("Entity." + evolutionEntityName + ".Tag");
        }
        return tag;
    }

    public int getEntityEvolutionCount(String evolutionEntityName) {
        int count = 0;
        if (EntityEvolution.entityData.getConfig().contains("Entity." + evolutionEntityName + ".Count")) {
            count = EntityEvolution.entityData.getConfig().getInt("Entity." + evolutionEntityName + ".Count");
        }
        return count;
    }

}
