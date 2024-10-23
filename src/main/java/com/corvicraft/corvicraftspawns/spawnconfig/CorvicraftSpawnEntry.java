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

	public static final CorvicraftSpawnEntry DUMMY = new CorvicraftSpawnEntry(new ResourceLocation("dummy:dummy"), 0, 0, 0);
	
	public static final String ENTITY_TYPE = "entity_type";
	public static final String WEIGHT = "weight";
	public static final String PACK_MIN = "pack_min";
	public static final String PACK_MAX = "pack_max";
	public static final String SPAWN_CATEGORY = "spawn_category";
	public static final String RARITY_MODIFIERS = "rarity_modifiers";
	
	public final int weight;
	public final int packMin;
	public final int packMax;
	public final ResourceLocation entityType;
	protected final RarityModifiers rarityModifiers;
	protected final Optional<MobCategory> spawnCategory;
	
	public CorvicraftSpawnEntry(ResourceLocation entityTypeIn, int weightIn, int packMinIn, int packMaxIn, RarityModifiers rarityModifiersIn, Optional<MobCategory> categoryIn) {
		this.entityType = entityTypeIn;
		this.weight = weightIn;
		this.packMin = packMinIn;
		this.packMax = packMaxIn;
		this.rarityModifiers = rarityModifiersIn;
		this.spawnCategory = categoryIn;
	}
	
	public CorvicraftSpawnEntry(ResourceLocation entityTypeIn, int weightIn, int packMinIn, int packMaxIn, RarityModifiers rarityModifiersIn) {
		this(entityTypeIn, weightIn, packMinIn, packMaxIn, rarityModifiersIn, Optional.empty());
	}
	
	public CorvicraftSpawnEntry(ResourceLocation entityTypeIn, int weightIn, int packMinIn, int packMaxIn) {
		this(entityTypeIn, weightIn, packMinIn, packMaxIn, RarityModifiers.NONE);
	}
	
	public MobSpawnSettings.SpawnerData getSpawnerData() {
		return new MobSpawnSettings.SpawnerData(ForgeRegistries.ENTITIES.getValue(this.entityType), this.weight, this.packMin, this.packMax);
	}
	
	public static Optional<CorvicraftSpawnEntry> readJson(JsonObject jsonIn) {
		if (jsonIn.has(ENTITY_TYPE)) {
			Builder entryBuilder = new Builder(new ResourceLocation(jsonIn.get(ENTITY_TYPE).getAsString()));
			readJsonToSpawnEntryBuilder(entryBuilder, jsonIn);
			if (jsonIn.has(RARITY_MODIFIERS)) entryBuilder.withRarityModifiers(RarityModifiers.readJson(jsonIn.get(RARITY_MODIFIERS).getAsJsonObject()));
			return Optional.of(entryBuilder.build());
		} else CorviCraftSpawns.getLogger().warn("Tried to load spawn entry from Json, but entry has no entity type!");
		return Optional.empty();
	}
	
	protected static void readJsonToSpawnEntryBuilder(Builder builderIn, JsonObject jsonIn) {
		if (jsonIn.has(WEIGHT)) builderIn.withWeight(GsonHelper.getAsInt(jsonIn, WEIGHT));
		if (jsonIn.has(PACK_MIN)) builderIn.withPackMin(GsonHelper.getAsInt(jsonIn, PACK_MIN));
		if (jsonIn.has(PACK_MAX)) builderIn.withPackMax(GsonHelper.getAsInt(jsonIn, PACK_MAX));
		if (jsonIn.has(SPAWN_CATEGORY)) {
			int category = GsonHelper.getAsInt(jsonIn, SPAWN_CATEGORY);
			switch (category) {
				default: builderIn.withSpawnCategory(MobCategory.CREATURE); break;
				case 1: builderIn.withSpawnCategory(MobCategory.MONSTER); break;
				case 2: builderIn.withSpawnCategory(MobCategory.AMBIENT); break;
				case 3: builderIn.withSpawnCategory(MobCategory.AXOLOTLS); break;
				case 4: builderIn.withSpawnCategory(MobCategory.UNDERGROUND_WATER_CREATURE); break;
				case 5: builderIn.withSpawnCategory(MobCategory.WATER_CREATURE); break;
				case 6: builderIn.withSpawnCategory(MobCategory.WATER_AMBIENT); break;
				case 7: builderIn.withSpawnCategory(MobCategory.MISC); break;
			}
		}
	}
	
	/** Used to permit subclasses to do their own reading */
	public Optional<CorvicraftSpawnEntry> fromJson(JsonObject jsonIn) { return readJson(jsonIn); }
	
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
		this.rarityModifiers.toJson(jsonObj);
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
			+ ", Spawn Type: " + this.getSpawnCategory() + ", Weight: " + this.weight + ", Pack Min: " + this.packMin + ", Pack Max: " + this.packMax + "], " + this.rarityModifiers.getDebugString());
		else CorviCraftSpawns.getLogger().debug("CorviCraft Spawn Entry: INVALID (Null Entity Type)");
	}
	
	public static class Builder {
		protected final ResourceLocation entityType;
		protected int weight = 0;
		protected int packMin = 0;
		protected int packMax = 0;
		protected RarityModifiers rarityModifiers = RarityModifiers.NONE;
		protected Optional<MobCategory> spawnCategory = Optional.empty();
		
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
		public Builder withRarityModifiers(RarityModifiers rarityModifiersIn) { 
			if (rarityModifiersIn != null) this.rarityModifiers = rarityModifiersIn;
			return this;
		}
		public Builder withSpawnCategory(MobCategory categoryIn) {
			if (categoryIn != null) this.spawnCategory = Optional.of(categoryIn);
			else this.spawnCategory = Optional.empty();
			return this;
		}
		
		public CorvicraftSpawnEntry build() {
			return new CorvicraftSpawnEntry(this.entityType, this.weight, this.packMin, Math.max(this.packMin, this.packMax), this.rarityModifiers, this.spawnCategory);
		}
	}
}
