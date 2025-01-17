package com.gavin101.accbuilder.branches.woodcutting;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.woodcutting.Woodcutting;
import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class ChopOakTreesBranch extends Branch {
    @Override
    public boolean isValid() {
        for (Map.Entry<Skill, LevelRange> entry : Woodcutting.OAK_TREE_LEVEL_RANGES.entrySet()) {
            Skill skill = entry.getKey();
            LevelRange range = entry.getValue();
            int currentLevel = Skills.getRealLevel(skill);

            if (currentLevel >= range.getMin() && currentLevel < range.getMax()) {
                Main.setActivity("Woodcutting oak trees to lv31", Skill.WOODCUTTING, range.getMax());
                return true;
            }
        }
        return false;
    }
}
