package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.data.NpcID;
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
        return tutorialProgress == 360
                || tutorialProgress == 370
                || (tutorialProgress == 410 && !GLib.isWidgetValid(Widgets.getWidgetChild(Constants.EQUIPMENT_INTERFACE_PARENT_ID)))
                || tutorialProgress == 470;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc(NpcID.COMBAT_INSTRUCTOR, Constants.COMBAT_INSTRUCTOR_AREA);
        return ReactionGenerator.getNormal();
    }
}
