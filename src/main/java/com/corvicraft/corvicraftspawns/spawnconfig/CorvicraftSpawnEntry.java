package com.corvicraft.corvicraftspawns.spawnconfig;

import java.util.Optional;

import com.corvicraft.corvicraftspawns.CorviCraftSpawns;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.registries.ForgeRegistries;

public class CorvicraftSpawnEntry {

	public static final String ENTITY_TYPE = "entity_type";
	public static final String WEIGHT = "weight";
	public static final String PACK_MIN = "pack_min";
	public static final String PACK_MAX = "pack_max";
	public static final String SPAWN_CATEGORY = "spawn_category";
	
	public final int weight;
	public final int packMin;
	public final int packMax;
	public final ResourceLocation entityType;
	private final Optional<MobCategory> spawnCategory;
	
	public CorvicraftSpawnEntry(ResourceLocation entityTypeIn, int weightIn, int packMinIn, int packMaxIn, Optional<MobCategory> categoryIn) {
		this.entityType = entityTypeIn;
		this.weight = weightIn;
		this.packMin = packMinIn;
		this.packMax = packMaxIn;
		this.spawnCategory = categoryIn;
	}
	
	public CorvicraftSpawnEntry(ResourceLocation entityTypeIn, int weightIn, int packMinIn, int packMaxIn) {
		this(entityTypeIn, weightIn, packMinIn, packMaxIn, Optional.empty());
	}
	
	public MobSpawnSettings.SpawnerData getSpawnerData() {
		return new MobSpawnSettings.SpawnerData(ForgeRegistries.ENTITIES.getValue(this.entityType), this.weight, this.packMin, this.packMax);
	}
	
	public static Optional<CorvicraftSpawnEntry> readJson(JsonObject jsonObjIn) {
		if (jsonObjIn.has(ENTITY_TYPE)) {
			Builder entryBuilder = new Builder(new ResourceLocation(jsonObjIn.get(ENTITY_TYPE).getAsString()));
			if (jsonObjIn.has(WEIGHT)) entryBuilder.withWeight(GsonHelper.getAsInt(jsonObjIn, WEIGHT));
			if (jsonObjIn.has(PACK_MIN)) entryBuilder.withPackMin(GsonHelper.getAsInt(jsonObjIn, PACK_MIN));
			if (jsonObjIn.has(PACK_MAX)) entryBuilder.withPackMax(GsonHelper.getAsInt(jsonObjIn, PACK_MAX));
			if (jsonObjIn.has(SPAWN_CATEGORY)) {
				int category = GsonHelper.getAsInt(jsonObjIn, SPAWN_CATEGORY);
				switch (category) {
					default: entryBuilder.withSpawnCategory(MobCategory.CREATURE); break;
					case 1: entryBuilder.withSpawnCategory(MobCategory.MONSTER); break;
					case 2: entryBuilder.withSpawnCategory(MobCategory.AMBIENT); break;
					case 3: entryBuilder.withSpawnCategory(MobCategory.AXOLOTLS); break;
					case 4: entryBuilder.withSpawnCategory(MobCategory.UNDERGROUND_WATER_CREATURE); break;
					case 5: entryBuilder.withSpawnCategory(MobCategory.WATER_CREATURE); break;
					case 6: entryBuilder.withSpawnCategory(MobCategory.WATER_AMBIENT); break;
					case 7: entryBuilder.withSpawnCategory(MobCategory.MISC); break;
				}
			}
			return Optional.of(entryBuilder.build());
		} else CorviCraftSpawns.getLogger().warn("Tried to load spawn entry from Json, but entry has no entity type!");
		return Optional.empty();
	}
	
	/** Used to permit subclasses to do their own reading */
	public Optional<CorvicraftSpawnEntry> fromJson(JsonObject jsonObjIn) { return readJson(jsonObjIn); }
	
	public JsonObject toJson() {
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(ENTITY_TYPE, this.entityType.toString());
		jsonObj.addProperty(WEIGHT, this.weight);
		jsonObj.addProperty(PACK_MIN, this.packMin);
		jsonObj.addProperty(PACK_MAX, this.packMax);
		if (!this.spawnCategory.isEmpty()) {
			switch (this.spawnCategory.get()) {
				default: jsonObj.addProperty(SPAWN_CATEGORY, 0); break;
				case MONSTER: jsonObj.addProperty(SPAWN_CATEGORY, 1); break;
				case AMBIENT: jsonObj.addProperty(SPAWN_CATEGORY, 2); break;
				case AXOLOTLS: jsonObj.addProperty(SPAWN_CATEGORY, 3); break;
				case UNDERGROUND_WATER_CREATURE: jsonObj.addProperty(SPAWN_CATEGORY, 4); break;
				case WATER_CREATURE: jsonObj.addProperty(SPAWN_CATEGORY, 5); break;
				case WATER_AMBIENT: jsonObj.addProperty(SPAWN_CATEGORY, 6); break;
				case MISC: jsonObj.addProperty(SPAWN_CATEGORY, 7); break;
			}
		}
		return jsonObj;
	}
	
	public int getWeight() { return this.weight; }
	public int packMin() { return this.packMin; }
	public int packMax() { return this.packMax; }
	public ResourceLocation getEntityTypeLocation() { return this.entityType; }
	public Optional<EntityType<?>> getEntityType() {
		if (this.entityType != null && ForgeRegistries.ENTITIES.containsKey(this.entityType)) return Optional.of(ForgeRegistries.ENTITIES.getValue(this.entityType));
		return Optional.empty();
	}
	public MobCategory getSpawnCategory() { return this.spawnCategory.orElse(MobCategory.CREATURE); }
	
	public void logDataEntry() {
		if (!this.getEntityType().isEmpty()) CorviCraftSpawns.getLogger().debug("CorviCraft Spawn Entry: [Entity: " + this.getEntityType().get().toString()
			+ ", Spawn Type: " + this.getSpawnCategory() + ", Weight: " + this.weight + ", Pack Min: " + this.packMin + ",Pack Max: " + this.packMax + "]");
		else CorviCraftSpawns.getLogger().debug("CorviCraft Spawn Entry: INVALID (Null Entity Type)");
	}
	
	public static class Builder {
		private final ResourceLocation entityType;
		private int weight = 0;
		private int packMin = 0;
		private int packMax = 0;
		private Optional<MobCategory> spawnCategory = Optional.empty();
		
		public Builder(ResourceLocation entityTypeIn) {
			this.entityType = entityTypeIn;
		}
		
		public Builder(EntityType<?> entityTypeIn) {
			this.entityType = entityTypeIn.getRegistryName();
		}
		
		public Builder withWeight(int weightIn) { 
			this.weight = Math.max(0, weightIn);
			return this;
		}
		public Builder withPackMin(int minIn) { 
			this.packMin = Math.max(0, minIn);
			return this;
		}
		public Builder withPackMax(int maxIn) { 
			this.packMax = Math.max(0, maxIn);
			return this;
		}
		public Builder withPackSize(int sizeIn) { 
			this.packMin = Math.max(0, sizeIn);
			this.packMax = this.packMin;
			return this;
		}
		public Builder withSpawnCategory(MobCategory categoryIn) {
			if (categoryIn != null) this.spawnCategory = Optional.of(categoryIn);
			else this.spawnCategory = Optional.empty();
			return this;
		}
		
		public CorvicraftSpawnEntry build() {
			return new CorvicraftSpawnEntry(this.entityType, this.weight, this.packMin, Math.max(this.packMin, this.packMax), this.spawnCategory);
		}
	}
}
