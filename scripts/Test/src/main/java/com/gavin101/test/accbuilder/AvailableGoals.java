package com.gavin101.test.accbuilder;

import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class AvailableGoals {

    public static class Quests {
        private static Goal createQuestGoal(Quest quest, String name) {
            return Goal.builder()
                    .goalDescription("Doing quest: " + name)
                    .completedCondition(quest::isFinished)
                    .build();
        }

        // Available quests
        public static final Goal DORICS_QUEST = createQuestGoal(Quest.DORICS_QUEST, "Doric's quest");
        public static final Goal WITCHS_POTION = createQuestGoal(Quest.WITCHS_POTION, "Witch's potion");
    }

    public static class Skills {
        private static Goal createSkillGoal(Skill skill, int goalLevel) {
            return Goal.builder()
                    .goalDescription("Getting " + skill.name().toLowerCase() + " level to: " + goalLevel)
                    .completedCondition(() -> net.eternalclient.api.accessors.Skills.getRealLevel(skill) >= goalLevel)
                    .build();
        }

        // Available skill goals
        public static Goal FISHING(int goalLevel) {
            return createSkillGoal(Skill.FISHING, goalLevel);
        }
        public static Goal MINING(int goalLevel) {
            return createSkillGoal(Skill.MINING, goalLevel);
        }
    }
}
