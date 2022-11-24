package com.msicraft.entityevolution.EvolutionSkills;

import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class EvolutionSkillUtil{

    public void saveAbilityTagToYml(String entityName, String abilityTag) {
        if (EntityEvolution.entityData.getConfig().contains("Entity." + entityName)) {
            ArrayList<String> abilitiesList = new ArrayList<>(EntityEvolution.entityData.getConfig().getStringList("Entity." + entityName + ".Ability"));
            abilitiesList.add(abilityTag);
            EntityEvolution.entityData.getConfig().set("Entity." + entityName + ".Ability", abilitiesList);
            EntityEvolution.entityData.saveConfig();
        }
    }

    public void setEntityAbilityTag(LivingEntity livingEntity, String tag) {
        ArrayList<String> abilityList = new ArrayList<>();
        PersistentDataContainer data = livingEntity.getPersistentDataContainer();
        if (data.has(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Ability"), PersistentDataType.STRING)) {

        }
    }

    public void applySpeedBuff(LivingEntity livingEntity) {
        PotionEffectType type = PotionEffectType.SPEED;
        int level = EntityEvolution.abilityData.getConfig().getInt("Ability.SpeedBuff.BuffLevel");
        PotionEffect potionEffect = new PotionEffect(type, 999999, (level - 1), false, false);
        livingEntity.addPotionEffect(potionEffect);
    }

    public void applyDamageBuff(LivingEntity livingEntity) {
        PotionEffectType type = PotionEffectType.INCREASE_DAMAGE;
        int level = EntityEvolution.abilityData.getConfig().getInt("Ability.DamageBuff.BuffLevel");
        PotionEffect potionEffect = new PotionEffect(type, 999999, (level - 1), false, false);
        livingEntity.addPotionEffect(potionEffect);
    }

}
