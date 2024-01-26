package me.voper.extrautilities.helper;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface StorageHelper {

    void restore(@Nonnull ItemStack storage);

    boolean isStorageItem(@Nonnull ItemStack item);

    void setAmount(@Nonnull ItemStack storage, int amount, Runnable onFailure);

    void setStoredItem(@Nullable ItemStack storage, @Nonnull ItemStack itemToStore);

    default void setStorageAmount(@Nonnull ItemStack storage, int amount, Runnable onFailure) {
        if (isStorage(storage))
            setAmount(storage, amount, onFailure);
    }

    default List<ItemStack> searchItems(@Nonnull InventoryHolder holder) {
        final List<ItemStack> targetItems = new ArrayList<>();
        final Inventory inv = holder.getInventory();

        for (ItemStack item : inv.getContents())
            if (isStorage(item))
                targetItems.add(item);

        return targetItems;
    }

    default void restoreAll(@Nonnull InventoryHolder holder) {
        searchItems(holder).forEach(this::restore);
    }

    default boolean isStorage(@Nullable ItemStack item) {
        return item != null && isStorageItem(item);
    }

}
