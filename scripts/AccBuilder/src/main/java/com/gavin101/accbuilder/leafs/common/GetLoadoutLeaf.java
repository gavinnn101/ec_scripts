package com.gavin101.accbuilder.leafs.common;

import com.gavin101.accbuilder.Main;
import lombok.Builder;
import lombok.Getter;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.loadout.WithdrawLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

import static com.gavin101.GLib.GLib.loadoutsFulfilled;

@Getter
@Builder
public class GetLoadoutLeaf extends Leaf {

    @Builder.Default
    private final boolean buyRemainder = false;

    @Builder.Default
    private final boolean useBestEquipment = false;

    // If null, we’ll use Main.bestEquipment in isValid() & onLoop().
    private final EquipmentLoadout equipmentLoadout;

    private final InventoryLoadout inventoryLoadout;

    @Override
    public boolean isValid() {
        EquipmentLoadout eq = useBestEquipment
                ? Main.currentEquipmentLoadout
                : equipmentLoadout;

        if (eq == null) {
            Log.debug("eq is null");
            return false;
        }
        if (inventoryLoadout == null) {
            Log.debug("inventory is null");
            return false;
        }

        if (loadoutsFulfilled(eq, inventoryLoadout)) {
            Main.activityLoadoutsFulfilled = true;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int onLoop() {
        EquipmentLoadout eq = useBestEquipment
                ? Main.currentEquipmentLoadout
                : equipmentLoadout;

        Log.info("Getting loadout");
        Main.currentEquipmentLoadout = equipmentLoadout;
        Main.currentInventoryLoadout = inventoryLoadout;
        new WithdrawLoadoutEvent(inventoryLoadout)
                .addEquipmentLoadout(eq)
                .setBuyRemainder(buyRemainder)
                .setEventCompleteCondition(() -> loadoutsFulfilled(eq, inventoryLoadout))
                .execute();
        Log.debug("Setting main.activityLoadoutsFulfilled = true");
        Main.activityLoadoutsFulfilled = true;
        return ReactionGenerator.getNormal();
    }
}
