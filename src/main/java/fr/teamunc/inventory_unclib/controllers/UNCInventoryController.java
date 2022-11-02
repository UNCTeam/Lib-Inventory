package fr.teamunc.base_unclib.controllers;

import fr.teamunc.base_unclib.models.inventories.UNCContainerInventory;
import fr.teamunc.base_unclib.models.inventories.UNCInventory;
import fr.teamunc.base_unclib.models.inventories.UNCPersistantInventory;
import fr.teamunc.base_unclib.utils.helpers.Message;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class UNCInventoryController {

    public UNCInventoryController(UNCContainerInventory containerInventory) {
        this.containerInventory = containerInventory;
    }

    /** Liste des inventaires existant
        -> Cela permet de gérer les actions quand on click dans un inventaire
        -> Ce type d'inventaire peut s'ouvrir facilement avec la méthode openInventory de la classe UNCInventory
     */
    @Getter
    private HashMap<String, UNCInventory> inventories = new HashMap<>();
    /**
     * Contient la liste des inventaires qu'on va sauvegarder
     * -> Identifié par un UUID
     * -> Ils peuvent contenir des items
     * -> Ont les sauvegarde et récupère même après reboot
     * -> L'interaction avec ces inventaires peut toujours être gérée grâce à leur title
     */
    @Getter
    private UNCContainerInventory containerInventory;

    /**
     *
     * @param inventory
     */
    public void registerInventory(UNCInventory... inventory) {
        for (UNCInventory inv : inventory) {
            if(this.inventories.containsKey(inv.getKey())) {
                Message.Get().broadcastMessageToConsole("L'inventaire " + inv.getKey() + " existe déjà");
                continue;
            }
            this.inventories.put(inv.getKey(), inv);
        }
    }

    public void removeInventory(String key) {
        inventories.remove(key);
    }

    public void removeInventory(UNCInventory inventory) {
        inventories.remove(inventory.getKey());
    }

    public UUID registerPersistantInventory(String uncInventoryKey) {
        return this.registerPersistantInventory(this.inventories.get(uncInventoryKey));
    }

    public UNCPersistantInventory getPersistantInventory(UUID uuid) {
        return this.containerInventory.getInventories().get(uuid);
    }

    /**
     * Enregistre un nouvel inventaire à sauvegarder
     * @param inventory Inventaire à register
     * @return L'UUID de l'inventaire
     */
    public UUID registerPersistantInventory(UNCInventory inventory) {
        UUID uuid = UUID.randomUUID();
        containerInventory.getInventories().put(uuid, new UNCPersistantInventory(inventory.getKey(),
                inventory.getTitle(), uuid, inventory.getInventory()));
        return uuid;
    }

    /**
     * Supprime un inventaire à sauvegarder
     * @param uuid
     */
    public void removePersistantInventory(UUID uuid) {
        containerInventory.getInventories().remove(uuid);
    }

    /**
     * Récupère un inventaire à sauvegarder et l'ouvre pour un joueur
     * @param uuid UUID de l'inventaire
     * @param player Joueur qui va ouvrir l'inventaire
     */
    public void openPersistantInventory(String uuid, Player player) {
        Inventory inventory = containerInventory.getInventories().get(UUID.fromString(uuid)).getInventory();
        player.openInventory(inventory);
    }
}
