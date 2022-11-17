package com.msicraft.entityevolution.Data.Tasks;

import com.msicraft.entityevolution.Data.Utils.EntityDataUtil;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityDataTask extends BukkitRunnable {

    private EntityDataUtil entityDataUtil = new EntityDataUtil();

    EntityEvolution plugin;

    public EntityDataTask(EntityEvolution plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        entityDataUtil.saveHashMapToYaml();
    }

}
