package fr.teamunc.inventory_unclib.minecraft.eventlisteners;

import fr.teamunc.base_unclib.BaseLib;
import fr.teamunc.base_unclib.models.inventories.CancelSlot;
import fr.teamunc.base_unclib.models.inventories.UNCInventory;
import fr.teamunc.base_unclib.models.inventories.UNCInventoryHolder;
import fr.teamunc.base_unclib.models.inventories.UNCItemMenu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof UNCInventoryHolder) {
            Bukkit.broadcastMessage("instance of UNCINVENTORYHOLDER");
            UNCInventoryHolder holder = (UNCInventoryHolder) event.getInventory().getHolder();
            UNCInventory inventory = BaseLib.getUNCInventoryController().getInventories().get(holder.getKey());
            if (inventory == null) {
                Bukkit.broadcastMessage("Inventory not found!");
                return;
            }
            if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) {
                event.setCancelled(true);
                return;
            }
            /** Détermine si le click est cancel */
            for (CancelSlot cancelSlot : inventory.getCancelSlotList()) {
                Bukkit.broadcastMessage("cancels : " + cancelSlot.getSlots().toString());
                if (cancelSlot.getSlots().contains(event.getRawSlot())) {
                    Bukkit.broadcastMessage("cancel!");
                    event.setCancelled(true);
                }
            }
            /** Détermine si click doit effectuer une action */
            UNCItemMenu item = inventory.getFixedItems().stream()
                    .filter(i -> i.getSlot() == event.getRawSlot())
                    .findFirst()
                    .orElse(null);
            // On lance l'action associé au slot si elle existe
            if (item != null && item.getAction() != null) {
                item.getAction().handleEvent(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof UNCInventoryHolder) {
            UNCInventoryHolder holder = (UNCInventoryHolder) event.getInventory().getHolder();
            UNCInventory inventory = BaseLib.getUNCInventoryController().getInventories().get(holder.getKey());
            if (inventory != null && inventory.getCloseAction() != null) {
                inventory.getCloseAction().handleEvent(event);
            }
        }
    }
}
