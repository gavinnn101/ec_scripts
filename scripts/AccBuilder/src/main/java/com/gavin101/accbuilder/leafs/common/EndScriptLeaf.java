package com.gavin101.accbuilder.leafs.common;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.VarPlayer;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.skill.Skill;

public class EndScriptLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !Players.localPlayer().isInCombat()
                && Skills.getRealLevel(Skill.ATTACK) >= 40
                && Skills.getRealLevel(Skill.STRENGTH) >= 40
                && Skills.getRealLevel(Skill.DEFENCE) >= 40
                && Skills.getRealLevel(Skill.FISHING) >= 40
                && Skills.getRealLevel(Skill.MINING) >= 40
                && Skills.getRealLevel(Skill.WOODCUTTING) >= 40
                && PlayerSettings.getConfig(VarPlayer.QUEST_POINTS) >= 10
                && !OwnedItems.contains("Raw");
    }

    @Override
    public int onLoop() {
        GLib.endScript(true, "Stopping script and logging out.");
        return ReactionGenerator.getNormal();
    }
}
