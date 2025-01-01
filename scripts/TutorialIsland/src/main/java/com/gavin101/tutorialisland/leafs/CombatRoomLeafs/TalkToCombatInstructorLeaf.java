package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class TalkToCombatInstructorLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 360
                || tutorialProgress == 370
                || tutorialProgress == 410
                || tutorialProgress == 470
        );
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to the combat instructor.");
        if (PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 410) {
            closeEquipmentInterface();
        }
        NPC instructor = NPCs.closest("Combat Instructor");
        if (instructor != null && instructor.canReach()) {
            GLib.talkWithNpc("Combat Instructor");
        } else if (!Constants.COMBAT_INSTRUCTOR_AREA.contains(Players.localPlayer())) {
            Log.debug("Walking to the combat instructor.");
            Walking.walk(Constants.COMBAT_INSTRUCTOR_AREA.getRandomTile(),
                    () -> NPCs.closest("Combat Instructor") != null
            );
        }
        return ReactionGenerator.getPredictable();
    }

    public void closeEquipmentInterface() {
        Log.info("Closing equipment interface.");
        WidgetChild closeEquipmentInterfaceButtonWidget = Widgets.getWidgetChild(Constants.EQUIPMENT_INTERFACE_PARENT_ID, Constants.EQUIPMENT_INTERFACE_CHILD_ID, Constants.EQUIPMENT_INTERFACE_X_BUTTON_ID);
        if (closeEquipmentInterfaceButtonWidget != null && closeEquipmentInterfaceButtonWidget.isVisible()) {
            new WidgetEvent(closeEquipmentInterfaceButtonWidget, "Close").setEventCompleteCondition(
                    () -> !closeEquipmentInterfaceButtonWidget.isVisible(), Calculations.random(1000, 2500)
            ).execute();
        }
    }
}
