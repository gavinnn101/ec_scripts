package com.gavin101.gbuilder.activitymanager.leafs;

import com.gavin101.gbuilder.activitymanager.activity.Activity;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import lombok.Builder;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.loadout.WithdrawLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

import static com.gavin101.GLib.GLib.loadoutsFulfilled;

@Builder
public class GetCurrentActivityLoadoutLeaf extends Leaf {
    @Builder.Default
    private final boolean buyRemainder = false;

    @Override
    public boolean isValid() {
        Activity activity = ActivityManager.getCurrentActivity();
        EquipmentLoadout equipmentLoadout = activity.getEquipmentLoadout();
        InventoryLoadout inventoryLoadout = activity.getInventoryLoadout();
        return !equipmentLoadout.isFulfilled() || !inventoryLoadout.isFulfilled();
    }

    @Override
    public int onLoop() {
        Log.info("Fulfilling current activity loadout.");
        Activity activity = ActivityManager.getCurrentActivity();
        EquipmentLoadout equipmentLoadout = activity.getEquipmentLoadout();
        InventoryLoadout inventoryLoadout = activity.getInventoryLoadout();

        new WithdrawLoadoutEvent(inventoryLoadout)
                .addEquipmentLoadout(equipmentLoadout)
                .setBuyRemainder(buyRemainder)
                .setEventCompleteCondition(() -> loadoutsFulfilled(equipmentLoadout, inventoryLoadout))
                .executed();
        return ReactionGenerator.getNormal();
    }
}
