package fr.teamunc.inventory_unclib.controllers;

import fr.teamunc.base_unclib.controllers.IUNCController;
import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.inventory_unclib.InventoryLib;
import fr.teamunc.inventory_unclib.Inventory_UNCLib;
import fr.teamunc.inventory_unclib.models.inventories.UNCInventoryContainer;
import fr.teamunc.inventory_unclib.models.inventories.UNCInventoryType;
import fr.teamunc.base_unclib.utils.helpers.Message;
import lombok.Getter;

import java.util.HashMap;

public class UNCInventoryController implements IUNCController {
    /** Liste des inventaires existant
     -> Cela permet de gérer les actions quand on click dans un inventaire
     -> Ce type d'inventaire peut s'ouvrir facilement avec la méthode openInventory de la classe UNCInventory
     */
    @Getter
    private HashMap<String, UNCInventoryType> inventories = new HashMap<>();
    /**
     * Contient la liste des inventaires qu'on va sauvegarder
     * -> Identifié par un UUID
     * -> Ils peuvent contenir des items
     * -> Ont les sauvegarde et récupère même après reboot
     * -> L'interaction avec ces inventaires peut toujours être gérée grâce à leur title
     */
    private UNCInventoryContainer inventoriesContainer;


    public UNCInventoryController(UNCInventoryContainer inventoriesContainer) {
        this.inventoriesContainer = inventoriesContainer;
    }

    /**
     *
     * @param inventory
     */
    public void registerInventory(UNCInventoryType... inventory) {
        for (UNCInventoryType inv : inventory) {
            if(this.inventories.containsKey(inv.getKey())) {
                Message.Get().broadcastMessageToConsole("L'inventaire " + inv.getKey() + " existe déjà");
                continue;
            }
            this.inventories.put(inv.getKey(), inv);
        }
    }

    public void removeInventoryType(String key) {
        inventories.remove(key);
    }

    public void removeInventoryType(UNCInventoryType inventory) {
        inventories.remove(inventory.getKey());
    }

    @Override
    public void save() {
        inventoriesContainer.save("inventories");
    }
}
