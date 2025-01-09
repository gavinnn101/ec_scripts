package com.gavin101.accbuilder.utility;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class General {
    public static boolean canStartCombatTier(Map<Skill, LevelRange> levelRanges) {
        return levelRanges.entrySet().stream()
                .allMatch(entry -> Skills.getRealLevel(entry.getKey()) >= entry.getValue().getMin());
    }
}
