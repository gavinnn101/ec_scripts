package com.gavin101.gbuilder.activities.fishing.shrimp;

import com.gavin101.gbuilder.activitymanager.ActivityManager;
import net.eternalclient.api.frameworks.tree.Branch;

public class FishShrimpBranch extends Branch {
    @Override
    public boolean isValid() {
        return ActivityManager.getCurrentActivity().getName().equals("Fish shrimp");
    }
}
