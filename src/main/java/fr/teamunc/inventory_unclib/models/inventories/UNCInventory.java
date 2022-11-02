package fr.teamunc.inventory_unclib.models.inventories;

import fr.teamunc.base_unclib.models.tickloops.IUNCInventoryCloseAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class UNCInventory {

    /* The inventory key */
    private String key;
    /* Title of the inventory */
    private String title;
    /* Size of the inventory */

    private Integer size;
    /* Action when the inventory is closed */
    private IUNCInventoryCloseAction closeAction;
    /* Items that are fixed in the inventory */
    private List<UNCItemMenu> fixedItems;
    /* slots thant we want to cancel click in actionClick */
    private List<CancelSlot> cancelSlotList;
    private Boolean isShiftClickCancel = false;
    private Boolean isDropClickCancel = false;

    @Builder
    public UNCInventory(String key, String title, Integer size, IUNCInventoryCloseAction closeAction,
                        List<UNCItemMenu> fixedItems, Boolean isShiftClickCancel, Boolean isDropClickCancel,
                        CancelSlot... cancelSlots) {
        this.key = key;
        this.title = title;
        this.size = size;
        this.closeAction = closeAction;
        this.fixedItems = fixedItems;
        this.isShiftClickCancel = isShiftClickCancel;
        this.isDropClickCancel = isDropClickCancel;
        this.cancelSlotList = Arrays.asList(cancelSlots);
    }

    public static UNCInventoryBuilder builder(String key, String title, Integer size) {
        return new UNCInventoryBuilder().key(key).title(title).size(size);
    }

    private void initializeFixedItems(Inventory inventory) {
        for (UNCItemMenu itemMenu : this.fixedItems) {
            inventory.setItem(itemMenu.getSlot(), itemMenu.getItemStack());
        }
    }

    public Inventory getInventory() {
        // Creation de l'inventaire
        Inventory inventory = Bukkit.createInventory(new UNCInventoryHolder(key), size, title);

        // Initialisation des items fixes
        this.initializeFixedItems(inventory);
        return inventory;
    }

    public void openInventory(Player player) {

        player.openInventory(getInventory());
    }
}
