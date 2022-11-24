package com.msicraft.entityevolution.EvolutionSkills.Abilities;

import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class LavaPouring implements Listener {

    @EventHandler
    public void onLavaPouringEvent(EntityDamageByEntityEvent e) {
        if (EntityEvolution.abilityData.getConfig().getBoolean("Ability.LavaPouring.Enabled")) {
        }
    }

}
