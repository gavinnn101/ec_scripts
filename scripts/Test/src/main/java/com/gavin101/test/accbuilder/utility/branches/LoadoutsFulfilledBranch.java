package com.gavin101.test.accbuilder.utility.branches;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;

@RequiredArgsConstructor
public class LoadoutsFulfilledBranch extends Branch {
    private final EquipmentLoadout equipmentLoadout;
    private final InventoryLoadout inventoryLoadout;
    @Override
    public boolean isValid() {
        return equipmentLoadout.isFulfilled() && inventoryLoadout.isFulfilled();
    }
}
