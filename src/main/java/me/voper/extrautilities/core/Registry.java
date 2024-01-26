package me.voper.extrautilities.core;

import lombok.Getter;
import me.voper.extrautilities.helper.StorageHelper;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Registry {

    private final List<StorageHelper> storageHelpers = new ArrayList<>();

    public void registerHelper(StorageHelper storageHelper) {
        storageHelpers.add(storageHelper);
    }

}
