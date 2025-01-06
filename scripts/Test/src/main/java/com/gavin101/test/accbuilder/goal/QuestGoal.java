package com.gavin101.test.accbuilder.goal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import net.eternalclient.api.wrappers.quest.Quest;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class QuestGoal extends Goal {
    private final Quest quest;
}
