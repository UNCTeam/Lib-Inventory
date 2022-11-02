package fr.teamunc.inventory_unclib;

import fr.teamunc.base_unclib.BaseLib;
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
        if (BaseLib.IsInit()) {
            InventoryLib.getUNCInventoryController().getContainerInventory().save("inventories");
        }

        getServer().getOnlinePlayers().forEach(player -> {
            player.closeInventory();
        });
    }
}
