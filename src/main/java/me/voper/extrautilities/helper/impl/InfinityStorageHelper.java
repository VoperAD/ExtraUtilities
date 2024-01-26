package me.voper.extrautilities.helper.impl;

import me.voper.extrautilities.helper.StorageHelper;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfinityStorageHelper implements StorageHelper {

    @Override
    public void restore(@Nonnull ItemStack storage) {

    }

    @Override
    public boolean isStorageItem(@Nonnull ItemStack item) {
        return false;
    }

    @Override
    public void setAmount(@Nullable ItemStack storage, int amount, Runnable onFailure) {

    }

    @Override
    public void setStoredItem(@Nullable ItemStack storage, @Nonnull ItemStack itemToStore) {

    }
}
