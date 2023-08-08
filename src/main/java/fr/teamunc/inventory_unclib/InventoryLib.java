package fr.teamunc.inventory_unclib;

import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.inventory_unclib.controllers.UNCInventoryController;
import fr.teamunc.inventory_unclib.minecraft.commandsExecutors.InventoriesCommands;
import fr.teamunc.inventory_unclib.minecraft.eventlisteners.InventoryListener;
import fr.teamunc.inventory_unclib.models.inventories.UNCInventoryContainer;
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
    private static UNCInventoryController inventoryController;

    private InventoryLib() {}

    public static void init(JavaPlugin plugin) {
        InventoryLib.plugin = plugin;

        // init json entities
        UNCEntitiesContainer.init(plugin.getDataFolder());

        inventoryController = new UNCInventoryController(initInventoryContainer());

        // register commands
        initCommands();
    }

    public static boolean isInit() {
        return plugin != null;
    }

    private static void initCommands() {
        PluginCommand inventoryCommand = plugin.getCommand("uncinventory");
        if (inventoryCommand != null) {
            inventoryCommand.setExecutor(new InventoriesCommands());
            inventoryCommand.setTabCompleter(new InventoriesCommands());
        }
    }

    private static UNCInventoryContainer initInventoryContainer() {
        UNCInventoryContainer res;

        try {
            res = UNCEntitiesContainer.loadContainer("inventories", UNCInventoryContainer.class);
        } catch (Exception e) {
            plugin.getLogger().info("Creating new inventory container file");
            res = new UNCInventoryContainer();
        }
        return res;
    }


    public static void initGameListener(Inventory_UNCLib inventoryUncLib) {
        inventoryUncLib.getServer().getPluginManager().registerEvents(new InventoryListener(), inventoryUncLib);
    }

    public static void save() {
        inventoryController.save();
    }
}
