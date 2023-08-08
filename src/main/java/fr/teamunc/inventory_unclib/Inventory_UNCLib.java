package fr.teamunc.inventory_unclib;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;

public final class Inventory_UNCLib extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        InventoryLib.initGameListener(this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (InventoryLib.isInit()) {
            InventoryLib.save();
        }

        getServer().getOnlinePlayers().forEach(HumanEntity::closeInventory);
    }
}
