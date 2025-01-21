package com.gavin101.accbuilder.constants.combat;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.List;
import java.util.Map;

public class ChickenCombat {
    public static final Map<Skill, LevelRange> CHICKEN_LEVEL_RANGES = Map.of(
            Skill.ATTACK,    new LevelRange(1, 10),
            Skill.STRENGTH,  new LevelRange(1, 10),
            Skill.DEFENCE,   new LevelRange(1, 10)
    );

    public static final int[] CHICKEN_LOOT = {
            ItemID.FEATHER
    };

    public static final List<Area> CHICKEN_COMBAT_AREAS = List.of(
            new RectArea(3172, 3302, 3183, 3289), // North of Lumbridge
            new RectArea(3225, 3301, 3236, 3295), // East of Lumbridge
            new RectArea(3020, 3282, 3014, 3298)  // South of Falador

    );
}
