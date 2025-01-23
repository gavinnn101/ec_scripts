package com.gavin101.accbuilder.leafs.common;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.skill.Skill;

public class EndScriptLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !Players.localPlayer().isInCombat()
                && Skills.getRealLevel(Skill.ATTACK) >= 50
                && Skills.getRealLevel(Skill.STRENGTH) >= 50
                && Skills.getRealLevel(Skill.DEFENCE) >= 50
                && Skills.getRealLevel(Skill.FISHING) >= 50
                && Skills.getRealLevel(Skill.MINING) >= 51
                && Skills.getRealLevel(Skill.WOODCUTTING) >= 51
                && GLib.getQuestPoints(false) >= 12
                && !OwnedItems.contains("Raw")
                && !OwnedItems.contains("logs");
    }

    @Override
    public int onLoop() {
        GLib.endScript(true, "Stopping script and logging out.");
        return ReactionGenerator.getNormal();
    }
}
