package com.corvicraft.corvicraftspawns.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CorvicraftConfig {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
	
	// Whether or not to log advanced debug info
    public static final ForgeConfigSpec.ConfigValue<Boolean> ADVANCED_DEBUGGING;
    
    // Whether or not to override vanilla spawns.  Otherwise, vanilla spawns will not be influenced.
    public static final ForgeConfigSpec.ConfigValue<Boolean> OVERRIDE_VANILLA;
    
    static {
    	BUILDER.push("General Settings");
    	ADVANCED_DEBUGGING = BUILDER.comment("Prints additional debug information to the log.",
    		"In most cases this information isn't necessary and excessive debugging can generate lag.",
			"However, if you are troubleshooting or reporting an issue, advanced log info may be helpful to turn on.")
			.define("advanced_debugging", false);
    	OVERRIDE_VANILLA = BUILDER.comment("Whether or not to override vanilla spawn entries with CorviCraft ones.",
    		"If disabled, vanilla spawns will not be altered.  If enabled, CorviCraft values can be used to alter vanilla ones (the default sets apply the same values as vanilla).")
    		.define("override_vanilla", false);
		BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
