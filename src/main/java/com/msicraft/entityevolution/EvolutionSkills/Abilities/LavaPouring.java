package com.msicraft.entityevolution.EvolutionSkills.Abilities;

import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LavaPouring implements Listener {

    @EventHandler
    public void onLavaPouringEvent(EntityDamageByEntityEvent e) {
        if (EntityEvolution.abilityData.getConfig().getBoolean("Ability.LavaPouring.Enabled")) {
        }
    }

    public void castLavaPouring(Location location) {
        boolean check = EntityEvolution.abilityData.getConfig().getBoolean("Ability.LavaPouring.Enabled");
        if (check) {
            int removeDelay = EntityEvolution.abilityData.getConfig().getInt("Ability.LavaPouring.RemoveDelay");
            World world = location.getWorld();
            if (world != null) {
                Block castBlock = world.getBlockAt(location);
                if (castBlock.getType() == Material.AIR) {
                    castBlock.setType(Material.LAVA);
                    if (removeDelay != -1) {
                        Bukkit.getScheduler().runTaskLater(EntityEvolution.getPlugin(), () -> {
                            castBlock.setType(Material.AIR);
                        }, removeDelay);
                    }
                }
            }
        }
    }

}
