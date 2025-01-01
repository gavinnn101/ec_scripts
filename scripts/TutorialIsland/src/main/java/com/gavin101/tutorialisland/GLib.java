package com.gavin101.tutorialisland;

import net.eternalclient.api.accessors.Dialogues;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.events.DialogueEvent;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.tabs.Tab;
import net.eternalclient.api.wrappers.tabs.Tabs;

import java.util.Arrays;

public class GLib {
    public static void talkWithNpc(String npcName) {
        Log.info("Talking to: " +npcName);
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing dialogue with: " +npcName);
            new DialogueEvent().setEventCompleteCondition(
                    () -> !Dialogues.inDialogue(), Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.debug("Starting talk with: " +npcName);
            NPC npc = NPCs.closest(npcName);
            if (npc != null && npc.canReach()) {
                Log.debug("Selecting 'Talk-to' on npc: " +npcName);
                new EntityInteractEvent(npc, "Talk-to").setEventCompleteCondition(
                        Dialogues::inDialogue, Calculations.random(1500, 3000)
                ).execute();
            }
        }
    }

    public static void talkWithNpc(String npcName, String... chatOptions) {
        Log.info("Talking to: " +npcName);
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing dialogue with: " +npcName + "with chat options: " + Arrays.toString(chatOptions));
            new DialogueEvent(chatOptions).setEventCompleteCondition(
                    () -> !Dialogues.inDialogue(), Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.debug("Starting talk with: " +npcName);
            NPC npc = NPCs.closest(npcName);
            if (npc != null && npc.canReach()) {
                Log.debug("Selecting 'Talk-to' on npc: " +npcName);
                new EntityInteractEvent(npc, "Talk-to").setEventCompleteCondition(
                        Dialogues::inDialogue, Calculations.random(1500, 3000)
                ).execute();
            }
        }
    }

    public static void openTab(Tab tab) {
        Log.info("Opening tab: " +tab.name());
        if (!Tabs.isOpen(tab)) {
            Log.debug("Tab isn't open. Opening tab: " +tab.name());
            if (Tabs.open(tab)) {
                MethodProvider.sleepUntil(
                        () -> Tabs.isOpen(tab), Calculations.random(1500, 3000)
                );
                // Above check seems correct but still spam opens tab.
                // Will add an extra sleep as a potential band-aid.
                MethodProvider.sleep(750, 1500);
            }
        }
    }
}
