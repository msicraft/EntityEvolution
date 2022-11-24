package com.msicraft.entityevolution.Inventory;

import com.msicraft.entityevolution.Data.Utils.EntityDataUtil;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvolutionEntityInfoInv implements InventoryHolder {

    private Inventory evolutionEntityInfoInv;

    public HashMap<String, Integer> page = new HashMap<>();

    public HashMap<String, Integer> max_page = new HashMap<>();

    private int maxSize = EntityEvolution.getPlugin().getRegisterEntityList().size();
    private ArrayList<String> registerEntityList = new ArrayList<>(EntityEvolution.getPlugin().getRegisterEntityList());

    private EntityDataUtil entityDataUtil = new EntityDataUtil();

    public EvolutionEntityInfoInv(Player player) {
        evolutionEntityInfoInv = Bukkit.createInventory(player, 54, "Evolution Entity Info");
        setEvolutionEntityList();
    }

    public void setEvolutionEntityList() {
        evolutionEntityInfoInv.clear();
        page_button_size();
        basic_button();
        page_book();
        int page_num = 0;
        if (page.containsKey("page")) {
            page_num = page.get("page");
        }
        int gui_count = 0;
        int gui_max = 45;
        int lastCount = page_num*45;
        for (int a = lastCount; a<maxSize; a++) {
            List<String> loreList = new ArrayList<>();
            String entityName = registerEntityList.get(a);
            ItemStack itemStack = new ItemStack(Material.PAPER, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) {
                Bukkit.getConsoleSender().sendMessage(EntityEvolution.getPrefix() + ChatColor.RED + " Evolution Entity List invalid itemMeta");
                break;
            }
            itemMeta.setDisplayName(entityName);
            double baseHealth = entityDataUtil.getEntityBaseHealth(entityName);
            double baseDamage = entityDataUtil.getEntityBaseDamage(entityName);
            double baseArmor = entityDataUtil.getEntityBaseArmor(entityName);
            double baseArmorToughness = entityDataUtil.getEntityBaseArmorToughness(entityName);
            int evolutionCount = entityDataUtil.getEntityEvolutionCount(entityName);
            String tag = entityDataUtil.getEntityTag(entityName);
            loreList.add(ChatColor.GREEN + "Name: " + ChatColor.WHITE + entityName);
            loreList.add(ChatColor.GREEN + "Type: " + ChatColor.WHITE + tag);
            loreList.add("");
            loreList.add(ChatColor.GREEN + "Base Health: " + ChatColor.WHITE + baseHealth);
            loreList.add(ChatColor.GREEN + "Base Damage: " + ChatColor.WHITE + baseDamage);
            loreList.add(ChatColor.GREEN + "Base Armor: " + ChatColor.WHITE + baseArmor);
            loreList.add(ChatColor.GREEN + "Base ArmorToughness: " + ChatColor.WHITE + baseArmorToughness);
            loreList.add("");
            loreList.add(ChatColor.GREEN + "Evolution Count: " + ChatColor.WHITE + evolutionCount);
            itemMeta.setLore(loreList);
            itemStack.setItemMeta(itemMeta);
            evolutionEntityInfoInv.setItem(gui_count, itemStack);
            gui_count++;
            if (gui_count >= gui_max) {
                break;
            }
        }
    }

    private void basic_button() {
        ItemStack itemStack = new ItemStack(Material.ARROW, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName("Next");
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Info"), PersistentDataType.STRING, "EE-Next");
            itemStack.setItemMeta(itemMeta);
            evolutionEntityInfoInv.setItem(50, itemStack);
            itemMeta.setDisplayName("Previous");
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Info"), PersistentDataType.STRING, "EE-Previous");
            itemStack.setItemMeta(itemMeta);
            evolutionEntityInfoInv.setItem(48, itemStack);
        }
    }

    private void page_book() {
        ItemStack itemStack = new ItemStack(Material.BOOK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String get_page = String.valueOf(page.get("page"));
        if (get_page.equals("null")) {
            get_page = "0";
        }
        int page = Integer.parseInt(get_page) + 1;
        itemMeta.setDisplayName("Page: " + page);
        itemStack.setItemMeta(itemMeta);
        evolutionEntityInfoInv.setItem(49, itemStack);
    }

    private void page_button_size() {
        int page_count = maxSize/45;
        max_page.put("max-page", page_count);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return evolutionEntityInfoInv;
    }

}
