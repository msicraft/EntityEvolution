package com.msicraft.entityevolution.API.CustomEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvolutionEntityDeathEvent extends Event {

    private final static HandlerList handlers = new HandlerList();

    private LivingEntity livingEntity;
    private int count;

    public EvolutionEntityDeathEvent(LivingEntity livingEntity, int count) {
        this.livingEntity = livingEntity;
        this.count = count;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public int getCount() {
        return count;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
