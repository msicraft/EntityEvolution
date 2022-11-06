package com.msicraft.entityevolution.API.CustomEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EvolutionEntitySpawnEvent extends Event {

    private final static HandlerList handlers = new HandlerList();

    private LivingEntity livingEntity;
    private int count;
    private CreatureSpawnEvent.SpawnReason spawnReason;
    private boolean isSuccess;

    public EvolutionEntitySpawnEvent(LivingEntity livingEntity, int count, CreatureSpawnEvent.SpawnReason spawnReason, boolean isSuccess) {
        this.livingEntity = livingEntity;
        this.count = count;
        this.spawnReason = spawnReason;
        this.isSuccess = isSuccess;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public int getEvolutionCount() {
        return count;
    }

    public CreatureSpawnEvent.SpawnReason getSpawnReason() {
        return spawnReason;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
