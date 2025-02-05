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


    public boolean canStart() {
        int skillLevel = Skills.getRealLevel(activitySkill);
        return skillLevel >= minLevel;
    }

    public boolean isCompleted() {
        int skillLevel = Skills.getRealLevel(activitySkill);
        return skillLevel >= maxLevel;
    }

    @Override
    public String getDetailedString() {
        return String.format("SkillActivity[name=%s, maxDurationMinutes=%d, skill=%s, minLevel=%d, maxLevel=%d, isValid=%b]",
                getName(), getMaxDurationMinutes(),
                activitySkill.name(),
                minLevel, maxLevel, isValid());
    }

    @Override
    protected boolean validateActivity() {
        return canStart() && !isCompleted();
    }
}
