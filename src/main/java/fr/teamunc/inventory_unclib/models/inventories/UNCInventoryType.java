package fr.teamunc.inventory_unclib.models.inventories;

import fr.teamunc.base_unclib.models.tickloops.IUNCInventoryAction;
import fr.teamunc.base_unclib.models.tickloops.IUNCInventoryCloseAction;
import fr.teamunc.inventory_unclib.utils.InventoryConstantes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UNCInventoryType {
    private String key;
    private String title;

    private Integer size;
    private IUNCInventoryCloseAction closeAction;
    private HashMap<Integer, ItemStack> fixedItems;
    private HashMap<Integer, IUNCInventoryAction> actions;
    private List<Integer> cancelSlots;
    public static class UNCInventoryTypeBuilder {

        public UNCInventoryTypeBuilder cancelSlot(Integer startSlot, Integer endSlot) {
            for (int i = startSlot; i <= endSlot; i++) {
                this.cancelSlots.add(i);
            }
            return this;
        }

        public UNCInventoryTypeBuilder cancelSlot(ArrayList<Integer> cancelSlots) {
            this.cancelSlots.addAll(cancelSlots);
            return this;
        }

        public UNCInventoryTypeBuilder fixedItem(Integer slot, ItemStack itemStack) {
            this.fixedItems.put(slot, itemStack);
            return this;
        }

        public UNCInventoryTypeBuilder fixedItem(Integer slot, ItemStack itemStack, IUNCInventoryAction action) {
            this.fixedItems.put(slot, itemStack);
            this.actions.put(slot, action);
            return this;
        }

        public UNCInventoryTypeBuilder action(Integer slot, IUNCInventoryAction action) {
            this.actions.put(slot, action);
            return this;
        }

        public UNCInventoryTypeBuilder customTitle(String titleInterfaceID) {
            this.title = InventoryConstantes.INVENTORY_CUSTOM_TITLE + titleInterfaceID;
            return this;
        }


    }

    public static UNCInventoryTypeBuilder builder(String key, Integer size) {
        return new UNCInventoryTypeBuilder().key(key).size(size);
    }

    private void initializeFixedItems(Inventory inventory) {
        for (Integer keySlot : this.fixedItems.keySet()) {
            inventory.setItem(keySlot, fixedItems.get(keySlot));
        }
    }

    public Inventory getInventory() {
        // Creation de l'inventaire
        Inventory inventory = Bukkit.createInventory(new UNCInventoryHolder(key), size, title);

        // Initialisation des items fixes
        this.initializeFixedItems(inventory);
        return inventory;
    }

    public void getInventoryInstance() {

    }
}
