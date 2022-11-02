package fr.teamunc.inventory_unclib;

import fr.teamunc.base_unclib.controllers.UNCInventoryController;
import fr.teamunc.base_unclib.minecraft.comandsExecutors.InventoriesCommands;
import fr.teamunc.base_unclib.minecraft.eventlisteners.InventoryListener;
import fr.teamunc.base_unclib.models.inventories.UNCContainerInventory;
import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Behaviors of the Base_UNCLib :
 * - init with the plugin
 */
public class InventoryLib {
    @Getter
    private static JavaPlugin plugin;
    @Getter
    private static UNCInventoryController UNCInventoryController;

    public static void init(JavaPlugin plugin) {
        InventoryLib.plugin = plugin;

        UNCInventoryController = new UNCInventoryController(initInventoryContainer());

        // register commands
        initCommands();
    }

    public static boolean IsInit() {
        return plugin != null;
    }

    private static void initCommands() {
        PluginCommand inventoryCommand = plugin.getCommand("uncinventory");
        if (inventoryCommand != null) {
            inventoryCommand.setExecutor(new InventoriesCommands());
            inventoryCommand.setTabCompleter(new InventoriesCommands());
        }
    }

    private static UNCContainerInventory initInventoryContainer() {
        UNCEntitiesContainer.init(plugin.getDataFolder());
        UNCContainerInventory res;

        try {
            res = UNCEntitiesContainer.loadContainer("inventories", UNCContainerInventory.class);
        } catch (Exception e) {
            plugin.getLogger().info("Creating new inventory container file");
            res = new UNCContainerInventory();
        }
        return res;
    }

    public static void initGameListener(Inventory_UNCLib inventory_uncLib) {
        inventory_uncLib.getServer().getPluginManager().registerEvents(new InventoryListener(), inventory_uncLib);
    }
}
