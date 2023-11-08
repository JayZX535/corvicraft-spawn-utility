package com.corvicraft.corvicraftspawns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class CorviSpawnDataPackage {
	
	private final CorviSpawnDataEntry[] spawnDataEntries;
	private ResourceLocation[] biomeList = new ResourceLocation[] {};
	private boolean overrideExisting;
	
	public CorviSpawnDataPackage(CorviSpawnDataEntry[] spawnDataEntriesIn, ResourceLocation biomeIn, boolean overrideExistingIn) {
		this.spawnDataEntries = spawnDataEntriesIn;
		this.biomeList  = new ResourceLocation[] {biomeIn};
		this.overrideExisting = overrideExistingIn;
	}
	
	public CorviSpawnDataPackage(CorviSpawnDataEntry[] spawnDataEntriesIn, ResourceLocation biomeIn[], boolean overrideExistingIn) {
		this.spawnDataEntries = spawnDataEntriesIn;
		this.biomeList = biomeIn;
		this.overrideExisting = overrideExistingIn;
	}
	
	public CorviSpawnDataPackage(JsonObject jsonObjIn) {
		List <ResourceLocation> biomeListIn = new ArrayList<ResourceLocation>();
		if (jsonObjIn.has("biome")) biomeListIn.add(new ResourceLocation(jsonObjIn.get("biome").getAsString()));
		if (jsonObjIn.has("biomes")) {
			JsonArray biomeArray = jsonObjIn.get("biomes").getAsJsonArray();
			if (!biomeArray.isEmpty()) for (JsonElement e : biomeArray) biomeListIn.add(new ResourceLocation(e.getAsString()));
		}
		
		CorviCraftSpawns.getLogger().info("Loading spawns for biomes " + biomeListIn.toString());
		
		List <CorviSpawnDataEntry> spawnsIn = new ArrayList<CorviSpawnDataEntry>();
		if (jsonObjIn.has("spawns")) {
			JsonArray spawnsArray = jsonObjIn.get("spawns").getAsJsonArray();
			if (!spawnsArray.isEmpty()) for (JsonElement e : spawnsArray) spawnsIn.add(new CorviSpawnDataEntry(e.getAsJsonObject()));
		}
		
		CorviSpawnDataEntry[] spawnsInArr = new CorviSpawnDataEntry[spawnsIn.size()];
		spawnsIn.toArray(spawnsInArr);
		this.spawnDataEntries = spawnsInArr; 
		
		ResourceLocation[] biomeListArr = new ResourceLocation[biomeListIn.size()];
		biomeListIn.toArray(biomeListArr);
		this.biomeList = biomeListArr;
		
		if (jsonObjIn.has("override_existing_entries")) this.overrideExisting = jsonObjIn.get("override_existing_entries").getAsBoolean();
		else this.overrideExisting = false;
	}
	
	public boolean overrideExistingEntries() {
		return this.overrideExisting;
	}
	
	public CorviSpawnDataEntry[] getSpawnDataEntries() {
		return this.spawnDataEntries;
	}
	
	private boolean isEqual(CorviSpawnDataEntry[] spawnDataEntriesIn) {
		return Arrays.equals(this.spawnDataEntries, spawnDataEntriesIn);
	}
	
	private void appendBiome(ResourceLocation biomeIn) {
		ResourceLocation[] biomeListNew = new ResourceLocation[this.biomeList.length + 1];
		for (int i = 0; i < this.biomeList.length; i++) {
			biomeListNew[i] = this.biomeList[i];
		}
		biomeListNew[biomeListNew.length - 1] = biomeIn;
		this.biomeList = biomeListNew;
	}
	
	private boolean containsBiome(ResourceLocation biomeIn) {
		if (this.biomeList.length < 1) return false;
		boolean containsBiome = false;
		for (int i = 0; i < this.biomeList.length; i++) {
			if (this.biomeList[i].equals(biomeIn)) containsBiome = true;
			if (containsBiome) i = this.biomeList.length;
		}
		return containsBiome;
	}
	
	public ResourceLocation[] getBiomes() {
		return this.biomeList;
	}
	
	public boolean appendIfValid(CorviSpawnDataEntry[] spawnDataEntriesIn, ResourceLocation biomeIn) {
		if (this.isEqual(spawnDataEntriesIn) && !this.containsBiome(biomeIn)) {
			this.appendBiome(biomeIn);
			return true;
		}
		return false;
	}
	
	/**
	 * Converts this spawn package to Json.  Invalid packages will return a json with nothing in it
	 * @return
	 */
	public JsonObject convertToJSON() {
		JsonObject jsonObj = new JsonObject();
		if (this.biomeList.length > 0 && this.spawnDataEntries.length > 0) {
			//If there is only one biome, this sets up a normal 1-biome entry
			if (this.biomeList.length == 1) {
				jsonObj.addProperty("biome", this.biomeList[0].toString());
			//Otherwise, a full biome array is added
			} else {
				JsonArray biomeArray = new JsonArray();
				for (int i = 0; i < this.biomeList.length; i++) biomeArray.add(this.biomeList[i].toString());
				jsonObj.add("biomes", biomeArray);
			}
			jsonObj.addProperty("override_existing_entries", this.overrideExisting);
			//Adds all spawn entries to an array
			JsonArray entryArray = new JsonArray();
			for (int i = 0; i < this.spawnDataEntries.length; i++) entryArray.add(this.spawnDataEntries[i].convertToJSON());
			jsonObj.add("spawns", entryArray);
		}
		return jsonObj;
	}
	
	public void addEntriesToMapBuilder(Builder<ResourceLocation, CorviSpawnDataPackage> builderIn) {
		if (this.spawnDataEntries.length > 0) {
			if (this.biomeList.length == 1) builderIn.put(this.biomeList[0], this);
			else for (int i = 0; i < this.biomeList.length; i++) builderIn.put(this.biomeList[i], new CorviSpawnDataPackage(this.spawnDataEntries, this.biomeList[i], this.overrideExisting));
		}
	}
	
	public void addSpawnEntries(BiomeLoadingEvent eventIn) {
		for (int i = 0; i < this.spawnDataEntries.length; i++) {
			EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(this.spawnDataEntries[i].getEntityType());
			if (entityType != null) eventIn.getSpawns().getSpawner(this.spawnDataEntries[i].getSpawnType())
					.add(new MobSpawnSettings.SpawnerData(entityType,
					this.spawnDataEntries[i].getWeight(),
					this.spawnDataEntries[i].getSpawnMin(),
					this.spawnDataEntries[i].getSpawnMax()));
		}
	}
	
	public void debugDataPackage() {
		CorviCraftSpawns.getLogger().info("CorviCraft Spawn Data Package Contains...");
		CorviCraftSpawns.getLogger().info("Biomes: " + this.biomeList.toString());
		CorviCraftSpawns.getLogger().info("Entries: ");
		for (int i = 0; i < this.spawnDataEntries.length; i++) this.spawnDataEntries[i].debugDataEntry();
		CorviCraftSpawns.getLogger().info("Override Existing? " + this.overrideExisting);	
	}
	
	public boolean isValid() {
		return this.biomeList.length > 0;
	}
}
