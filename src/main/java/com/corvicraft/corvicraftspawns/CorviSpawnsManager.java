package com.corvicraft.corvicraftspawns;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class CorviSpawnsManager {
	
	public final String MODID;
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	private Map<ResourceLocation, CorviSpawnDataPackage> BIOME_SPAWN_SETS = ImmutableMap.of();
	private final Map<ResourceLocation, CorviSpawnDataPackage> DEFAULT_SPAWNS;
	private final EntityType<?>[] validEntityTypes;
	private boolean needsLoad = true;
	
	public CorviSpawnsManager(String modidIn, Map<ResourceLocation, CorviSpawnDataPackage> defaultSpawnsIn, EntityType<?>[] validEntityTypesIn) {
		this.MODID = modidIn;
		this.DEFAULT_SPAWNS = defaultSpawnsIn;
		this.validEntityTypes = validEntityTypesIn;
		this.needsLoad = true;
	}
	
	private void loadSpawns() {
		//Gets the path to the config directory
		File configDir = new File(CorviCraftSpawns.getConfigPath().toString());
		//Checks to make sure config directory exists before proceeding
		if (configDir.exists()) {
			//Creates the config file
			File configFile = new File(configDir, this.MODID + "-spawns.json");
			//If no existing config file is found
			if (!configFile.exists()) {
				//Attempt to write default spawns to file
				try {
					Writer writer = new FileWriter(configFile);
					GSON.toJson(this.writeSpawnsToJSON(this.DEFAULT_SPAWNS), writer);
					writer.flush(); //flush data to file   <---
					writer.close(); //close write
				} catch (IOException e) {
					CorviCraftSpawns.getLogger().error("Unable to write to spawns config file for mod " + this.MODID + " due to the following--");
					CorviCraftSpawns.getLogger().error(e.toString());
				}
			}
			
			JsonArray jsonArray = new JsonArray();
			
			//Tries to read spawns from file
			try {
				FileReader reader = null;
				//Attempt to read from file (catches file not found)
				try {
					reader = new FileReader(configFile);
					jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
					this.BIOME_SPAWN_SETS = this.readSpawnsFromJSON(jsonArray);
				//If the file cannot be found, use default spawns (this really shouldn't happen, but we're trying to be safe)
				} catch (FileNotFoundException e) {
					CorviCraftSpawns.getLogger().warn("Couldn't find spawns config file for mod " + this.MODID + "!  Using defaults instead.");
					e.printStackTrace();
				}
			//If spawns cannot be read
			} catch (Exception e) {
				CorviCraftSpawns.getLogger().error("Unable to load spawns config file for mod " + this.MODID + " due to the following--");
				CorviCraftSpawns.getLogger().error(e.toString());
				CorviCraftSpawns.getLogger().error("Using default settings instead.");
			}
			//If spawns have not been set, use default
			if (this.BIOME_SPAWN_SETS.isEmpty()) this.BIOME_SPAWN_SETS = this.DEFAULT_SPAWNS;;
		//If no config directory is found
		} else {
			CorviCraftSpawns.getLogger().warn("Unable to find config directory!  Default spawn settings for mod " + this.MODID + " will be used.");
			this.BIOME_SPAWN_SETS = this.DEFAULT_SPAWNS;
		}
		this.needsLoad = false;
	}
	
	/**
	 * Returns a list of CorviSpawnDataPackages.  Usually used to convert a biome list to JSON, accounting for entries with multiple biomes.
	 */
	private List<CorviSpawnDataPackage> getSpawnPackageList(Map<ResourceLocation, CorviSpawnDataPackage> biomeMapIn){
		List <CorviSpawnDataPackage> spawnsList = new ArrayList<CorviSpawnDataPackage>();
		for (ResourceLocation key : biomeMapIn.keySet()) {
			boolean foundMatch = false;
			//Loops through existing list of SpawnDataPackages to see if any of them use the same spawn set as the currently selected one
			for (int i = 0; i < spawnsList.size(); i++) {
				//Appends the entry if it matches, otherwise returns false
				foundMatch = spawnsList.get(i).appendIfValid(biomeMapIn.get(key).getSpawnDataEntries(), key);
				//If a match is found, skip to the end
				if (foundMatch) i = spawnsList.size();
			}
			//If no existing entry is found, create a new one
			if (!foundMatch) spawnsList.add(new CorviSpawnDataPackage(biomeMapIn.get(key).getSpawnDataEntries(), key, biomeMapIn.get(key).overrideExistingEntries()));
		}
		return spawnsList;
	}
	
	private JsonArray writeSpawnsToJSON(Map<ResourceLocation, CorviSpawnDataPackage> spawnDataPackageIn) {
		JsonArray jsonArray = new JsonArray();
		List<CorviSpawnDataPackage> spawnsList = this.getSpawnPackageList(spawnDataPackageIn);
		for (CorviSpawnDataPackage spawnPackage : spawnsList) jsonArray.add(spawnPackage.convertToJSON());
		return jsonArray;
	}
	
	private Map<ResourceLocation, CorviSpawnDataPackage> readSpawnsFromJSON(JsonArray jsonArrayIn) {
		Builder<ResourceLocation, CorviSpawnDataPackage> builderSettings = ImmutableMap.builder();
		if (!jsonArrayIn.isEmpty()) {
			for (JsonElement e : jsonArrayIn) {
				CorviSpawnDataPackage spawnPackage = new CorviSpawnDataPackage(e.getAsJsonObject());
				if (spawnPackage.isValid()) for (int i = 0; i < spawnPackage.getBiomes().length; i++)
					builderSettings.put(spawnPackage.getBiomes()[i],
							new CorviSpawnDataPackage(spawnPackage.getSpawnDataEntries(), spawnPackage.getBiomes()[i], spawnPackage.overrideExistingEntries()));
			}
		}
		return builderSettings.build();
	}
	
	/**
	 * Adds the spawn entries from a spawn event.  This does NOT auto subscribe to the event, mods should do that individually.
	 */
	public void addSpawns(BiomeLoadingEvent eventIn, String modid) {
		//If biomes haven't been loaded, load biomes
		if (this.needsLoad) this.loadSpawns();
		if (this.BIOME_SPAWN_SETS.containsKey(eventIn.getName())) {
			//If should override existing entries, remove all from the valid entities list
			if (this.BIOME_SPAWN_SETS.get(eventIn.getName()).overrideExistingEntries()) {
				for (int i = 0; i < this.validEntityTypes.length; i++) {
					EntityType<?> currentType = this.validEntityTypes[i];
					eventIn.getSpawns().getSpawner(MobCategory.CREATURE).removeIf((entry) -> { return entry.type.equals(currentType); });
					eventIn.getSpawns().getSpawner(MobCategory.AMBIENT).removeIf((entry) -> { return entry.type.equals(currentType); });
					eventIn.getSpawns().getSpawner(MobCategory.WATER_CREATURE).removeIf((entry) -> { return entry.type.equals(currentType); });
					eventIn.getSpawns().getSpawner(MobCategory.WATER_AMBIENT).removeIf((entry) -> { return entry.type.equals(currentType); });
					eventIn.getSpawns().getSpawner(MobCategory.UNDERGROUND_WATER_CREATURE).removeIf((entry) -> { return entry.type.equals(currentType); });
					eventIn.getSpawns().getSpawner(MobCategory.MONSTER).removeIf((entry) -> { return entry.type.equals(currentType); });
					eventIn.getSpawns().getSpawner(MobCategory.AXOLOTLS).removeIf((entry) -> { return entry.type.equals(currentType); });
					eventIn.getSpawns().getSpawner(MobCategory.MISC).removeIf((entry) -> { return entry.type.equals(currentType); });
				}
			}
			this.BIOME_SPAWN_SETS.get(eventIn.getName()).addSpawnEntries(eventIn);
		}
	}
}
