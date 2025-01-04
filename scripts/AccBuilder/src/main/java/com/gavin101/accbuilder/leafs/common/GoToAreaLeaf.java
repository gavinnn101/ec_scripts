package com.gavin101.accbuilder.leafs.common;

import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.walking.Walking;

public class GoToAreaLeaf extends Leaf {
    private final Area area;
    private final EquipmentLoadout equipmentLoadout;
    private final InventoryLoadout inventoryLoadout;

    public GoToAreaLeaf(Area area, EquipmentLoadout equipmentLoadout, InventoryLoadout inventoryLoadout) {
        this.area = area;
        this.equipmentLoadout = equipmentLoadout;
        this.inventoryLoadout = inventoryLoadout;
    }


    @Override
    public boolean isValid() {
        Log.info("equipment fulfilled: " +equipmentLoadout.isFulfilled());
        Log.info("Inventory fulfilled: " +inventoryLoadout.isFulfilled());
        return equipmentLoadout.isFulfilled()
                && inventoryLoadout.isFulfilled()
                && !area.contains(Players.localPlayer());
    }

    @Override
    public int onLoop() {
        Log.info("Walking to area");
        Walking.walk(area.getRandomTile());
        return ReactionGenerator.getNormal();
    }
}
