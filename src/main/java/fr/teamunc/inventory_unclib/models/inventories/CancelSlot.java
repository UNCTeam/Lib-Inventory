package fr.teamunc.inventory_unclib.models.inventories;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class CancelSlot {
    private List<Integer> slots = new ArrayList<>();

    public CancelSlot(Integer... slots) {
        this.slots = Arrays.asList(slots);
    }

    public CancelSlot(Integer startSlot, Integer endSlot) {
        for (int i = startSlot; i <= endSlot; i++) {
            slots.add(i);
        }
    }
}
