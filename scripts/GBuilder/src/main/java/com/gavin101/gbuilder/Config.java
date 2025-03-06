package com.gavin101.gbuilder;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Config {
    @SerializedName("DEBUG")
    private boolean debug = false;

    @SerializedName("ENABLE_BREAKS")
    private boolean enableBreaks = false;
}