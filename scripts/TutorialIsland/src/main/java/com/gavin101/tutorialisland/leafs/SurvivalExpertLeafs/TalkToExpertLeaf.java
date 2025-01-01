package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.hintarrows.HintArrow;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToExpertLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 20 || tutorialProgress == 30 || tutorialProgress == 60)
                && HintArrow.getNpc() != null;
    }

    @Override
    public int onLoop() {
        NPC survivalExpert = NPCs.closest("Survival Expert");
        if (survivalExpert != null && survivalExpert.canReach()) {
            GLib.talkWithNpc("Survival Expert");
        } else if (!Constants.SURVIVAL_EXPERT_AREA.contains(Players.localPlayer())) {
            Log.debug("Survival Expert is null, going to survival expert area.");
            Walking.walk(Constants.SURVIVAL_EXPERT_AREA.getRandomTile(),
                    () -> NPCs.closest("Survival Expert") != null
            );
        }
        return ReactionGenerator.getPredictable();
    }
}
