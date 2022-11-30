package com.msicraft.entityevolution.Inventory;

import com.msicraft.entityevolution.EntityEvolution;
import com.msicraft.entityevolution.EvolutionSkills.EvolutionSkillUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvolutionSettingInv implements InventoryHolder {

    private Inventory evolutionSettingInv;

    public HashMap<String, Integer> page = new HashMap<>();

    public HashMap<String, Integer> max_page = new HashMap<>();

    private int maxSize = EntityEvolution.getPlugin().getRegisterEntityList().size();

    public EvolutionSettingInv(Player player) {
        evolutionSettingInv = Bukkit.createInventory(player, 54, "Entity Evolution Setting");
        page_button_size();
        abilityMaxPageSize();
    }

    private List<String> normalLoreList = new ArrayList<>();
    private ArrayList<String> registerEntityList = new ArrayList<>(EntityEvolution.getPlugin().getRegisterEntityList());

    public void selectInv() {
        ItemStack setItem;
        setItem = setNormalItemStack(Material.BARRIER, ChatColor.RED + "Close", normalLoreList, "EE-Close");
        evolutionSettingInv.setItem(0, setItem);
        normalLoreList.add(ChatColor.GREEN + "Set Entity Evolution");
        setItem = setNormalItemStack(Material.BOOK, ChatColor.WHITE + "Entity Setting", normalLoreList, "EE-EntitySetting");
        evolutionSettingInv.setItem(3, setItem);
        if (!normalLoreList.isEmpty()) {
            normalLoreList.clear();
        }
        setItem = setNormalItemStack(Material.ANVIL, ChatColor.WHITE + "Equipment Evolution", normalLoreList, "EE-EquipEvolution");
        //evolutionSettingInv.setItem(5, setItem);
    }

    private EvolutionSkillUtil evolutionSkillUtil = new EvolutionSkillUtil();

    private static final int[] entityEditSlot = {19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public HashMap<String, Integer> abilityPage = new HashMap<>();
    public HashMap<String, Integer> abilityMaxPage = new HashMap<>();

    private int abilityMaxSize = evolutionSkillUtil.getAbilityList().size();

    public void settingEntityEditInv() {
        evolutionSettingInv.clear();
        normalEntityBackButton();
        ItemStack itemStack;
        itemStack = setNormalItemStack(Material.BLAZE_POWDER, ChatColor.WHITE + "Ability Edit", normalLoreList, "EE-EntityEdit-Ability");
        evolutionSettingInv.setItem(entityEditSlot[0], itemStack);
    }

    public void setEditEntity(Player player) {
        String entityName = null;
        if (EntityEvolution.getPlugin().getLastEditEntity().containsKey(player.getUniqueId())) {
            entityName = EntityEvolution.getPlugin().getLastEditEntity().get(player.getUniqueId());
        }
        if (entityName == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + " Invalid entity name");
        }
        ItemStack itemStack;
        itemStack = new ItemStack(Material.BOOK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(entityName);
        itemStack.setItemMeta(itemMeta);
        evolutionSettingInv.setItem(4, itemStack);
    }

    public void settingEntityAbilityEdit() {
        evolutionSettingInv.clear();
        abilityPageSetting();
        int page_num = 0;
        if (abilityPage.containsKey("page")) {
            page_num = abilityPage.get("page");
        }
        int max = entityEditSlot.length;
        int lastCount = page_num*max;
        int count = 0;
        ItemStack itemStack;
        normalLoreList.add(ChatColor.YELLOW + "Left Click: " + ChatColor.WHITE +  "edit");
        normalLoreList.add(ChatColor.YELLOW + "Right Click: " + ChatColor.WHITE + "clear");
        for (int a = lastCount; a<abilityMaxSize; a++) {
            int slot = entityEditSlot[count];
            String abilityName = evolutionSkillUtil.getAbilityList().get(a);
            itemStack = setNormalItemStack(Material.PAPER, abilityName, normalLoreList, abilityName);
            normalLoreList.add(ChatColor.YELLOW + "Left Click: " + ChatColor.WHITE +  "edit");
            normalLoreList.add(ChatColor.YELLOW + "Right Click: " + ChatColor.WHITE + "clear");
            evolutionSettingInv.setItem(slot, itemStack);
            count++;
            if (count >= max) {
                break;
            }
        }
    }

    private void normalEntityBackButton() {
        ItemStack itemStack;
        itemStack = setNormalItemStack(Material.BARRIER, ChatColor.RED + "Back", normalLoreList, "EE-Setting-Back");
        evolutionSettingInv.setItem(45, itemStack);
    }

    public void setEntityListInv() {
        evolutionSettingInv.clear();
        pageSettings();
        List<String> loreList = new ArrayList<>();
        loreList.add("");
        List<String> loreList2 = new ArrayList<>();
        loreList2.add(ChatColor.YELLOW + "Left Click: Edit");
        ItemStack itemStack;
        int page_num = 0;
        if (page.containsKey("page")) {
            page_num = page.get("page");
        }
        int gui_count = 0;
        int gui_max = 45;
        int lastCount = page_num*45;
        for (int a = lastCount; a<maxSize; a++) {
            String entityName = registerEntityList.get(a);
            itemStack = setNormalItemStack(Material.PAPER, entityName, loreList, entityName);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.setLore(loreList2);
                itemStack.setItemMeta(itemMeta);
                evolutionSettingInv.setItem(gui_count, itemStack);
                gui_count++;
                if (gui_count >= gui_max) {
                    break;
                }
            }
        }
        ItemStack setItem;
        setItem = setNormalItemStack(Material.BARRIER, ChatColor.RED + "Back", normalLoreList, "EE-Back");
        evolutionSettingInv.setItem(45, setItem);
    }

    private ItemStack setNormalItemStack(Material material,String itemName, List<String> loreList, String dataTag) {
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(itemName);
            itemMeta.setLore(loreList);
            if (dataTag != null) {
                PersistentDataContainer data = itemMeta.getPersistentDataContainer();
                data.set(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-SettingInv"), PersistentDataType.STRING, dataTag);
            }
        }
        itemStack.setItemMeta(itemMeta);
        if (!loreList.isEmpty()) {
            loreList.clear();
        }
        return itemStack;
    }

    private void pageSettings() {
        page_book();
        basic_button();
    }

    private void abilityPageSetting() {
        abilityPageBook();;
        abilityBasicButton();
    }

    private void abilityBasicButton() {
        ItemStack itemStack;
        itemStack = setNormalItemStack(Material.BARRIER, ChatColor.RED + "Back", normalLoreList, "EE-EntityEdit-Back");
        itemStack.setItemMeta(itemStack.getItemMeta());
        evolutionSettingInv.setItem(45, itemStack);
        itemStack = setNormalItemStack(Material.ARROW, "Previous", normalLoreList, "EE-AbilityEdit-Previous");
        itemStack.setItemMeta(itemStack.getItemMeta());
        evolutionSettingInv.setItem(48, itemStack);
        itemStack = setNormalItemStack(Material.ARROW, "Next", normalLoreList, "EE-AbilityEdit-Next");
        itemStack.setItemMeta(itemStack.getItemMeta());
        evolutionSettingInv.setItem(50, itemStack);
    }

    private void basic_button() {
        ItemStack itemStack = new ItemStack(Material.ARROW, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName("Next");
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-SettingInv"), PersistentDataType.STRING, "EE-Next");
            itemStack.setItemMeta(itemMeta);
            evolutionSettingInv.setItem(50, itemStack);
            itemMeta.setDisplayName("Previous");
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-SettingInv"), PersistentDataType.STRING, "EE-Previous");
            itemStack.setItemMeta(itemMeta);
            evolutionSettingInv.setItem(48, itemStack);
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
        evolutionSettingInv.setItem(49, itemStack);
    }

    private void page_button_size() {
        int page_count = maxSize/45;
        max_page.put("max-page", page_count);
    }

    private void abilityPageBook() {
        ItemStack itemStack = new ItemStack(Material.BOOK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String get_page = String.valueOf(abilityPage.get("page"));
        if (get_page.equals("null")) {
            get_page = "0";
        }
        int page = Integer.parseInt(get_page) + 1;
        itemMeta.setDisplayName("Page: " + page);
        itemStack.setItemMeta(itemMeta);
        evolutionSettingInv.setItem(49, itemStack);
    }

    private void abilityMaxPageSize() {
        int page_count = abilityMaxSize/entityEditSlot.length;
        abilityMaxPage.put("max-page", page_count);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return evolutionSettingInv;
    }

}
