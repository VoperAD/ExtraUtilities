package me.voper.extrautilities.helper.impl;

import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentQuantumStorageType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.voper.extrautilities.helper.StorageHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

public class NetworksStorageHelper implements StorageHelper {

    private static final int[] QUANTUM_STORAGE_SIZE = new int[]{
            4096,
            32768,
            262144,
            2097152,
            16777216,
            134217728,
            1073741824,
            Integer.MAX_VALUE
    };

    @Override
    public void restore(@Nonnull ItemStack storage) {
        getAndProcessCache(storage,
                cache -> {
                    final ItemStack itemStored = cache.getItemStack();
                    if (itemStored == null)
                        return;

                    final int amount = cache.getAmount();
                    final SlimefunItem sfItem = SlimefunItem.getByItem(itemStored);

                    if (sfItem == null)
                        return;

                    final ItemStack restore = new ItemStack(sfItem.getItem());
                    cache.setItemStack(restore);
                    cache.setAmount(amount);
                    updateStorage(storage, cache);
                }, () -> {});
    }

    @Override
    public boolean isStorageItem(@Nonnull ItemStack item) {
        final SlimefunItem sfItem = SlimefunItem.getByItem(item);
        return sfItem != null && sfItem.getId().startsWith("NTW_QUANTUM_STORAGE_");
    }

    @Override
    public void setAmount(@Nonnull ItemStack storage, int amount, Runnable onFailure) {
        getAndProcessCache(storage,
                cache -> {
                    if (cache.getItemStack() == null)
                        onFailure.run();

                    cache.setAmount(Math.min(amount, cache.getLimit()));
                    updateStorage(storage, cache);
                }, () -> {});
    }

    @Override
    public void setStoredItem(@Nullable ItemStack storage, @Nonnull ItemStack itemToStore) {
        if (!isStorage(storage))
            return;

        getAndProcessCache(storage,
                cache -> {
                    cache.setItemStack(itemToStore);
                    cache.setAmount(cache.getAmount());
                    updateStorage(storage, cache);
                }, () -> {
                    final String id = SlimefunItem.getByItem(storage).getId();
                    final String[] split = id.split("_");
                    final int limit = QUANTUM_STORAGE_SIZE[Integer.parseInt(split[split.length - 1]) - 1];
                    final QuantumCache cache = new QuantumCache(itemToStore, 1, limit, false);
                    updateStorage(storage, cache);
                });
    }

    @ParametersAreNonnullByDefault
    private void updateStorage(ItemStack storage, QuantumCache cache) {
        final ItemMeta meta = storage.getItemMeta();
        cache.updateMetaLore(meta);
        DataTypeMethods.setCustom(meta, Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE, cache);
        storage.setItemMeta(meta);
    }

    @ParametersAreNonnullByDefault
    private void getAndProcessCache(ItemStack storage, Consumer<QuantumCache> ifPresent, Runnable orElse) {
        DataTypeMethods.getOptionalCustom(storage.getItemMeta(), Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE)
                .ifPresentOrElse(ifPresent, orElse);
    }

}
