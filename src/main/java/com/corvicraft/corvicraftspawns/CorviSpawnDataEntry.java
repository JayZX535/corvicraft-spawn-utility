package com.corvicraft.corvicraftspawns;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;

public class CorviSpawnDataEntry {
	private static String SPAWN_TYPE = "spawnType";
	private static String ENTITY_TYPE = "entityType";
	private static String SPAWN_WEIGHT = "spawnWeight";
	private static String PACK_MIN = "minPackSize";
	private static String PACK_MAX = "maxPackSize";
	
	private final MobCategory spawnCategory;
	private final ResourceLocation entityType;
	private final int weight;
	private final int spawnMin;
	private final int spawnMax;
	
	public CorviSpawnDataEntry(ResourceLocation entityTypeIn, int weightIn, int spawnMinIn, int spawnMaxIn, MobCategory spawnCategory) {
		this.entityType = entityTypeIn;
		this.spawnCategory = spawnCategory;
		this.weight = weightIn;
		this.spawnMin = spawnMinIn;
		this.spawnMax = Math.max(this.spawnMin, spawnMaxIn); //Safety check to make sure maximum is not set less than minimum
	}
	
	/**
	 * Creates a SpawnDataPackage from the provided JSON (if valid)
	 */
	public CorviSpawnDataEntry (JsonObject jsonObjIn) {
		this.spawnCategory = jsonObjIn.has(SPAWN_TYPE) ? getSpawnType(jsonObjIn.get(SPAWN_TYPE).getAsInt()) : MobCategory.CREATURE;
		this.weight = jsonObjIn.has(SPAWN_WEIGHT) ? Math.max(0, jsonObjIn.get(SPAWN_WEIGHT).getAsInt()): -1; 
		this.spawnMin = jsonObjIn.has(PACK_MIN) ? Math.max(0, jsonObjIn.get(PACK_MIN).getAsInt()): -1; 
		this.spawnMax = jsonObjIn.has(PACK_MAX) ? Math.max(this.spawnMin, jsonObjIn.get(PACK_MAX).getAsInt()): -1;
		this.entityType = jsonObjIn.has(ENTITY_TYPE) ? new ResourceLocation(jsonObjIn.get(ENTITY_TYPE).getAsString()) : null;
		CorviCraftSpawns.getLogger().info("Created spawn set for entity " + this.getEntityType().toString() + " with weight of " +
				this.weight + ", minimum pack size of " + this.spawnMin + ", and maximum pack size of " +
				this.spawnMax + " as spawn type " + this.getSpawnType().getName());
	}
	
	public MobCategory getSpawnType() {return this.spawnCategory;}
	public static MobCategory getSpawnType(int idIn) {
		switch(idIn) {
			default: return MobCategory.CREATURE;
			case 1: return MobCategory.AMBIENT;
			case 2: return MobCategory.WATER_CREATURE;
			case 3: return MobCategory.WATER_AMBIENT;
			case 4: return MobCategory.UNDERGROUND_WATER_CREATURE;
			case 5: return MobCategory.MONSTER;
			case 6: return MobCategory.AXOLOTLS;
			case 7: return MobCategory.MISC;
		}
	}
	public static int getSpawnTypeID(MobCategory spawnTypeIn) {
		switch(spawnTypeIn) {
			default: return 0;
			case AMBIENT: return 1;
			case WATER_CREATURE: return 2;
			case WATER_AMBIENT: return 3;
			case UNDERGROUND_WATER_CREATURE: return 4;
			case MONSTER: return 5;
			case AXOLOTLS: return 6;
			case MISC: return 7;
		}
	}
	public ResourceLocation getEntityType() {return this.entityType;}
	public int getWeight() {return this.weight;}
	public int getSpawnMin() {return this.spawnMin;}
	public int getSpawnMax() {return this.spawnMax;}
	
	
	/**
	 * Converts this spawn entry to Json.  Invalid entries will return a json with nothing in it
	 * @return
	 */
	public JsonObject convertToJSON() {
		JsonObject jsonObj = new JsonObject();
		if (this.isValidSpawnEntry()) {
			jsonObj.addProperty(ENTITY_TYPE, this.entityType.toString());
			jsonObj.addProperty(SPAWN_TYPE, getSpawnTypeID(this.spawnCategory));
			jsonObj.addProperty(SPAWN_WEIGHT, this.weight);
			jsonObj.addProperty(PACK_MIN, this.spawnMin);
			jsonObj.addProperty(PACK_MAX, this.spawnMax);
		}
		return jsonObj;
	}
	
	/**
	 * Checks the validity conditions for this spawn entry-- invalid spawn entries will not be utilized and in most cases won't be added to spawn maps at all
	 */
	public boolean isValidSpawnEntry() {
		return this.entityType != null;
	}
	
	public void debugDataEntry() {
		CorviCraftSpawns.getLogger().info("CorviCraft Spawn Data Entry...");
		CorviCraftSpawns.getLogger().info("Entity: " + this.entityType.toString());
		CorviCraftSpawns.getLogger().info("Spawn Type: " + this.getSpawnType().toString());
		CorviCraftSpawns.getLogger().info("Weight: " + this.getWeight());
		CorviCraftSpawns.getLogger().info("Pack Min: " + this.getSpawnMin());
		CorviCraftSpawns.getLogger().info("Pack Max: " + this.getSpawnMax());
	}
}
