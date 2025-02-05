package com.gavin101.gbuilder.activities.skilling.fishing.common.constants;

import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;

import java.util.ArrayList;
import java.util.List;

public class Fishing {
    private static final RectArea LUMBRIDGE_FISHING_AREA = new RectArea(3245, 3155, 3238, 3144);

    private static final PolyArea RIMMINGTON_FISHING_AREA = new PolyArea(
            new WorldTile(2982, 3179, 0),
            new WorldTile(2988, 3181, 0),
            new WorldTile(3001, 3157, 0),
            new WorldTile(2994, 3156, 0)
    );

    private static final RectArea BARB_FLY_FISHING_AREA = new RectArea(3110, 3436, 3101, 3423);


    public enum FishingType {
        SMALL_FISHING_NET,
        BAIT_FISHING,
        FLY_FISHING,
        CAGE_FISHING,
        HARPOON_FISHING // Will probably need to be broken out into sub-types (not all harpoon spots have swordfish + shark I think)
    }

    public static List<Area> FISHING_TYPE_TO_AREAS_MAP(FishingType fishingType) {
        List<Area> areas = new ArrayList<>();
        switch (fishingType) {
            case SMALL_FISHING_NET:
            case BAIT_FISHING:
                Log.debug("Returning lumbridge/rimmington fishing areas.");
                areas.add(LUMBRIDGE_FISHING_AREA);
                areas.add(RIMMINGTON_FISHING_AREA);
                break;
            case FLY_FISHING:
                Log.debug("Returning barb fly fishing area.");
                areas.add(BARB_FLY_FISHING_AREA);
                break;
        }
        return areas;
    }
}
