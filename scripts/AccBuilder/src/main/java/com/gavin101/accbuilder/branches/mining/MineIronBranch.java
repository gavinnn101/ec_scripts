package com.gavin101.accbuilder.branches.mining;

import com.gavin101.accbuilder.Main;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

@RequiredArgsConstructor
public class MineIronBranch extends Branch {
    private final int miningGoalLevel;

    @Override
    public boolean isValid() {
        int miningLevel = Skills.getRealLevel(Skill.MINING);
        if (miningLevel >= 15 && miningLevel < miningGoalLevel) {
            Main.setActivity("Mining iron ore until lv" +miningGoalLevel +" mining.", Skill.MINING, miningGoalLevel);
            return true;
        }
        return false;
    }
}
