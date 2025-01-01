package com.gavin101.amethystaio.branches.CommonBranches;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.frameworks.tree.Branch;

public class NeedToHopWorldsBranch extends Branch {
    @Override
    public boolean isValid() {
        return Main.needToHopWorlds;
    }
}
