package com.msicraft.entityevolution.Data.Utils;

import com.msicraft.entityevolution.API.EvolutionUtils;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class EntityDataUtil {

    private EvolutionUtils evolutionUtils = new EvolutionUtils();

    public void loadYamlToHashMap() {
        ConfigurationSection section = EntityEvolution.entityData.getConfig().getConfigurationSection("Entity");
        if (section == null) {
            Bukkit.getConsoleSender().sendMessage(EntityEvolution.getPrefix() + ChatColor.RED + " Invalid EntityData.yml");
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

}
