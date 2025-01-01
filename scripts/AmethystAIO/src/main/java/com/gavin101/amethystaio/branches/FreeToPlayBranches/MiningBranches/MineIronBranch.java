package com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches;

import com.gavin101.amethystaio.GActivityHelper;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;


public class MineIronBranch extends Branch {
    private static final int requiredMiningLevel = 15;
    @Override
    public boolean isValid() {
        return  GActivityHelper.getCurrentActivity().equals(MiningBranch.activityName)
                && Skills.getRealLevel(Skill.MINING) >= requiredMiningLevel;
    }
}