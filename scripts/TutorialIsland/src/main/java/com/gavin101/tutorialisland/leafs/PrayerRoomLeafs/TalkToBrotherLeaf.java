package com.gavin101.tutorialisland.leafs.PrayerRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
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
        GLib.talkWithNpc("Brother Brace", Constants.PRAYER_ROOM_AREA);
        return ReactionGenerator.getPredictable();
    }
}
