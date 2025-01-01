package com.gavin101.amethystaio.branches.PayToPlayBranches;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.Client;
import net.eternalclient.api.frameworks.tree.Branch;

public class PayToPlayBranch extends Branch {
    @Override
    public boolean isValid() {
        return Main.freeToPlayFinished && Client.hasMembership();
    }
}
