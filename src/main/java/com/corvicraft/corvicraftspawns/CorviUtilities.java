package com.corvicraft.corvicraftspawns;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;

public class CorviUtilities {

	public static int getJsonElementAsInt(JsonElement elementIn, String elementName) {
		int integerOut = -1;
		try {
			integerOut = Math.max(0, elementIn.getAsInt());
		} catch (ClassCastException e) {
			CorviCraftSpawns.getLogger().warn("Provided " + elementName + " was not an integer!");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			CorviCraftSpawns.getLogger().warn("Provided " + elementName + " had too many elements!");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			CorviCraftSpawns.getLogger().warn("Provided " + elementName + " was not an integer format!");
			e.printStackTrace();
		}
		return integerOut;
	}
	
	@Nullable
    public static String getJsonElementAsString(JsonElement elementIn, String elementName) {
    	String jsonElement = null;
    	try {
    		jsonElement = elementIn.getAsString();
		} catch (ClassCastException e) {
			CorviCraftSpawns.getLogger().warn("Provided " + elementName + " was not a string!");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			CorviCraftSpawns.getLogger().warn("Provided " + elementName + " had too many elements!");
			e.printStackTrace();
		}
    	return jsonElement;
    }
}
