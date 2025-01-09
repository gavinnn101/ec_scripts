package com.gavin101.accbuilder.utility;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class GetTrainableSkills {

    public static List<Skill> getTrainableSkills(Map<Skill, LevelRange> levelRanges) {
        // 1. Check if all skills are above the minimum requirement
        boolean allAboveMin = levelRanges.entrySet().stream()
                .allMatch(entry -> Skills.getRealLevel(entry.getKey()) >= entry.getValue().getMin());

        // 2. If not all are above min, return empty
        if (!allAboveMin) {
            return Collections.emptyList();
        }

        // 3. Filter skills that are still below their max
        return levelRanges.entrySet().stream()
                .filter(entry -> Skills.getRealLevel(entry.getKey()) < entry.getValue().getMax())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
