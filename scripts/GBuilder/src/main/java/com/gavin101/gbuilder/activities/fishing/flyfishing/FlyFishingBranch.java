package com.gavin101.gbuilder.activities.fishing.flyfishing;

import com.gavin101.gbuilder.activitymanager.ActivityManager;
import net.eternalclient.api.frameworks.tree.Branch;

public class FlyFishingBranch extends Branch {

    @Override
    public boolean isValid() {
        return ActivityManager.getCurrentActivity().getName().equals("Fly fishing");
    }
}
