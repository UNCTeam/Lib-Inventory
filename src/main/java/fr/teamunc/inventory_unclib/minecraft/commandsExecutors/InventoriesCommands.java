package fr.teamunc.inventory_unclib.minecraft.commandsExecutors;

import fr.teamunc.base_unclib.BaseLib;
import fr.teamunc.base_unclib.models.inventories.UNCInventory;
import fr.teamunc.base_unclib.models.libtools.CommandsTab;
import fr.teamunc.base_unclib.utils.helpers.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InventoriesCommands extends CommandsTab implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equalsIgnoreCase("uncinventory")) {
            return false;
        }
        if (!BaseLib.IsInit()) {
            Message.Get().sendMessage("BaseLib is not initialized!", sender, true);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Usage: /uncinventory <open | list> <inventory name>");
            return true;
        }
        switch (args[0]) {
            case "list":
                sender.sendMessage("Inventories list:");
                BaseLib.getUNCInventoryController().getInventories().forEach((name, inv) -> sender.sendMessage(name));
                return true;
            case "open":
                if (sender instanceof Player) {
                    if (args.length < 2) {
                        sender.sendMessage("Usage: /uncinventory <open | list> <inventory name>");
                        return true;
                    }
                    UNCInventory inventory = BaseLib.getUNCInventoryController().getInventories().get(args[1]);
                    if (inventory == null) {
                        sender.sendMessage("Inventory not found!");
                        return true;
                    }
                    ((Player) sender).openInventory(inventory.getInventory());
                }
                return true;
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
                    BaseLib.getUNCInventoryController().getInventories().keySet().stream()
                            .collect(Collectors.toList()));
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
