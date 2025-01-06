package com.gavin101.test.accbuilder.tiers.fishing;

import com.gavin101.test.accbuilder.tiers.SkillTier;
import net.eternalclient.api.frameworks.tree.Branch;

import java.util.List;
import java.util.Random;


public class FishingTier extends SkillTier {
    public FishingTier(int minLevel, int maxLevel, List<Branch> availableTasks) {
        super(minLevel, maxLevel, availableTasks);
    }

    public Branch buildTierBranch() {
        List<Branch> availableTasks = getAvailableTasks();
        if (!availableTasks.isEmpty()) {
            int randomTaskIndex = new Random().nextInt(availableTasks.size());
            return getAvailableTasks().get(randomTaskIndex);
        }
        return null;
    }
}
