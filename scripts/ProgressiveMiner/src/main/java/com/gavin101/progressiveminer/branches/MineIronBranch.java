package com.gavin101.progressiveminer.branches;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class MineIronBranch extends Branch {
    @Override
    public boolean isValid() {
        int miningLevel = Skills.getRealLevel(Skill.MINING);
        return miningLevel >= 15 && miningLevel < 92;
    }
}
