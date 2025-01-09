package com.gavin101.accbuilder.branches.fishing.shrimp;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.fishing.ShrimpFishing;
import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class FishShrimpBranch extends Branch {
    @Override
    public boolean isValid() {
        for (Map.Entry<Skill, LevelRange> entry : ShrimpFishing.SHRIMP_FISHING_LEVEL_RANGES.entrySet()) {
            Skill skill = entry.getKey();
            LevelRange range = entry.getValue();
            int currentLevel = Skills.getRealLevel(skill);

            if (currentLevel >= range.getMin() && currentLevel < range.getMax()) {
                Main.setActivity("Small net fishing", Skill.FISHING, range.getMax());
                return true;
            }
        }
        return false;
    }
}
