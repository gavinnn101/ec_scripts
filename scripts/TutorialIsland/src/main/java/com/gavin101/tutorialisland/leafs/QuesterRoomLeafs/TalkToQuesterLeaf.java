package com.gavin101.tutorialisland.leafs.QuesterRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.hintarrows.HintArrow;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.Tile;
import net.eternalclient.api.wrappers.tabs.Tab;
import net.eternalclient.api.wrappers.tabs.Tabs;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToQuesterLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorialProgress == 170
                || tutorialProgress == 200
                || tutorialProgress == 220
                || (tutorialProgress == 230 && Tabs.isDisabled(Tab.QUEST))
                || tutorialProgress == 240;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Quest Guide", Constants.QUEST_GUIDE_AREA);
        return ReactionGenerator.getPredictable();
    }
}
