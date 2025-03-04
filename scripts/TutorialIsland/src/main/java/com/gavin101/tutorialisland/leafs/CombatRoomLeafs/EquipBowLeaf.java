package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.equipment.Equipment;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.EquipmentLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class EquipBowLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 480 && !Equipment.containsAll(Constants.rangeRatEquipmentLoadout.getIDs());
    }

    @Override
    public int onLoop() {
        Log.info("Equipping shortbow and arrows.");
        new EquipmentLoadoutEvent(Constants.rangeRatEquipmentLoadout).setEventCompleteCondition(
                () -> Equipment.containsAll(Constants.rangeRatEquipmentLoadout.getIDs()), Calculations.random(1500, 3000)
        ).execute();
        return ReactionGenerator.getPredictable();
    }
}
