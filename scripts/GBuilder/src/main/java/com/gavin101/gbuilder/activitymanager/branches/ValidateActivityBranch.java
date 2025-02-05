package com.gavin101.gbuilder.activitymanager.branches;

import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.Activity;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Branch;

@RequiredArgsConstructor
public class ValidateActivityBranch extends Branch {
    private final Activity activity;

    @Override
    public boolean isValid() {
        return ActivityManager.getCurrentActivity().getName().equals(activity.getName());
    }
}
