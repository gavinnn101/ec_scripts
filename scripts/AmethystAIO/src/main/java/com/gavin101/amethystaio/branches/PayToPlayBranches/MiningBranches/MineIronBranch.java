package com.gavin101.amethystaio.branches.PayToPlayBranches.MiningBranches;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class MineIronBranch extends Branch {
    @Override
    public boolean isValid() {
        return Skills.getRealLevel(Skill.MINING) < 92;
    }
}
