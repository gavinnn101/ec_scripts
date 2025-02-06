package com.gavin101.gbuilder.activitymanager.activity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.wrappers.skill.Skill;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SkillActivity extends Activity {
    @NonNull
    private final Skill activitySkill;
    @Builder.Default
    private final int minLevel = 1;
    @Builder.Default
    private final int maxLevel = 99;
    @Builder.Default
    private final ActivityType type = ActivityType.SKILL;

    @Override
    public String getDetailedString() {
        return String.format("SkillActivity[name=%s, maxDurationMinutes=%d, skill=%s, minLevel=%d, maxLevel=%d, isValid=%b]",
                getName(), getMaxDurationMinutes(),
                activitySkill.name(),
                minLevel, maxLevel, isValid());
    }

    @Override
    protected boolean validateActivity() {
        int skillLevel = Skills.getRealLevel(activitySkill);
        return skillLevel >= minLevel && skillLevel < maxLevel;
    }
}
