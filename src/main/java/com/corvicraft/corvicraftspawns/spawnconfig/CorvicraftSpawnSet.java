package com.corvicraft.corvicraftspawns.spawnconfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.corvicraft.corvicraftspawns.CorviCraftSpawns;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class CorvicraftSpawnSet {

	public static final String REPLACE_EXISTING = "replace_existing";
	public static final String SPAWNS = "spawns";
	
	public final String setName;
	protected boolean replaceExisting;
	protected final List<CorvicraftSpawnEntry> spawnEntries;
	
	public CorvicraftSpawnSet (String setNameIn, boolean replaceExistingIn, List<CorvicraftSpawnEntry> spawnEntriesIn) {
		this.setName = setNameIn;
		this.replaceExisting = replaceExistingIn;
		this.spawnEntries = spawnEntriesIn;
	}
	
	public boolean replaceExisting() { return this.replaceExisting; }
	
	public CorvicraftSpawnEntry[] getSpawnEntries() {
		CorvicraftSpawnEntry[] spawnEntries = new CorvicraftSpawnEntry[this.spawnEntries.size()];
		this.spawnEntries.toArray(spawnEntries);
		return spawnEntries;
	}
	
	public void addSpawnEntries(BiomeLoadingEvent eventIn, List<EntityType<?>> validTypesIn) {
		for (int i = 0; i < this.spawnEntries.size(); i++) {
			CorvicraftSpawnEntry activeEntry = this.spawnEntries.get(i);
			if (this.isValidEntityType(activeEntry.entityType, validTypesIn)) eventIn.getSpawns().getSpawner(activeEntry.getSpawnCategory()).add(activeEntry.getSpawnerData());
		}
	}
	
	public boolean isValidEntityType(ResourceLocation testTypeIn, List<EntityType<?>> validTypesIn) {
		if (testTypeIn == null) {
			CorviCraftSpawns.getLogger().warn("Provided entity type was null!");
			return false;
		}
		if (ForgeRegistries.ENTITIES.containsKey(testTypeIn)) {
			EntityType<?> testType = ForgeRegistries.ENTITIES.getValue(testTypeIn);
			if (validTypesIn.contains(testType)) return true;
			else CorviCraftSpawns.getLogger().warn("Entity type " + testTypeIn.toString() + " is not controlled by this mod's spawn set!");
		} else CorviCraftSpawns.getLogger().warn("Entity type " + testTypeIn.toString() + " was not found in Forge registry!");
		return false;
	}
	
	public static Optional<CorvicraftSpawnSet> readFromJson(String nameIn, JsonObject jsonObjIn, @Nonnull CorvicraftSpawnEntry dummyIn) { 
		if (jsonObjIn.has(SPAWNS)) {
			JsonArray spawns = jsonObjIn.getAsJsonArray(SPAWNS);
			List<CorvicraftSpawnEntry> spawnEntries = new LinkedList<>();
			for (JsonElement spawn : spawns) {
				Optional<CorvicraftSpawnEntry> entry = dummyIn.fromJson(spawn.getAsJsonObject());
				if (!entry.isEmpty()) spawnEntries.add(entry.get());
			}
			boolean replaceExisting = false;
			if (jsonObjIn.has(REPLACE_EXISTING)) replaceExisting = GsonHelper.getAsBoolean(jsonObjIn, REPLACE_EXISTING);
			return Optional.of(new CorvicraftSpawnSet(nameIn, replaceExisting, spawnEntries));
		}
		CorviCraftSpawns.getLogger().warn("Tried to read spawn set " + nameIn + " from Json, but it contained no spawns entry!");
		return Optional.empty();
	}
	
	public JsonObject writeToJson(boolean logDebugIn) {
		JsonObject jsonObj = new JsonObject();
		JsonArray spawnArray = new JsonArray();
		for (CorvicraftSpawnEntry entry : this.spawnEntries) {
			if (!entry.getEntityType().isEmpty()) {
				spawnArray.add(entry.toJson());
				if (logDebugIn) entry.logDataEntry();
			} else CorviCraftSpawns.getLogger().warn("Found invalid spawn entry while writing to Json, skipping!");
		}
		jsonObj.addProperty(REPLACE_EXISTING, this.replaceExisting);
		jsonObj.add(SPAWNS, spawnArray);
		return jsonObj;
	}
	
	public void logDataEntry() {
		CorviCraftSpawns.getLogger().debug("Replace existing spawns? " + this.replaceExisting);
		for (CorvicraftSpawnEntry entry : this.spawnEntries) entry.logDataEntry();
	}
	
	public static class Builder {
		
		protected boolean replaceExisting = false;
		protected List<CorvicraftSpawnEntry> spawnEntries = new LinkedList<>();
		
		protected String setName;
		
		public Builder (String setNameIn) {
			this.setName = setNameIn;
		}
		
		public Builder replaceExisting(boolean replaceExistingIn) {
			this.replaceExisting = replaceExistingIn;
			return this;
		}
		
		public Builder withSpawns(CorvicraftSpawnEntry... entries) {
			for (CorvicraftSpawnEntry entry : entries) this.spawnEntries.add(entry);
			return this;
		}
		
		public CorvicraftSpawnSet build() {
			return new CorvicraftSpawnSet(this.setName, this.replaceExisting, this.spawnEntries);
		}
	}
}
