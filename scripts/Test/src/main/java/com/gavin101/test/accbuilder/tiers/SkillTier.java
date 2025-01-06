package com.gavin101.test.accbuilder.tiers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Branch;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SkillTier {
    private final int minLevel;
    private final int maxLevel;
    private final List<Branch> availableTasks;

    public boolean containsLevel(int level) {
        return level >= minLevel && level <= maxLevel;
    }
}