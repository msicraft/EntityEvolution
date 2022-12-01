package com.msicraft.entityevolution.EvolutionSkills;

import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class EvolutionSkillUtil{

    public ArrayList<String> getAbilityList() {
        ArrayList<String> list = new ArrayList<>();
        ConfigurationSection section = EntityEvolution.abilityData.getConfig().getConfigurationSection("Ability");
        if (section != null) {
            list.addAll(section.getKeys(false));
        }
        return list;
    }

    public String getAbilityCastType(String abilityData) {
        String abilityCastType;
        String[] data = abilityData.split(":");
        abilityCastType = data[0];
        return abilityCastType;
    }

    public String getAbilityName(String abilityData) {
        String abilityName;
        String[] data = abilityData.split(":");
        abilityName = data[1];
        return abilityName;
    }

    public void saveAbilityDataToEntity(String entityName, String abilityType, String abilityName) {
        if (EntityEvolution.entityData.getConfig().contains("Entity." + entityName)) {
            ArrayList<String> abilityList = new ArrayList<>(EntityEvolution.entityData.getConfig().getStringList("Entity." + entityName + ".Ability"));
            String data = abilityType + ":" + abilityName;
            abilityList.add(data);
            EntityEvolution.entityData.getConfig().set("Entity." + entityName + ".Ability", abilityList);
        }
    }

}
