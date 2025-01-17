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

public class EquipSwordLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 420;
    }

    @Override
    public int onLoop() {
        Log.info("Equipping sword and shield.");
        EquipmentLoadout loadout = new EquipmentLoadout().addWeapon(Constants.BRONZE_SWORD_ID).addShield(Constants.WOODEN_SHIELD_ID);
        new EquipmentLoadoutEvent(loadout).setEventCompleteCondition(
                () -> Equipment.containsAll(Constants.BRONZE_SWORD_ID, Constants.WOODEN_SHIELD_ID), Calculations.random(1500, 3000)
        ).execute();
        return ReactionGenerator.getNormal();
    }
}
