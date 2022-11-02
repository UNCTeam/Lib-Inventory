package fr.teamunc.inventory_unclib.models.inventories;

import fr.teamunc.base_unclib.models.tickloops.IUNCInventoryAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public class UNCItemMenu {
    private Integer slot;
    private ItemStack itemStack;

    private IUNCInventoryAction action;

    public UNCItemMenu(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
