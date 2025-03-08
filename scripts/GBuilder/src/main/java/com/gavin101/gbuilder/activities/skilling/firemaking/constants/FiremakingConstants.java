package com.gavin101.gbuilder.activities.skilling.firemaking.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;

import java.util.List;

public class FiremakingConstants {
    public static final int FIREMAKING_ANIMATION = 733;

    @Getter
    @AllArgsConstructor
    public enum FiremakingArea {
        GRAND_EXCHANGE(GRAND_EXCHANGE_FIREMAKING_AREA, FIREMAKING_GRAND_EXCHANGE_TILES),
        VARROCK_WEST_BANK(VARROCK_WEST_BANK_FIREMAKING_AREA, FIREMAKING_WEST_VARROCK_TILES),
        VARROCK_EAST_BANK(VARROCK_EAST_BANK_FIREMAKING_AREA, FIREMAKING_EAST_VARROCK_TILES);

        private final Area area;
        private final List<WorldTile> tiles;
    }

    private static final Area GRAND_EXCHANGE_FIREMAKING_AREA = new RectArea(3143, 3510, 3188, 3468);
    private static final Area VARROCK_WEST_BANK_FIREMAKING_AREA = new RectArea(3200, 3432, 3170, 3428);
    private static final Area VARROCK_EAST_BANK_FIREMAKING_AREA = new RectArea(3241, 3430, 3265, 3428);

    public static final List<WorldTile> FIREMAKING_GRAND_EXCHANGE_TILES = List.of(
            new WorldTile(3175, 3473, 0),
            new WorldTile(3175, 3474, 0),
            new WorldTile(3175, 3475, 0),
            new WorldTile(3175, 3476, 0),
            new WorldTile(3175, 3477, 0),
            new WorldTile(3175, 3478, 0),
            new WorldTile(3183, 3482, 0),
            new WorldTile(3183, 3483, 0),
            new WorldTile(3183, 3488, 0),
            new WorldTile(3183, 3489, 0),
            new WorldTile(3183, 3490, 0),
            new WorldTile(3183, 3491, 0),
            new WorldTile(3182, 3496, 0),
            new WorldTile(3182, 3497, 0)
    );

    public static final List<WorldTile> FIREMAKING_WEST_VARROCK_TILES = List.of(
            new WorldTile(3200, 3432, 0),
            new WorldTile(3200, 3431, 0),
            new WorldTile(3200, 3430, 0),
            new WorldTile(3200, 3429, 0),
            new WorldTile(3200, 3428, 0)
    );

    public static final List<WorldTile> FIREMAKING_EAST_VARROCK_TILES = List.of(
            new WorldTile(3265, 3429, 0),
            new WorldTile(3265, 3428, 0)
    );
}
