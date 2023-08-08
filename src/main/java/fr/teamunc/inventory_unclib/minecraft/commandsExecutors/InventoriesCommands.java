package fr.teamunc.inventory_unclib.minecraft.commandsExecutors;

import fr.teamunc.base_unclib.models.libtools.CommandsTab;
import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.inventory_unclib.InventoryLib;
import fr.teamunc.inventory_unclib.models.inventories.UNCInventoryType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InventoriesCommands extends CommandsTab implements CommandExecutor {

    private static final String USAGE_MESSAGE = "Usage: /uncinventory <open | list> <inventory name>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equalsIgnoreCase("uncinventory")) {
            return false;
        }
        if (!InventoryLib.isInit()) {
            Message.Get().sendMessage("InventoryLib is not initialized!", sender, true);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(USAGE_MESSAGE);
            return true;
        }
        switch (args[0]) {
            case "list":
                sender.sendMessage("Inventories list:");
                InventoryLib.getInventoryController().getInventories().forEach((name, inv) -> sender.sendMessage(name));
                return true;
            case "open":
                if (sender instanceof Player) {
                    if (args.length < 2) {
                        sender.sendMessage(USAGE_MESSAGE);
                        return true;
                    }
                    UNCInventoryType inventory = InventoryLib.getInventoryController().getInventories().get(args[1]);
                    if (inventory == null) {
                        sender.sendMessage("Inventory not found!");
                        return true;
                    }
                    ((Player) sender).openInventory(inventory.getInventory());
                }
                return true;
            default:
                sender.sendMessage(USAGE_MESSAGE);
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> result;
        if (args[0].equalsIgnoreCase("open")) {
            result = checkAllTab(
                    args,
                    Arrays.asList("open", "list"),
                    new ArrayList<>(InventoryLib.getInventoryController().getInventories().keySet()));
        } else {
            result = checkAllTab(
                    args,
                    Arrays.asList("open", "list"));
        }


        //sort the list
        Collections.sort(result);
        return result;
    }
}
