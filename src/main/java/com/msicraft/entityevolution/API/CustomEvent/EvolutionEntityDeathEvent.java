package com.msicraft.entityevolution.API.CustomEvent;

import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvolutionEntityDeathEvent extends Event {

    private final static HandlerList handlers = new HandlerList();

    private EntityType entityType;
    private int count;

    public EvolutionEntityDeathEvent(EntityType entityType, int count) {
        this.entityType = entityType;
        this.count = count;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
