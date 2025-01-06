package com.gavin101.test.accbuilder.goal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import net.eternalclient.api.wrappers.skill.Skill;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SkillGoal extends Goal {
    private final Skill skill;
    private final int targetLevel;
}
