package com.gavin101.tutorialisland.leafs.GuidesRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;


public class TalkToGuideLeaf extends Leaf {

    private static final String[] CHAT_OPTIONS = {
            "I am brand new! This is my first time here.",
            "I am an experienced player.",
            "I've played in the past, but not recently."
    };

    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 2 || tutorialProgress == 7);
    }

    @Override
    public int onLoop() {
        int chatOptionIndex = Calculations.random(0, CHAT_OPTIONS.length - 1);
        String chatOption = CHAT_OPTIONS[chatOptionIndex];
        GLib.talkWithNpc("Gielinor Guide", Constants.GUIDE_ROOM_AREA, chatOption);
        return ReactionGenerator.getNormal();
    }
}
