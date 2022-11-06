package com.msicraft.entityevolution.API.CustomEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityEvolutionEvent extends Event {

    private final static HandlerList handlers = new HandlerList();

    private LivingEntity livingEntity;
    private int beforeCount;
    private int afterCount;
    private boolean isSuccess;

    public EntityEvolutionEvent(LivingEntity livingEntity, int beforeCount, int afterCount, boolean isSuccess) {
        this.livingEntity = livingEntity;
        this.beforeCount = beforeCount;
        this.afterCount = afterCount;
        this.isSuccess = isSuccess;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public int getBeforeCount() {
        return beforeCount;
    }

    public int getAfterCount() {
        return afterCount;
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
