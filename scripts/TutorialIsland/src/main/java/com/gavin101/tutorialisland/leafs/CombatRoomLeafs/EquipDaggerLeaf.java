package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class EquipDaggerLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 405;
    }

    @Override
    public int onLoop() {
        new InventoryEvent(Inventory.get("Bronze dagger"), "Equip").setEventCompleteCondition(
                () -> !Inventory.contains("Bronze dagger"), Calculations.random(1000, 2500)
        ).execute();
        return ReactionGenerator.getPredictable();
    }
}
