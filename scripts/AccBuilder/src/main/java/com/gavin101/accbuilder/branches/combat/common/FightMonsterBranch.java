package com.gavin101.accbuilder.branches.combat.common;

import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.map.Area;

public class FightMonsterBranch extends Branch {
    private final Area area;

    public FightMonsterBranch(Area area) {
        this.area = area;
    }

    @Override
    public boolean isValid() {
        return area.contains(Players.localPlayer());
    }
}
