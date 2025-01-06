package com.gavin101.test.accbuilder.tasks.leveling.fishing.tierthree.branches;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class TierThreeFishingBranch extends Branch {
    @Override
    public boolean isValid() {
        int currentFishingLevel = Skills.getRealLevel(Skill.FISHING);
        return currentFishingLevel >= 20 && currentFishingLevel < 99;
    }
}
