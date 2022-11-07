package com.msicraft.entityevolution.Event;

import com.msicraft.entityevolution.API.CustomEvent.EntityEvolutionEvent;
import com.msicraft.entityevolution.API.CustomEvent.EvolutionEntityDeathEvent;
import com.msicraft.entityevolution.API.EvolutionUtils;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Random;

public class EntityDeath implements Listener {

    private EvolutionUtils utils = new EvolutionUtils();
    private Random random = new Random();

    @EventHandler
    public void entityDeathToEvolution(EntityDeathEvent e) {
        if (EntityEvolution.getPlugin().getConfig().getBoolean("Setting.Enabled")) {
            if (e.getEntityType() != EntityType.PLAYER) {
                ArrayList<String> registerEntityList = new ArrayList<>(EntityEvolution.getPlugin().getRegisterEntityList());
                LivingEntity livingEntity = e.getEntity();
                String entityType = e.getEntityType().name().toUpperCase();
                if (!registerEntityList.contains(entityType) && !utils.getBlackList().contains(entityType)) {
                    utils.registerBaseEntityData(livingEntity);
                }
                double chance = random.nextDouble();
                double evolutionChance = EntityEvolution.getPlugin().getConfig().getDouble("Setting.Evolution-Chance");
                int evolutionCount = utils.getEvolutionCount(livingEntity);
                if (chance <= evolutionChance) {
                    if (!utils.getBlackList().contains(entityType)) {
                        if (EntityEvolution.getPlugin().getConfig().getBoolean("Setting.Check-Nearby-Player.Enabled")) {
                            if (utils.hasNearbyPlayer(livingEntity.getLocation()))  {
                                utils.addEvolutionCount(livingEntity, evolutionCount);
                            } else {
                                Bukkit.getPluginManager().callEvent(new EntityEvolutionEvent(livingEntity, evolutionCount, evolutionCount, false));
                            }
                        } else {
                            utils.addEvolutionCount(livingEntity, evolutionCount);
                        }
                    } else {
                        Bukkit.getPluginManager().callEvent(new EntityEvolutionEvent(livingEntity, evolutionCount, evolutionCount, false));
                    }
                } else {
                    Bukkit.getPluginManager().callEvent(new EntityEvolutionEvent(livingEntity, evolutionCount, evolutionCount, false));
                }
            }
        }
    }

    @EventHandler
    public void evolutionEntityDeath(EntityDeathEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            LivingEntity livingEntity = e.getEntity();
            PersistentDataContainer data = livingEntity.getPersistentDataContainer();
            if (data.has(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING)) {
                String countS = data.get(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING);
                int value = 0;
                if (countS != null) {
                    value = Integer.parseInt(countS);
                }
                Bukkit.getPluginManager().callEvent(new EvolutionEntityDeathEvent(livingEntity, value));
            }
        }
    }

}
