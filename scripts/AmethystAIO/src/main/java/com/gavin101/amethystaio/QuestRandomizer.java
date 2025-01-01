package com.gavin101.amethystaio;

import net.eternalclient.api.wrappers.quest.Quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestRandomizer {
    private static List<Quest> randomizedQuests;

    public static void setRandomQuestOrder() {
        List<Quest> quests = new ArrayList<>(Constants.F2P_QUESTS);
        Collections.shuffle(quests);
        randomizedQuests = quests;
    }

    public static Quest getNextQuest() {
        for (Quest quest : randomizedQuests) {
            if (!quest.isFinished()) {
                return quest;
            }
        }
        return null; // All quests are finished
    }

    public static boolean isNextQuest(Quest quest) {
        return quest.equals(getNextQuest());
    }
}
