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

}
