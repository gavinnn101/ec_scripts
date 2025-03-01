package com.gavin101.gbuilder.activitymanager.activity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class MiscActivity extends Activity {
    @Builder.Default
    private final ActivityType type = ActivityType.MISC;

    @Override
    public String getDetailedString() {
        return "Misc Activity: " + getName();
    }

    @Override
    protected boolean validateActivity() {
        // Let the individual task do validation for misc activities
        return true;
    }
}
