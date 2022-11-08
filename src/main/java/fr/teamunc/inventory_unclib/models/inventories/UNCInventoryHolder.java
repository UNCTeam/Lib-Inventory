package fr.teamunc.inventory_unclib.models.inventories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class UNCInventoryHolder implements InventoryHolder {
    private String key;
    private final HashMap<String, Object> additionalInformation;

    public UNCInventoryHolder(String key) {
        this.key = key;
        this.additionalInformation = additionalInformation;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }


    public <T> T getAdditionalInformation(String key, Class<T> clazz) throws ClassCastException {
        return clazz.cast(this.additionalInformation.get(key));
    }

    public void setAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    }
}
