package fr.teamunc.inventory_unclib.models.inventories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UNCInventory implements Serializable {
    private final String inventoryKey;
    private final String title;
    private final Inventory inventory;

    public static UNCInventoryBuilder builder(String key) {
        return new UNCInventoryBuilder().inventoryKey(key);
    }

}
