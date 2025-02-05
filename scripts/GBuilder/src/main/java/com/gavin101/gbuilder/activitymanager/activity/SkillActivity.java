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
    private final int minLevel = 0;
    @Builder.Default
    private final int maxLevel = 0;

    @Override
    protected boolean validateActivity() {
        int skillLevel = Skills.getRealLevel(activitySkill);
        return (minLevel == 0 || skillLevel >= minLevel) &&
                (maxLevel == 0 || skillLevel < maxLevel);
    }

    @Override
    public String getDetailedString() {
        return String.format("SkillActivity[name=%s, maxDurationMinutes=%d, skill=%s, minLevel=%d, maxLevel=%d, isValid=%b]",
                getName(), getMaxDurationMinutes(),
                activitySkill.name(),
                minLevel, maxLevel, isValid());
    }
}
