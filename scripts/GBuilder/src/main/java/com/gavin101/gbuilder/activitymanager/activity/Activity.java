package com.gavin101.gbuilder.activitymanager.activity;

import com.gavin101.gbuilder.utility.constants.Common;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.math.Calculations;

import java.util.function.Supplier;

@Data
@SuperBuilder
public abstract class Activity {
    public enum ActivityType {
        SKILL,
        QUEST
    }

    @NonNull
    private final String name;
    protected final ActivityType type;
    @NonNull
    private final Supplier<Branch> branchSupplier;
    @Builder.Default
    private final Supplier<Boolean> validator = () -> true;
    @Builder.Default
    private EquipmentLoadout equipmentLoadout = Common.EMPTY_EQUIPMENT_LOADOUT;
    @Builder.Default
    private final InventoryLoadout inventoryLoadout = Common.EMPTY_INVENTORY_LOADOUT;
    @Builder.Default
    @Setter
    private long maxDurationMinutes = Calculations.random(30, 60);

    // Sub-class implements function
    public abstract String getDetailedString();

    // Sub-class implements function
    protected abstract boolean validateActivity();

    public boolean isValid() {
        return validateActivity() && validator.get();
    }

    public Branch getBranch() {
        return branchSupplier.get();
    }
}

