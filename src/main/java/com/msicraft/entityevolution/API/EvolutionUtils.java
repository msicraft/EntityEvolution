package com.msicraft.entityevolution.API;

import com.msicraft.entityevolution.API.CustomEvent.EntityEvolutionEvent;
import com.msicraft.entityevolution.AttributeUtils;
import com.msicraft.entityevolution.EntityEvolution;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class EvolutionUtils {

    private AttributeUtils attributeUtils = new AttributeUtils();

    public ArrayList<String> getBlackList() {
        ArrayList<String> list = new ArrayList<>();
        for (String a : EntityEvolution.getPlugin().getConfig().getStringList("Setting.BlackList")) {
            list.add(a.toUpperCase());
        }
        return list;
    }

    public boolean hasNearbyPlayer(Location location) {
        boolean check = false;
        World world = location.getWorld();
        if (world != null) {
            double value = EntityEvolution.getPlugin().getConfig().getDouble("Setting.Check-Nearby-Player.Radius");
            for (Entity entity : world.getNearbyEntities(location, value, value, value)) {
                if (entity instanceof Player) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public void registerBaseEntityData(LivingEntity livingEntity, String tag) {
        String type = livingEntity.getType().toString().toUpperCase();
        if (livingEntity.getCustomName() != null) {
            type = livingEntity.getCustomName().toUpperCase().replaceAll("\\s", "_");
        }
        double baseHealth = attributeUtils.getBaseHealth(livingEntity);
        double baseDamage = attributeUtils.getBaseDamage(livingEntity);
        double baseArmor = attributeUtils.getBaseArmor(livingEntity);
        double baseArmorToughness = attributeUtils.getBaseArmorToughness(livingEntity);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseHealth", baseHealth);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseDamage", baseDamage);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseArmor", baseArmor);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".BaseArmorToughness", baseArmorToughness);
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".Tag", tag);
        EntityEvolution.entityData.saveConfig();
    }

    public void addEvolutionCount(LivingEntity livingEntity, int count) {
        String type = livingEntity.getType().toString().toUpperCase();
        if (livingEntity.getCustomName() != null) {
            type = livingEntity.getCustomName().toUpperCase().replaceAll("\\s", "_");
        }
        int nextCount = count + 1;
        EntityEvolution.entityData.getConfig().set("Entity." + type + ".Count", nextCount);
        EntityEvolution.entityData.saveConfig();
        Bukkit.getPluginManager().callEvent(new EntityEvolutionEvent(livingEntity, count, nextCount, true));
    }

    public int getEvolutionCount(LivingEntity livingEntity) {
        String type = livingEntity.getType().toString().toUpperCase();
        if (livingEntity.getCustomName() != null) {
            type = livingEntity.getCustomName().toUpperCase().replaceAll("\\s", "_");
        }
        return EntityEvolution.entityData.getConfig().contains("Entity." + type + ".Count") ? EntityEvolution.entityData.getConfig().getInt("Entity." + type + ".Count") : 0;
    }

    public void setAttribute(LivingEntity livingEntity, String attributeName, String type, int evolutionCount, double value, boolean check) {
        Bukkit.getScheduler().runTaskLater(EntityEvolution.getPlugin(), ()-> {
            if (check) {
                AttributeInstance instance = livingEntity.getAttribute(Attribute.valueOf(attributeName));
                if (instance != null) {
                    double cal = value * evolutionCount;
                    double roundCal = (Math.round(cal*100)/100.0);
                    AttributeModifier modifier = new AttributeModifier(UUID.randomUUID().toString(), roundCal, AttributeModifier.Operation.valueOf(type));
                    instance.addModifier(modifier);
                    //Bukkit.getServer().broadcastMessage("test2: " + attributeName + " | " + type + " | " + instance.getValue() + " Tag: " + livingEntity.getPersistentDataContainer().get(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING));
                }
            }
        }, 2L);
    }

    public void setEvolutionTag(LivingEntity livingEntity, int count) {
        PersistentDataContainer data = livingEntity.getPersistentDataContainer();
        if (!data.has(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(EntityEvolution.getPlugin(), "EntityEvolution-Count-Tag"), PersistentDataType.STRING, String.valueOf(count));
        }
    }

    private final ArrayList<String> evolutionAttribute = new ArrayList<>(Arrays.asList("GENERIC_MAX_HEALTH", "GENERIC_ATTACK_DAMAGE", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS",
            "GENERIC_MOVEMENT_SPEED", "GENERIC_KNOCKBACK_RESISTANCE"));
    private final ArrayList<String> mapAttributeList = new ArrayList<>(Arrays.asList("Health", "Damage", "Armor", "ArmorToughness", "MovementSpeed", "KnockbackResistance"));
    private final ArrayList<String> attributeList = new ArrayList<>();

    public void setVanillaEntityData(LivingEntity livingEntity) {
        String type = livingEntity.getType().name().toUpperCase();
        if (livingEntity.getCustomName() != null) {
            type = livingEntity.getCustomName().toUpperCase().replaceAll("\\s", "_");
        }
        int max = evolutionAttribute.size();
        for (int a = 0; a<max; a++) {
            String attributeName = evolutionAttribute.get(a);
            double value = getAttributeValue(livingEntity, attributeName);
            String keyValue = getVanillaDataValue(value, a);
            attributeList.add(keyValue);
        }
        String keyValue = attributeList.toString().replaceAll("\\s", "");
        keyValue = getEncoded(keyValue);
        EntityEvolution.getPlugin().getVanillaMobLastData().put(type, keyValue);
        if (!attributeList.isEmpty()) {
            attributeList.clear();
        }
    }

    private double getAttributeValue(LivingEntity livingEntity, String attributeName) {
        double value = -1;
        AttributeInstance instance = livingEntity.getAttribute(Attribute.valueOf(attributeName));
        if (instance != null) {
            value = Math.round(instance.getValue() * 100) / 100.0;
        }
        return value;
    }

    private String getVanillaDataValue(double value, int count) {
        String type;
        if (value == -1) {
            type = mapAttributeList.get(count) + "=null";
        } else {
            type = mapAttributeList.get(count) + "=" + value;
        }
        return type;
    }

    private String getEncoded(String a) {
        String encodedMessage;
        String encoded = a;
        for (int count = 0; count<3; count++) {
           encoded = applyEncoded(encoded);
        }
        encodedMessage = encoded;
        return encodedMessage;
    }

    private String applyEncoded(String a) {
        byte[] message = a.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(message);
    }

    private String getDecoded(String a) {
        String decodedMessage;
        String decoded = a;
        for (int count = 0; count<3; count++) {
            decoded = applyDecoded(decoded);
        }
        decodedMessage = decoded;
        return decodedMessage;
    }

    private String applyDecoded(String a) {
        byte[] message = Base64.getDecoder().decode(a);
        return new String(message, StandardCharsets.UTF_8);
    }

}
