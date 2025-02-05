package com.gavin101.gbuilder.activities.skilling.firemaking.constants;

import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;

import java.util.List;

public class FiremakingConstants {
    public static final int FIREMAKING_ANIMATION = 733;

    private static final Area GRAND_EXCHANGE_FIREMAKING_AREA = new RectArea(3143, 3510, 3188, 3468);
    private static final Area VARROCK_WEST_BANK_FIREMAKING_AREA = new RectArea(3200, 3432, 3170, 3428);
    private static final Area VARROCK_EAST_BANK_FIREMAKING_AREA = new RectArea(3241, 3430, 3265, 3428);

    public static final List<Area> FIREMAKING_AREAS = List.of(
            GRAND_EXCHANGE_FIREMAKING_AREA,
            VARROCK_WEST_BANK_FIREMAKING_AREA,
            VARROCK_EAST_BANK_FIREMAKING_AREA
    );

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

    private static final List<List<WorldTile>> LIST_OF_FIREMAKING_TILE_SETS = List.of(
            FIREMAKING_GRAND_EXCHANGE_TILES,
            FIREMAKING_WEST_VARROCK_TILES,
            FIREMAKING_EAST_VARROCK_TILES
    );

    public static List<WorldTile> getRandomFiremakingTileSet() {
        int randomIndex = Calculations.random(LIST_OF_FIREMAKING_TILE_SETS.size() - 1);
        List<WorldTile> tileSet = LIST_OF_FIREMAKING_TILE_SETS.get(randomIndex);
        Log.debug("Returning firemaking tile set: " +tileSet);
        return tileSet;
    }

    public static boolean atValidFiremakingTile() {
        WorldTile playerTile = Players.localPlayer().getWorldTile();
        GameObject nearestFire = GameObjects.closest("Fire");
        if (nearestFire != null && Players.localPlayer().getWorldTile().equals(nearestFire.getWorldTile())) {
            Log.debug("fire on our tile, returning false.");
            return false;
        }
        if (FiremakingConstants.FIREMAKING_AREAS.stream().noneMatch(area -> area.contains(playerTile))) {
            Log.debug("player tile not in any firemaking areas, returning false.");
            return false;
        }
        return true;
    }
}
