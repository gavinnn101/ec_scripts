package com.gavin101.tutorialisland.leafs.PrayerRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToBrotherLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 540
                || tutorialProgress == 550
                || tutorialProgress == 570
                || tutorialProgress == 600
        );
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to brother brace.");
        NPC brother = NPCs.closest("Brother Brace");
        if (brother != null && brother.canReach()) {
            GLib.talkWithNpc("Brother Brace");
        } else {
            Log.info("Going to prayer room to talk to Brother Brace");
            if (!Constants.PRAYER_ROOM_AREA.contains(Players.localPlayer())) {
                Log.debug("Walking to prayer room");
                Walking.walk(Constants.PRAYER_ROOM_AREA.getRandomTile(),
                        () -> NPCs.closest("Brother Brace") != null
                );
            }
        }

        return ReactionGenerator.getPredictable();
    }
}
