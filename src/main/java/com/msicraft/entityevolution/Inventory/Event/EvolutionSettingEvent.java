package com.msicraft.entityevolution.Inventory.Event;

import com.msicraft.entityevolution.EntityEvolution;
import com.msicraft.entityevolution.EvolutionSkills.EvolutionSkillUtil;
import com.msicraft.entityevolution.Inventory.EvolutionSettingInv;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;

public class EvolutionSettingEvent implements Listener {

    public HashMap<String, Integer> page_count = new HashMap<>();
    public HashMap<String, Integer> abilityPageCount = new HashMap<>();

    private EvolutionSkillUtil evolutionSkillUtil = new EvolutionSkillUtil();

    @EventHandler
    public void onEvolutionSettingClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("Entity Evolution Setting")) {
            if (e.getInventory().getHolder() == e.getWhoClicked()) {
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                if (e.getCurrentItem() == null) {
                    return;
                }
                EvolutionSettingInv evolutionSettingInv = new EvolutionSettingInv(player);
                ItemStack itemStack = e.getCurrentItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                int max = evolutionSettingInv.max_page.get("max-page");
                int abilityMax = evolutionSettingInv.abilityMaxPage.get("max-page");
                if (!page_count.containsKey("page")) {
                    page_count.put("page", 0);
                }
                if (!abilityPageCount.containsKey("page")) {
                    abilityPageCount.put("page", 0);
                }
                if (itemMeta != null) {
                    PersistentDataContainer data = itemMeta.getPersistentDataContainer();
                    if (data.has(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-SettingInv"), PersistentDataType.STRING)) {
                        String dataArg = data.get(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-SettingInv"), PersistentDataType.STRING);
                        if (dataArg != null && e.isLeftClick()) {
                            switch (dataArg) {
                                case "EE-Next" -> {
                                    int current_page = page_count.get("page");
                                    int next_page = current_page + 1;
                                    if (next_page > max) {
                                        next_page = 0;
                                    }
                                    page_count.put("page", next_page);
                                    evolutionSettingInv.page.put("page", next_page);
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.setEntityListInv();
                                }
                                case "EE-Previous" -> {
                                    int current_page = page_count.get("page");
                                    int next_page = current_page - 1;
                                    if (next_page < 0) {
                                        next_page = max;
                                    }
                                    page_count.put("page", next_page);
                                    evolutionSettingInv.page.put("page", next_page);
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.setEntityListInv();
                                }
                                case "EE-Close" -> player.closeInventory();
                                case "EE-Back" -> {
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.selectInv();
                                }
                                case "EE-EntitySetting", "EE-Setting-Back" -> {
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.setEntityListInv();
                                }
                                case "EE-EntityEdit-Back" -> {
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.settingEntityEditInv();
                                    evolutionSettingInv.setEditEntity(player);
                                }
                                case "EE-EntityEdit-Ability" -> {
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.settingEntityAbilityEdit();
                                    evolutionSettingInv.setEditEntity(player);
                                }
                                case "EE-AbilityEdit-Next" -> {
                                    int currentPage = abilityPageCount.get("page");
                                    int nextPage = currentPage + 1;
                                    if (nextPage > abilityMax) {
                                        nextPage = abilityMax;
                                    }
                                    abilityPageCount.put("page", nextPage);
                                    evolutionSettingInv.abilityPage.put("page", nextPage);
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.settingEntityAbilityEdit();
                                    evolutionSettingInv.setEditEntity(player);
                                }
                                case "EE-AbilityEdit-Previous" -> {
                                    int currentPage = abilityPageCount.get("page");
                                    int nextPage = currentPage - 1;
                                    if (nextPage < 0) {
                                        nextPage = abilityMax;
                                    }
                                    abilityPageCount.put("page", nextPage);
                                    evolutionSettingInv.abilityPage.put("page", nextPage);
                                    player.closeInventory();
                                    player.openInventory(evolutionSettingInv.getInventory());
                                    evolutionSettingInv.settingEntityAbilityEdit();
                                    evolutionSettingInv.setEditEntity(player);
                                }
                            }
                            if (EntityEvolution.getPlugin().getRegisterEntityList().contains(dataArg)) {
                                player.closeInventory();
                                player.openInventory(evolutionSettingInv.getInventory());
                                EntityEvolution.getPlugin().getLastEditEntity().put(player.getUniqueId(), dataArg);
                                evolutionSettingInv.settingEntityEditInv();
                                evolutionSettingInv.setEditEntity(player);
                            }
                            if (evolutionSkillUtil.getAbilityList().contains(dataArg)) {
                                player.sendMessage("test: " + dataArg);
                            }
                        }
                    }
                }
            }
        }
    }

}
