package com.corvicraft.corvicraftspawns.spawnconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.corvicraft.corvicraftspawns.CorviCraftSpawns;
import com.corvicraft.corvicraftspawns.config.CorvicraftConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class CorvicraftSpawnHandler {
	
	public static final String JSON_SUFFIX = ".json";
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	
	public final String modid;
	protected final Map<ResourceLocation, CorvicraftSpawnSet> spawnSets = new HashMap<>();
	protected final Map<CorvicraftSpawnSet, ResourceLocation[]> defaultSpawnSets;
	
	protected final List<EntityType<?>> validEntityTypes;
	
	protected CorvicraftSpawnHandler(String modidIn, Map<CorvicraftSpawnSet, ResourceLocation[]> defaultSpawnsIn, List<EntityType<?>> validEntityTypesIn) {
		this.modid = modidIn;
		this.defaultSpawnSets = defaultSpawnsIn;
		this.validEntityTypes = validEntityTypesIn;
	}
	
	public void addSpawns(BiomeLoadingEvent eventIn) {
		if (this.spawnSets.containsKey(eventIn.getName())) {
			CorvicraftSpawnSet activeSet = this.spawnSets.get(eventIn.getName());
			if (this.shouldLogDebugData()) {
				CorviCraftSpawns.getLogger().debug(this.modid + " is loading spawns for biome " + eventIn.getName());
				activeSet.logDataEntry();
			}
			// If set to override, remove all spawns for this mod's controlled entities
			if (activeSet.replaceExisting) {
				for (int i = 0; i < this.validEntityTypes.size(); i++) {
					EntityType<?> currentType = this.validEntityTypes.get(i);
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
			activeSet.addSpawnEntries(eventIn, this.validEntityTypes);
		}
	}
	
	public void loadSpawns() {
		boolean useDefault = false;
		//Gets the path to the config directory
		File configDir = new File(CorviCraftSpawns.getConfigPath().toString());
		//Checks to make sure config directory exists before proceeding
		if (configDir.exists()) {
			//Creates the config file
			File modDir = new File(configDir, this.modid);
			if (modDir.exists() && modDir.isDirectory()) {
				// Checks if spawns file exists and is valid.  This will be used later
				File spawnsConfig = new File(modDir, "spawns.json");
				if (spawnsConfig.exists() && !spawnsConfig.isDirectory()) {
					JsonArray spawnsArray = null;
					//Tries to read spawns from file
					FileReader reader = null;
					try {
						reader = new FileReader(spawnsConfig);
						spawnsArray = JsonParser.parseReader(reader).getAsJsonArray();
						reader.close();
					//If the file cannot be found, use default spawns (this really shouldn't happen, but we're trying to be safe)
					} catch (FileNotFoundException e) {
						CorviCraftSpawns.getLogger().warn("Couldn't find spawns config file for mod " + this.modid + "!  Using defaults instead.");
						e.printStackTrace();
						useDefault = true;
					} catch (JsonParseException e) {
						CorviCraftSpawns.getLogger().warn("Spawn config file text for mod " + this.modid + " could not be parsed!  Using defaults instead.");
						e.printStackTrace();
						useDefault = true;
					} catch (IllegalStateException e) {
						CorviCraftSpawns.getLogger().warn("Spawn config file for mod " + this.modid + " is of the wrong type (not a Json array)!  Using defaults instead.");
						e.printStackTrace();
						useDefault = true;
					} catch (IOException e) {
						CorviCraftSpawns.getLogger().warn("Unable to load spawns config file for mod " + this.modid + " !  Using defaults instead.");
						e.printStackTrace();
						useDefault = true;
					}
					
					File setsFolder = new File(modDir, "spawn_sets");
					if (spawnsArray != null && !spawnsArray.isEmpty()) {
						Map<String, CorvicraftSpawnSet> spawnSetMap = this.readSetsFromFolder(setsFolder);
						for (JsonElement spawnElement : spawnsArray) {
							JsonObject spawnObj = null;
							try {
								spawnObj = spawnElement.getAsJsonObject();
							} catch (IllegalStateException error) {
				    			CorviCraftSpawns.getLogger().warn("Provided spawns object was of the wrong type!");
				    			error.printStackTrace();
				    		}
							if (spawnObj != null && spawnObj.has("spawn_set") && spawnObj.has("biomes")) {
								String setName = spawnObj.get("spawn_set").getAsString();
								List<ResourceLocation> biomes = new LinkedList<>();
								try {
									JsonArray spawnArray = spawnObj.get("biomes").getAsJsonArray();
									if (!spawnArray.isEmpty()) {
										for (JsonElement biomeElement : spawnArray) {
											String biome = biomeElement.getAsString();
											// Validate that biome isn't null
											if (biome != null) biomes.add(new ResourceLocation(biome));
										}
									}
								} catch (IllegalStateException error) {
					    			CorviCraftSpawns.getLogger().warn("Provided biomes object was of the wrong type!");
					    			error.printStackTrace();
					    		}
								
								if (biomes.isEmpty()) CorviCraftSpawns.getLogger().debug(this.modid + " provided a spawn set that has no biome list and cannot be used!");
								else if (!spawnSetMap.containsKey(setName)) CorviCraftSpawns.getLogger().debug(this.modid + " provided a spawn set with an invalid set key that cannot be used!  Make sure all of your set keys correspond to the name of a file.");
								else for (ResourceLocation biome : biomes) this.spawnSets.put(biome, spawnSetMap.get(setName));
							}
						}
					}
					else CorviCraftSpawns.getLogger().info("Found empty spawns Json for " + this.modid + ".");
					
				}  else {
					CorviCraftSpawns.getLogger().warn("Couldn't find spawns Json for " + this.modid + "!  Defaults will be used.");
					useDefault = true;
				}
			} else {
				CorviCraftSpawns.getLogger().warn("Couldn't find mod directory for " + this.modid + "!  Defaults will be used.");
				useDefault = true;
			}
			if (useDefault) {
				if (!modDir.exists()) modDir.mkdir();
				if (modDir.exists()) this.writeDefaultSpawnsToJson(modDir);
				else CorviCraftSpawns.getLogger().warn("Unable to create mod directory for " + this.modid + "!");
			}
		}
		else CorviCraftSpawns.getLogger().warn("Couldn't find config directory for " + this.modid + "!  Defaults will be used.");
		if (useDefault) {
			this.spawnSets.clear();
			for (CorvicraftSpawnSet spawnSet : this.defaultSpawnSets.keySet()) {
				for (ResourceLocation biomeLocation : this.defaultSpawnSets.get(spawnSet)) this.spawnSets.put(biomeLocation, spawnSet);
			}
		}
		
	}
	
	protected Map<String, CorvicraftSpawnSet> readSetsFromFolder(@Nonnull File setDirectoryIn) {
		Map<String, CorvicraftSpawnSet> spawnSets = new HashMap<>();
		if (setDirectoryIn.isDirectory()) {
			if (setDirectoryIn.listFiles().length < 1) CorviCraftSpawns.getLogger().info("Spawn set directory for " + this.modid + " is empty.");
			// Gets all files in the directory
			for (File setFile : setDirectoryIn.listFiles()) {
				// Tests to see if this file is a json
				if (!setFile.isDirectory() && setFile.getName().subSequence(setFile.getName().length() - JSON_SUFFIX.length(), setFile.getName().length()).equals(JSON_SUFFIX)) {
					String name = setFile.getName().subSequence(0, setFile.getName().length() - JSON_SUFFIX.length()).toString();
					
					JsonObject setObj = new JsonObject();
					
					//Tries to read set from file
					FileReader reader = null;
					try {
						reader = new FileReader(setFile);
						setObj = JsonParser.parseReader(reader).getAsJsonObject();
						reader.close();
					//If the file cannot be found, use default spawns (this really shouldn't happen, but we're trying to be safe)
					} catch (FileNotFoundException e) {
						CorviCraftSpawns.getLogger().warn("Could not find spawn set file " + setFile.getPath() + "!");
						e.printStackTrace();
					} catch (JsonParseException e) {
						CorviCraftSpawns.getLogger().warn("Unable to parse spawn set file " + setFile.getPath() + " as a Json!");
						e.printStackTrace();
					} catch (IllegalStateException e) {
						CorviCraftSpawns.getLogger().warn("Unable to parse spawn set file " + setFile.getPath() + " as a Json Object!");
						e.printStackTrace();
					} catch (IOException e) {
						CorviCraftSpawns.getLogger().warn("Unable to load spawn set file " + setFile.getPath() + "!");
						e.printStackTrace();
					}
					Optional<CorvicraftSpawnSet> spawnSet = this.getSpawnSetFromJson(name, setObj);
					if (!spawnSet.isEmpty()) {
						spawnSets.put(name, spawnSet.get());
						if (this.shouldLogDebugData()) {
							CorviCraftSpawns.getLogger().debug("Loaded spawn set " + name + ":");
							spawnSet.get().logDataEntry();
						}
					}
				}
				else CorviCraftSpawns.getLogger().debug("Skipping file " + setFile.getPath() + " from directory " + setDirectoryIn.getPath() + " because "+ setFile.getPath() + " is not a Json.");
			}
		}
		else CorviCraftSpawns.getLogger().warn("Tried to read spawn sets from directory " + setDirectoryIn.getPath() + " but " + setDirectoryIn.getPath() + " is not a directory!");
		return spawnSets;
	}
	
	protected void writeDefaultSpawnsToJson(File modDirIn) {
		if (modDirIn.exists() && modDirIn.isDirectory()) {
			JsonArray spawnsJson = new JsonArray();
			File setsFolder = new File(modDirIn, "spawn_sets");
			if (!setsFolder.exists()) setsFolder.mkdir();
			if (setsFolder.exists()) {
				for (CorvicraftSpawnSet spawnSet : this.defaultSpawnSets.keySet()) {
					File spawnFile = new File(setsFolder, spawnSet.setName + ".json");
					if (this.shouldLogDebugData()) CorviCraftSpawns.getLogger().debug("Adding spawns for " + spawnSet.setName + " to Json:");
					boolean spawnsWritten = false;
					//Attempt to write default spawn set to file
					try {
						Writer writer = new FileWriter(spawnFile);
						GSON.toJson(spawnSet.writeToJson(this.shouldLogDebugData()), writer);
						writer.flush(); //flush data to file   <---
						writer.close(); //close write
						spawnsWritten = true;
					} catch (IOException e) {
						CorviCraftSpawns.getLogger().error("Unable to write spawn set " + spawnSet.setName + " for mod " + this.modid + " due to the following-");
						e.printStackTrace();
					} catch (JsonIOException e) {
						CorviCraftSpawns.getLogger().error("Unable to write spawn set json " + spawnSet.setName + " for mod " + this.modid + " due to the following-");
						e.printStackTrace();
					}
					if (spawnsWritten) {
						JsonObject spawnObj = new JsonObject();
						JsonArray biomes = new JsonArray();
						for (ResourceLocation biomeLocation : this.defaultSpawnSets.get(spawnSet)) biomes.add(biomeLocation.toString());
						spawnObj.add("biomes", biomes);
						spawnObj.addProperty("spawn_set", spawnSet.setName);
						spawnsJson.add(spawnObj);
					}
				}
			}
			else CorviCraftSpawns.getLogger().warn("Unable to create spawn sets directory for " + this.modid + "!");
			File spawns = new File(modDirIn, "spawns.json");
			//Attempt to write default spawns to file
			try {
				Writer writer = new FileWriter(spawns);
				GSON.toJson(spawnsJson, writer);
				writer.flush(); //flush data to file   <---
				writer.close(); //close write
			} catch (IOException e) {
				CorviCraftSpawns.getLogger().error("Unable to write spawns file for mod " + this.modid + " due to the following-");
				e.printStackTrace();
			} catch (JsonIOException e) {
				CorviCraftSpawns.getLogger().error("Unable to write spawns json for mod " + this.modid + " due to the following-");
				e.printStackTrace();
			}
		}
	}
	
	protected Optional<CorvicraftSpawnSet> getSpawnSetFromJson(String nameIn, JsonObject jsonObj) {
		return CorvicraftSpawnSet.readFromJson(nameIn, jsonObj);
	}
	
	public boolean shouldLogDebugData() { 
		return CorvicraftConfig.ADVANCED_DEBUGGING.get();
	}
	
	public static class Builder {
		
		public final String modid;
		private final Map<CorvicraftSpawnSet, ResourceLocation[]> defaultSpawnSets = new HashMap<>();
		private final List<EntityType<?>> validEntityTypes = new LinkedList<>();
		
		public Builder (String modIdIn) {
			this.modid = modIdIn;
		}
		
		public Builder withSpawnSet(CorvicraftSpawnSet spawnSet, ResourceLocation... biomes) {
			this.defaultSpawnSets.put(spawnSet, biomes);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder withSpawnSet(CorvicraftSpawnSet spawnSet, ResourceKey<Biome>... biomes) {
			ResourceLocation[] biomeLocations = new ResourceLocation[biomes.length];
			for (int i = 0; i < biomes.length; i++) biomeLocations[i] = biomes[i].location();
			return this.withSpawnSet(spawnSet, biomeLocations);
		}
		
		public Builder withEntityTypes(EntityType<?>... entityTypesIn) {
			for (EntityType<?> entityType : entityTypesIn) {
				if (!this.validEntityTypes.contains(entityType)) this.validEntityTypes.add(entityType);
			}
			return this;
		}
		
		public CorvicraftSpawnHandler build() {
			return new CorvicraftSpawnHandler(this.modid, this.defaultSpawnSets, this.validEntityTypes);
		}
	}
}
