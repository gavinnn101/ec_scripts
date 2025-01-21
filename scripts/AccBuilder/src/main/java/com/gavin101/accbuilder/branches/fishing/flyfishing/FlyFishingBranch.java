package com.gavin101.accbuilder.branches.fishing.flyfishing;

import com.gavin101.accbuilder.Main;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

@RequiredArgsConstructor
public class FlyFishingBranch extends Branch {
    private final int levelGoal;

    @Override
    public boolean isValid() {
        int flyFishingLevelRequirement = 20;
        int fishingLevel = Skills.getRealLevel(Skill.FISHING);
        if (fishingLevel >= flyFishingLevelRequirement && fishingLevel < levelGoal) {
            Main.setActivity("Fly fishing", Skill.FISHING, levelGoal);
            return true;
        }
        return false;
    }
}
