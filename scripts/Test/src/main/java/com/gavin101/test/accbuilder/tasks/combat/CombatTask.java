package com.gavin101.test.accbuilder.tasks.combat;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.Area;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CombatTask extends Leaf {
    private final NPC npcToFight;
    private final Area npcArea;
    private final Supplier<Boolean> validation;

    @Override
    public boolean isValid() {
        return validation.get();
    }

    @Override
    public int onLoop() {
        return 0;
    }
}
