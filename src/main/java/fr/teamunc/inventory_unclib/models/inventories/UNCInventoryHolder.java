package fr.teamunc.inventory_unclib.models.inventories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@AllArgsConstructor
public class UNCInventoryHolder implements InventoryHolder {
    @Getter
    private String key;
    @Override
    public Inventory getInventory() {
        return null;
    }
}
