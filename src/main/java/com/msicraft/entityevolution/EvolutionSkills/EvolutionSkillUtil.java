package com.msicraft.entityevolution.EvolutionSkills;

import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class EvolutionSkillUtil{

    public void saveAbilityTag(String entityName, String abilityTag) {
        if (EntityEvolution.entityData.getConfig().contains("Entity." + entityName)) {
            ArrayList<String> abilitiesList = new ArrayList<>(EntityEvolution.entityData.getConfig().getStringList("Entity." + entityName + ".Ability"));
            abilitiesList.add(abilityTag);
            EntityEvolution.entityData.getConfig().set("Entity." + entityName + ".Ability", abilitiesList);
            EntityEvolution.entityData.saveConfig();
        }
    }
}
