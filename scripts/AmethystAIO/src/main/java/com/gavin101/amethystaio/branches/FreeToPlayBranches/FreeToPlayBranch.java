package com.gavin101.amethystaio.branches.FreeToPlayBranches;

import com.gavin101.amethystaio.GLib;
import com.gavin101.amethystaio.Main;
import com.gavin101.amethystaio.QuestRandomizer;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.skill.Skill;

public class FreeToPlayBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Main.freeToPlayFinished) {
            return false;
        }

        if (QuestRandomizer.getNextQuest() == null && Skills.getRealLevel(Skill.MINING) >= 60) {
            Log.info("Setting freeToPlayFinished = true");
            Main.freeToPlayFinished = true;
            GLib.setEFAccountNote(Main.efApiKey, Main.efAccountId, "F2P Finished");
            GLib.endScript(true, "Pay to play not implemented yet, ending script.");
            return false;
        }

        return true;
    }
}