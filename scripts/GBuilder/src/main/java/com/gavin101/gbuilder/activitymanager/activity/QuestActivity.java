package com.gavin101.gbuilder.activitymanager.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import net.eternalclient.api.wrappers.quest.Quest;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class QuestActivity extends Activity {
    @NonNull
    private final Quest quest;

    @Override
    protected boolean validateActivity() {
        return !quest.isFinished();
    }

    @Override
    public String getDetailedString() {
        return String.format("QuestActivity[name=%s, maxDurationMinutes=%d, quest=%s, isFinished=%b, isValid=%b]",
                getName(), getMaxDurationMinutes(),
                quest.name(),
                quest.isFinished(),
                isValid());
    }
}