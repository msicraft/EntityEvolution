package com.msicraft.entityevolution.EvolutionSkills.Abilities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class PotionBuff implements Listener {

    @EventHandler
    public void applyPotionEffect(CreatureSpawnEvent e) {
        LivingEntity livingEntity = e.getEntity();
        PersistentDataContainer data = livingEntity.getPersistentDataContainer();
    }

}
