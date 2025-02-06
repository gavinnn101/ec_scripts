package com.gavin101.gbuilder.activities.skilling.combat.constants;

import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;

import java.util.List;

public class Chickens {
    public static final int[] CHICKEN_LOOT = {
            ItemID.FEATHER
    };

    public static final List<Area> CHICKEN_COMBAT_AREAS = List.of(
            new RectArea(3172, 3302, 3183, 3289), // North of Lumbridge
            new RectArea(3225, 3301, 3236, 3295), // East of Lumbridge
            new RectArea(3020, 3282, 3014, 3298)  // South of Falador

    );
}
