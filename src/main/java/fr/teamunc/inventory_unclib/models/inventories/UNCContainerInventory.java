package fr.teamunc.inventory_unclib.models.inventories;

import fr.teamunc.base_unclib.models.inventories.UNCPersistantInventory;
import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class UNCContainerInventory extends UNCEntitiesContainer {
    private HashMap<UUID, UNCPersistantInventory> inventories = new HashMap<>();
}
