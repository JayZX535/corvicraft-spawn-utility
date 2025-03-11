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
	public static final String RARITY_MODIFIERS = "rarity_modifiers";
	
	public final int weight;
	public final int packMin;
	public final int packMax;
	public final ResourceLocation entityType;
	public final RarityModifiers rarityModifiers;
	
	public CorvicraftSpawnEntry(ResourceLocation entityTypeIn, int weightIn, int packMinIn, int packMaxIn, RarityModifiers rarityModifiersIn) {
		this.entityType = entityTypeIn;
		this.weight = weightIn;
		this.packMin = packMinIn;
		this.packMax = packMaxIn;
		this.rarityModifiers = rarityModifiersIn;
	}
	
	@Deprecated
	/** In order to improve mob cap complience, mob category is no longer assignable */
	public CorvicraftSpawnEntry(ResourceLocation entityTypeIn, int weightIn, int packMinIn, int packMaxIn, RarityModifiers rarityModifiersIn, Optional<MobCategory> categoryIn) {
		this(entityTypeIn, weightIn, packMinIn, packMaxIn, rarityModifiersIn);
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
	}
	
	/** Used to permit subclasses to do their own reading */
	public Optional<CorvicraftSpawnEntry> fromJson(JsonObject jsonIn) { return readJson(jsonIn); }
	
	public JsonObject toJson() {
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(ENTITY_TYPE, this.entityType.toString());
		jsonObj.addProperty(WEIGHT, this.weight);
		jsonObj.addProperty(PACK_MIN, this.packMin);
		jsonObj.addProperty(PACK_MAX, this.packMax);
		JsonObject rarityModifiers = new JsonObject();
		this.rarityModifiers.toJson(rarityModifiers);
		jsonObj.add(RARITY_MODIFIERS, rarityModifiers);
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
	
	@Deprecated
	/** It's more ideal to check the entry's type and then call category directly from that */
	public MobCategory getSpawnCategory() { 
		if (ForgeRegistries.ENTITIES.containsKey(this.entityType)) {
			EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(this.entityType);
			if (entityType != null) return entityType.getCategory();
		}
		return MobCategory.CREATURE; }
	
	public void logDataEntry() {
		if (!this.getEntityType().isEmpty()) CorviCraftSpawns.getLogger().debug("CorviCraft Spawn Entry: [Entity: " + this.getEntityType().get().toString()
			+ ", Weight: " + this.weight + ", Pack Min: " + this.packMin + ", Pack Max: " + this.packMax + "], " + this.rarityModifiers.getDebugString());
		else CorviCraftSpawns.getLogger().debug("CorviCraft Spawn Entry: INVALID (Null Entity Type)");
	}
	
	public static class Builder {
		protected final ResourceLocation entityType;
		protected int weight = 0;
		protected int packMin = 0;
		protected int packMax = 0;
		protected RarityModifiers rarityModifiers = RarityModifiers.NONE;
		
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
		
		@Deprecated
		/** In order to improve mob cap complience, mob category is no longer assignable */
		public Builder withSpawnCategory(MobCategory categoryIn) { return this; }
		
		public CorvicraftSpawnEntry build() {
			return new CorvicraftSpawnEntry(this.entityType, this.weight, this.packMin, Math.max(this.packMin, this.packMax), this.rarityModifiers);
		}
	}
}
