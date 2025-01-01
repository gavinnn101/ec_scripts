package com.gavin101.accbuilder.constants;

import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class Common {
    public static final Map<Skill, AttackStyle> SKILL_TO_ATTACK_STYLE = Map.of(
            Skill.ATTACK, AttackStyle.ACCURATE,
            Skill.STRENGTH, AttackStyle.AGGRESSIVE,
            Skill.DEFENCE, AttackStyle.DEFENSIVE
    );
}
