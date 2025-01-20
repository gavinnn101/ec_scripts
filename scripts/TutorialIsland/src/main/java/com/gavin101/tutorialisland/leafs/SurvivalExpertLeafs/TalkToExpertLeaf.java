package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.hintarrows.HintArrow;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.tabs.Tab;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToExpertLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorialProgress == 10
                || tutorialProgress == 20
                || (tutorialProgress == 30 && Tab.INVENTORY.isDisabled())
                || tutorialProgress == 60;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Survival Expert", Constants.SURVIVAL_EXPERT_AREA);
        return ReactionGenerator.getPredictable();
    }
}
