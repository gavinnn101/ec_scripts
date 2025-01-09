package com.gavin101.accbuilder.branches.fishing.bait;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.fishing.BaitFishing;
import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class BaitFishingBranch extends Branch {
    @Override
    public boolean isValid() {
        for (Map.Entry<Skill, LevelRange> entry : BaitFishing.BAIT_FISHING_LEVEL_RANGES.entrySet()) {
            Skill skill = entry.getKey();
            LevelRange range = entry.getValue();
            int currentLevel = Skills.getRealLevel(skill);

            if (currentLevel >= range.getMin() && currentLevel < range.getMax()) {
                Main.setActivity("Bait fishing", Skill.FISHING, range.getMax());
                return true;
            }
        }
        return false;
    }
}
