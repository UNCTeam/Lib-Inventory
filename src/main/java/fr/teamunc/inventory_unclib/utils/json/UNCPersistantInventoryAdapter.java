package fr.teamunc.inventory_unclib.utils.json;

import com.google.gson.*;
import fr.teamunc.base_unclib.models.inventories.UNCInventoryHolder;
import fr.teamunc.base_unclib.models.inventories.UNCPersistantInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

// TODO : Tester si ça fonctionne
public class UNCPersistantInventoryAdapter implements JsonSerializer<UNCPersistantInventory>, JsonDeserializer<UNCPersistantInventory> {

    /**
     * A method to serialize an {@link ItemStack} array to Base64 String.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     * @throws IllegalStateException
     */
    public String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                if (item != null) {
                    dataOutput.writeObject(item.serialize());
                } else {
                    dataOutput.writeObject(null);
                }
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException
     */
    public ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int Index = 0; Index < items.length; Index++) {
                Map<String, Object> stack = (Map<String, Object>) dataInput.readObject();

                if (stack != null) {
                    items[Index] = ItemStack.deserialize(stack);
                } else {
                    items[Index] = null;
                }
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    @Override
    public UNCPersistantInventory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        InventoryType inventoryType = InventoryType.valueOf(jsonObject.get("type").getAsString());
        String title = jsonObject.get("title").getAsString();
        String key = jsonObject.get("key").getAsString();
        UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        Integer size = jsonObject.get("size").getAsInt();
        ItemStack[] contents;
        try {
            contents = itemStackArrayFromBase64(jsonObject.get("contents").getAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Inventory inventory = Bukkit.createInventory(null, inventoryType, title);
        if (inventoryType.equals(InventoryType.CHEST)) {
            UNCInventoryHolder inventoryHolder = null;
            if (jsonObject.has("holder")) {
                inventoryHolder = new UNCInventoryHolder(jsonObject.get("holder").getAsString());
            }
            inventory = Bukkit.createInventory(inventoryHolder, size, title);
        }
        inventory.setContents(contents);

        return new UNCPersistantInventory(key, title, uuid, inventory);
    }

    @Override
    public JsonElement serialize(UNCPersistantInventory src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("size", src.getInventory().getSize());
        jsonObject.addProperty("type", src.getInventory().getType().name());
        jsonObject.addProperty("contents", itemStackArrayToBase64(src.getInventory().getContents()));
        jsonObject.addProperty("title", src.getTitle());
        jsonObject.addProperty("key", src.getInventoryKey());
        jsonObject.addProperty("uuid", src.getUuid().toString());
        if (src.getInventory().getHolder() instanceof UNCInventoryHolder) {
            jsonObject.addProperty("holder", ((UNCInventoryHolder) src.getInventory().getHolder()).getKey());
        }
        return jsonObject;
    }
}
