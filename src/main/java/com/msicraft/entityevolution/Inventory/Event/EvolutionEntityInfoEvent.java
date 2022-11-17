package com.msicraft.entityevolution.Inventory.Event;

import com.msicraft.entityevolution.EntityEvolution;
import com.msicraft.entityevolution.Inventory.EvolutionEntityInfoInv;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class EvolutionEntityInfoEvent implements Listener {

    public HashMap<String, Integer> page_count = new HashMap<>();

    @EventHandler
    public void onEvolutionEntityInfoInvClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("Evolution Entity Info")) {
            if (e.getInventory().getHolder() == e.getWhoClicked()) {
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                if (e.getCurrentItem() == null) {
                    return;
                }
                EvolutionEntityInfoInv evolutionEntityInfoInv = new EvolutionEntityInfoInv(player);
                ItemStack itemStack = e.getCurrentItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                int max = evolutionEntityInfoInv.max_page.get("max-page");
                if (!page_count.containsKey("page")) {
                    page_count.put("page", 0);
                }
                if (itemMeta != null) {
                    PersistentDataContainer data = itemMeta.getPersistentDataContainer();
                    if (data.has(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Info"), PersistentDataType.STRING)) {
                        String dataArg = data.get(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Info"), PersistentDataType.STRING);
                        if (dataArg != null && e.isLeftClick() && itemStack.getType() == Material.ARROW) {
                            switch (dataArg) {
                                case "EE-Next" -> {
                                    int current_page = page_count.get("page");
                                    int next_page = current_page + 1;
                                    if (next_page > max) {
                                        next_page = 0;
                                    }
                                    page_count.put("page", next_page);
                                    evolutionEntityInfoInv.page.put("page", next_page);
                                }
                                case "EE-Previous" -> {
                                    int current_page = page_count.get("page");
                                    int next_page = current_page - 1;
                                    if (next_page < 0) {
                                        next_page = max;
                                    }
                                    page_count.put("page", next_page);
                                    evolutionEntityInfoInv.page.put("page", next_page);
                                }
                            }
                            player.closeInventory();
                            player.openInventory(evolutionEntityInfoInv.getInventory());
                            evolutionEntityInfoInv.setEvolutionEntityList();
                        }
                    }
                }
            }
        }
    }

}
