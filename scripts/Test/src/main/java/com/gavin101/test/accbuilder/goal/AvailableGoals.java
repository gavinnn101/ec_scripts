package com.gavin101.test.accbuilder.goal;

import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class AvailableGoals {

    public static class Quests {
        // Available quests
        public static final QuestGoal DORICS_QUEST = QuestGoal.builder()
                .goalName("Do quest: " + Quest.DORICS_QUEST.name())
                .quest(Quest.DORICS_QUEST)
                .completedCondition(Quest.DORICS_QUEST::isFinished)
                .build();
        public static final QuestGoal WITCHS_POTION = QuestGoal.builder()
                .goalName("Do quest: " + Quest.WITCHS_POTION.name())
                .quest(Quest.WITCHS_POTION)
                .completedCondition(Quest.WITCHS_POTION::isFinished)
                .build();
    }

    public static class Skills {
        // Available skill goals
        public static SkillGoal FISHING(int goalLevel) {
            return SkillGoal.builder()
                    .goalName("Get " + Skill.FISHING.name() + " level to: " + goalLevel)
                    .skill(Skill.FISHING)
                    .targetLevel(goalLevel)
                    .completedCondition(() -> net.eternalclient.api.accessors.Skills.getRealLevel(Skill.FISHING) >= goalLevel)
                    .build();
        }

        public static SkillGoal MINING(int goalLevel) {
            return SkillGoal.builder()
                    .goalName("Get " + Skill.MINING.name() + " level to: " + goalLevel)
                    .skill(Skill.MINING)
                    .targetLevel(goalLevel)
                    .completedCondition(() -> net.eternalclient.api.accessors.Skills.getRealLevel(Skill.MINING) >= goalLevel)
                    .build();
        }
    }
}
