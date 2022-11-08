package com.msicraft.entityevolution;

import com.msicraft.entityevolution.Command.MainCommand;
import com.msicraft.entityevolution.Command.TabComplete;
import com.msicraft.entityevolution.Data.EntityData;
import com.msicraft.entityevolution.Event.EntityDeath;
import com.msicraft.entityevolution.Event.EntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class EntityEvolution extends JavaPlugin {

    private static EntityEvolution plugin;
    protected FileConfiguration config;
    public static EntityData entityData;

    public static EntityEvolution getPlugin() {
        return plugin;
    }
    public static String getPrefix() {
        return "[Entity Evolution]";
    }

    private HashMap<String, String> vanillaMobLastData = new HashMap<>();
    public HashMap<String, String> getVanillaMobLastData() {
        return vanillaMobLastData;
    }

    public ArrayList<String> getRegisterEntityList() {
        ArrayList<String> list = new ArrayList<>();
        ConfigurationSection section = EntityEvolution.entityData.getConfig().getConfigurationSection("Entity");
        if (section != null) {
            Set<String> entityList = section.getKeys(false);
            list.addAll(entityList);
        }
        return list;
    }

    public ArrayList<String> getEvolutionAttributes() {
        ArrayList<String> list = new ArrayList<>();
        ConfigurationSection section = getPlugin().getConfig().getConfigurationSection("Evolution-Setting");
        if (section != null) {
            Set<String> attributes = section.getKeys(false);
            list.addAll(attributes);
        }
        return list;
    }

    @Override
    public void onEnable() {
        entityData = new EntityData(this);
        plugin = this;
        createFiles();
        final int configVersion = plugin.getConfig().contains("config-version", true) ? plugin.getConfig().getInt("config-version") : -1;
        if (configVersion != 1) {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + ChatColor.RED + " You are using the old config");
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + ChatColor.RED + " Created the latest config.yml after replacing the old config.yml with config_old.yml");
            replaceconfig();
            createFiles();
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + " You are using the latest version of config.yml");
        }
        final int entityConfig = entityData.getConfig().contains("config-version", true) ? entityData.getConfig().getInt("config-version") : -1;
        if (entityConfig != 1) {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + ChatColor.RED + " You are using the old entityData.yml");
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + ChatColor.RED + " Created the latest entityData.yml after replacing the old entityData.yml with entityData_old.yml");
            replaceEntityConfig();
            entityData = new EntityData(this);
        }else {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + " You are using the latest version of entityData.yml");
        }
        dataFilesReload();
        commandsRegister();
        eventsRegister();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + " Plugin Enable");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + ChatColor.RED +" Plugin Disable");
    }

    public void dataFilesReload() {
        plugin.reloadConfig();
        entityData.reloadConfig();
    }

    private void commandsRegister() {
        PluginCommand mainCommand = getServer().getPluginCommand("entityevolution");
        if (mainCommand != null) {
            mainCommand.setExecutor(new MainCommand());
            mainCommand.setTabCompleter(new TabComplete());
        }
    }

    private PluginManager pluginManager = Bukkit.getServer().getPluginManager();

    private void eventsRegister() {
        pluginManager.registerEvents(new EntityDeath(), this);
        pluginManager.registerEvents(new EntitySpawn(), this);
    }

    private void createFiles() {
        File configf = new File(getDataFolder(), "config.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        FileConfiguration config = new YamlConfiguration();

        try {
            config.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void replaceconfig() {
        File file = new File(getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        File config_old = new File(getDataFolder(),"config_old-" + dateFormat.format(date) + ".yml");
        file.renameTo(config_old);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + " Plugin replaced the old config.yml with config_old.yml and created a new config.yml");
    }

    private void replaceEntityConfig() {
        File file = new File(getDataFolder(), "entityData.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        File config_old = new File(getDataFolder(),"entityData_old-" + dateFormat.format(date) + ".yml");
        file.renameTo(config_old);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getPrefix() + " Plugin replaced the old entityData.yml with entityData_old.yml and created a new entityData.yml");
    }

}
