package com.gavin101.test.accbuilder.tasks.leveling.fishing;

import com.gavin101.test.accbuilder.tasks.leveling.fishing.tierone.TierOneFishingConstants;
import com.gavin101.test.accbuilder.tasks.leveling.fishing.tierthree.TierThreeConstants;
import net.eternalclient.api.wrappers.map.Area;

public class Common {
    public static String getFishingSpotName(String interaction) {
        switch (interaction) {
            default:
            case "Net":
            case "Bait":
                return "Fishing spot";
            case "Lure":
                return "Rod Fishing spot";
        }
    }

    public static Area getFishingSpotArea(String interaction) {
        switch (interaction) {
            default:
            case "Net":
            case "Bait":
                return TierOneFishingConstants.LUMBRIDGE_FISHING_AREA;
            case "Lure":
                return TierThreeConstants.BARB_FLY_FISHING_AREA;
        }
    }
}
