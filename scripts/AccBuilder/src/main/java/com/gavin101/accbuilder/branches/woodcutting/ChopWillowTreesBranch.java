package com.gavin101.accbuilder.branches.woodcutting;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.woodcutting.Woodcutting;
import com.gavin101.accbuilder.utility.LevelRange;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

@RequiredArgsConstructor
public class ChopWillowTreesBranch extends Branch {
    private final int levelGoal;

    @Override
    public boolean isValid() {
        int willowWoodcuttingLevelRequirement = 30;
        int woodcuttingLevel = Skills.getRealLevel(Skill.WOODCUTTING);
        if (woodcuttingLevel >= willowWoodcuttingLevelRequirement && woodcuttingLevel < levelGoal) {
            Main.setActivity("Woodcutting willow logs", Skill.WOODCUTTING, levelGoal);
            return true;
        }
        return false;
    }
}
