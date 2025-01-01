package com.gavin101.tutorialisland.leafs.QuesterRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
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
        return (tutorialProgress == 200
                || tutorialProgress == 220
                || (tutorialProgress == 230 && Tabs.isDisabled(Tab.QUEST))
                || tutorialProgress == 240
        );
    }

    @Override
    public int onLoop() {
        NPC quester = NPCs.closest("Quest Guide");
        if (quester != null && quester.canReach()) {
            GLib.talkWithNpc("Quest Guide");
        } else {
            getToQuester();
        }
        return ReactionGenerator.getPredictable();
    }

    public void getToQuester() {
        GameObject door = GameObjects.closest(Constants.QUEST_ENTRANCE_DOOR_ID);
        if (door != null) {
            Log.debug("Opening door to quester room.");
            new EntityInteractEvent(door, "Open").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 220, Calculations.random(1000, 3000)
            ).execute();
        } else {
            Log.info("Going to quester room.");
            Tile hintArrowTile = HintArrow.getWorldTile();
            if (hintArrowTile != null) {
                Log.debug("Going to hint arrow tile (quest tutorial room door.)");
                if (hintArrowTile.canReach()) {
                    Walking.walk(hintArrowTile, () -> GameObjects.closest(Constants.QUEST_ENTRANCE_DOOR_ID) != null);
                }
            }
        }
    }
}
