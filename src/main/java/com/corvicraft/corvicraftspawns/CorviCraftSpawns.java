package com.corvicraft.corvicraftspawns;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import org.slf4j.Logger;

import java.nio.file.Path;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CorviCraftSpawns.MODID)
public class CorviCraftSpawns
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "corvicraftspawns";
    private static final Path configPath = FMLPaths.GAMEDIR.get().resolve("config").toAbsolutePath();
    private static final CorviSpawnsManager VANILLA_SPAWNS = getNewCorviCraftSpawnsVanilla();

    public CorviCraftSpawns(){
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    /**
     * Event handler for mod events-- NOT forge events!
     */
    @Mod.EventBusSubscriber(modid = CorviCraftSpawns.MODID)
    public class CorviCraftEvents {

    	/**
    	 * Biome load event
    	 * Used to add spawns for vanilla animals
    	 */
    	 @SubscribeEvent(priority = EventPriority.HIGH)
    	 public static void addSpawn(BiomeLoadingEvent eventIn) {
    		 VANILLA_SPAWNS.addSpawns(eventIn, MODID);
    	 }
    }
    
    public static Path getConfigPath() {return configPath;}
    public static Logger getLogger() {return LOGGER;}
    
    /**public static CorviSpawnsManager getNewCorviCraftSpawnsVanilla() {
		Builder<ResourceLocation, CorviSpawnDataPackage> builderSettings = ImmutableMap.builder();
		builderSettings.put(Biomes.BADLANDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.BADLANDS.location(), false));
		builderSettings.put(Biomes.BAMBOO_JUNGLE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PARROT.getRegistryName(), 40, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PANDA.getRegistryName(), 80, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.OCELOT.getRegistryName(), 2, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.BAMBOO_JUNGLE.location(), false));
		builderSettings.put(Biomes.BASALT_DELTAS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.GHAST.getRegistryName(), 40, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.MAGMA_CUBE.getRegistryName(), 100, 2, 5, MobCategory.MONSTER)
		}, Biomes.BASALT_DELTAS.location(), false));
		builderSettings.put(Biomes.BEACH.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.TURTLE.getRegistryName(), 5, 2, 5, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.BEACH.location(), false));
		builderSettings.put(Biomes.BIRCH_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.BIRCH_FOREST.location(), false));
		builderSettings.put(Biomes.COLD_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 15, 3, 6, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 15, 1, 5, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 3, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.COLD_OCEAN.location(), false));
		builderSettings.put(Biomes.CRIMSON_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.ZOMBIFIED_PIGLIN.getRegistryName(), 1, 2, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.HOGLIN.getRegistryName(), 9, 3, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.PIGLIN.getRegistryName(), 5, 3, 4, MobCategory.MONSTER)
		}, Biomes.CRIMSON_FOREST.location(), false));
		builderSettings.put(Biomes.DARK_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DARK_FOREST.location(), false));
		builderSettings.put(Biomes.DEEP_COLD_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 15, 3, 6, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 15, 1, 5, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 3, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DEEP_COLD_OCEAN.location(), false));
		builderSettings.put(Biomes.DEEP_FROZEN_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.POLAR_BEAR.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 15, 1, 5, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 1, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DEEP_FROZEN_OCEAN.location(), false));
		builderSettings.put(Biomes.DEEP_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 10, 3, 6, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 1, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 1, 1, 2, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DEEP_OCEAN.location(), false));
		builderSettings.put(Biomes.DEEP_LUKEWARM_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 8, 3, 6, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.PUFFERFISH.getRegistryName(), 5, 1, 3, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 8, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 2, 1, 2, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DEEP_LUKEWARM_OCEAN.location(), false));
		builderSettings.put(Biomes.DESERT.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 19, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 1, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.HUSK.getRegistryName(), 80, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DESERT.location(), false));
		builderSettings.put(Biomes.DRIPSTONE_CAVES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DRIPSTONE_CAVES.location(), false));
		builderSettings.put(Biomes.END_BARRENS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 4, 4, MobCategory.MONSTER)
		}, Biomes.END_BARRENS.location(), false));
		builderSettings.put(Biomes.END_MIDLANDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 4, 4, MobCategory.MONSTER)
		}, Biomes.END_MIDLANDS.location(), false));
		builderSettings.put(Biomes.END_HIGHLANDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 4, 4, MobCategory.MONSTER)
		}, Biomes.END_HIGHLANDS.location(), false));
		builderSettings.put(Biomes.ERODED_BADLANDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.ERODED_BADLANDS.location(), false));
		builderSettings.put(Biomes.FLOWER_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FLOWER_FOREST.location(), false));
		builderSettings.put(Biomes.FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 5, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FOREST.location(), false));
		builderSettings.put(Biomes.FROZEN_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.POLAR_BEAR.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 15, 1, 5, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 1, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FROZEN_OCEAN.location(), false));
		builderSettings.put(Biomes.FROZEN_PEAKS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.GOAT.getRegistryName(), 5, 1, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FROZEN_PEAKS.location(), false));
		builderSettings.put(Biomes.FROZEN_RIVER.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 1, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 5, 1, 5, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 2, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FROZEN_RIVER.location(), false));
		builderSettings.put(Biomes.GROVE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.FOX.getRegistryName(), 8, 2, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.GROVE.location(), false));
		builderSettings.put(Biomes.ICE_SPIKES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 10, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.POLAR_BEAR.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 20, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.STRAY.getRegistryName(), 80, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.ICE_SPIKES.location(), false));
		builderSettings.put(Biomes.JAGGED_PEAKS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.GOAT.getRegistryName(), 5, 1, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.JAGGED_PEAKS.location(), false));
		builderSettings.put(Biomes.JUNGLE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PARROT.getRegistryName(), 40, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PANDA.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.OCELOT.getRegistryName(), 2, 1, 3, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.JUNGLE.location(), false));
		builderSettings.put(Biomes.LUKEWARM_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 15, 3, 6, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.PUFFERFISH.getRegistryName(), 5, 1, 3, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 10, 1, 2, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 2, 1, 2, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.LUKEWARM_OCEAN.location(), false));
		builderSettings.put(Biomes.LUSH_CAVES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.AXOLOTL.getRegistryName(), 10, 4, 6, MobCategory.AXOLOTLS)
		}, Biomes.LUSH_CAVES.location(), false));
		builderSettings.put(Biomes.MEADOW.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 2, 2, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 2, 2, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.MEADOW.location(), false));
		builderSettings.put(Biomes.MUSHROOM_FIELDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.MOOSHROOM.getRegistryName(), 8, 4, 8, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.MUSHROOM_FIELDS.location(), false));
		builderSettings.put(Biomes.NETHER_WASTES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.GHAST.getRegistryName(), 50, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIFIED_PIGLIN.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.MAGMA_CUBE.getRegistryName(), 2, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 1, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.PIGLIN.getRegistryName(), 15, 4, 4, MobCategory.MONSTER)
		}, Biomes.NETHER_WASTES.location(), false));
		builderSettings.put(Biomes.OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 10, 3, 6, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 1, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 1, 1, 2, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.OCEAN.location(), false));
		builderSettings.put(Biomes.OLD_GROWTH_BIRCH_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.OLD_GROWTH_BIRCH_FOREST.location(), false));
		builderSettings.put(Biomes.OLD_GROWTH_PINE_TAIGA.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.FOX.getRegistryName(), 8, 2, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 25, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.OLD_GROWTH_PINE_TAIGA.location(), false));
		builderSettings.put(Biomes.OLD_GROWTH_SPRUCE_TAIGA.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.FOX.getRegistryName(), 8, 2, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.OLD_GROWTH_SPRUCE_TAIGA.location(), false));
		builderSettings.put(Biomes.PLAINS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 5, 2, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.PLAINS.location(), false));
		builderSettings.put(Biomes.RIVER.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 100, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 5, 1, 5, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 2, 1, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.RIVER.location(), false));
		builderSettings.put(Biomes.SAVANNA.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 1, 2, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 1, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SAVANNA.location(), false));
		builderSettings.put(Biomes.SAVANNA_PLATEAU.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 1, 2, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 1, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.LLAMA.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SAVANNA_PLATEAU.location(), false));
		builderSettings.put(Biomes.SMALL_END_ISLANDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 4, 4, MobCategory.MONSTER)
		}, Biomes.SMALL_END_ISLANDS.location(), false));
		builderSettings.put(Biomes.SNOWY_BEACH.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SNOWY_BEACH.location(), false));
		builderSettings.put(Biomes.SNOWY_PLAINS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 10, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.POLAR_BEAR.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 20, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.STRAY.getRegistryName(), 80, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SNOWY_PLAINS.location(), false));
		builderSettings.put(Biomes.SNOWY_SLOPES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.GOAT.getRegistryName(), 5, 1, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SNOWY_SLOPES.location(), false));
		builderSettings.put(Biomes.SNOWY_TAIGA.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.FOX.getRegistryName(), 8, 2, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SNOWY_TAIGA.location(), false));
		builderSettings.put(Biomes.SOUL_SAND_VALLEY.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 20, 5, 5, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GHAST.getRegistryName(), 50, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 1, 4, 4, MobCategory.MONSTER)
		}, Biomes.SOUL_SAND_VALLEY.location(), false));
		builderSettings.put(Biomes.SPARSE_JUNGLE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SPARSE_JUNGLE.location(), false));
		builderSettings.put(Biomes.STONY_PEAKS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.STONY_PEAKS.location(), false));
		builderSettings.put(Biomes.STONY_SHORE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.STONY_SHORE.location(), false));
		builderSettings.put(Biomes.SUNFLOWER_PLAINS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 5, 2, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SUNFLOWER_PLAINS.location(), false));
		builderSettings.put(Biomes.SWAMP.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 1, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SWAMP.location(), false));
		builderSettings.put(Biomes.TAIGA.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.FOX.getRegistryName(), 8, 2, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.TAIGA.location(), false));
		builderSettings.put(Biomes.THE_END.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 4, 4, MobCategory.MONSTER)
		}, Biomes.THE_END.location(), false));
		builderSettings.put(Biomes.WARM_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.PUFFERFISH.getRegistryName(), 15, 1, 3, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
				new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 10, 4, 4, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 2, 1, 2, MobCategory.WATER_CREATURE),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.WARM_OCEAN.location(), false));
		builderSettings.put(Biomes.WARPED_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 1, 4, 4, MobCategory.MONSTER)
		}, Biomes.WARPED_FOREST.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.LLAMA.getRegistryName(), 5, 4, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.WINDSWEPT_FOREST.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_GRAVELLY_HILLS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.LLAMA.getRegistryName(), 5, 4, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.WINDSWEPT_GRAVELLY_HILLS.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_HILLS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.LLAMA.getRegistryName(), 5, 4, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.WINDSWEPT_HILLS.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_SAVANNA.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 1, 2, 6, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 1, MobCategory.CREATURE),
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.WINDSWEPT_SAVANNA.location(), false));
		builderSettings.put(Biomes.WOODED_BADLANDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
				new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
				new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
				new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.WOODED_BADLANDS.location(), false));
		
    	return new CorviSpawnsManager(MODID, builderSettings.build(), new EntityType<?>[] {EntityType.AXOLOTL, EntityType.BAT, EntityType.BEE, EntityType.BLAZE,
    		EntityType.CAT, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY,
    		EntityType.DROWNED, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX, EntityType.GHAST, EntityType.GLOW_SQUID, EntityType.GOAT,
    		EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HORSE, EntityType.HUSK, EntityType.ILLUSIONER, EntityType.IRON_GOLEM, EntityType.LLAMA,
    		EntityType.MAGMA_CUBE, EntityType.MOOSHROOM, EntityType.MULE, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PHANTOM, EntityType.PIG,
    		EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.POLAR_BEAR, EntityType.PUFFERFISH, EntityType.RABBIT, EntityType.RAVAGER,
    		EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.SLIME,
    		EntityType.SNOW_GOLEM, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.STRIDER, EntityType.TROPICAL_FISH, EntityType.TURTLE,
    		EntityType.VEX, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.WOLF, EntityType.ZOGLIN, EntityType.ZOMBIE,
    		EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN});
    }*/
    
    public static CorviSpawnsManager getNewCorviCraftSpawnsVanilla() {
		Builder<ResourceLocation, CorviSpawnDataPackage> builderSettings = ImmutableMap.builder();
		CorviSpawnDataEntry[] undergroundOnly = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] birchAndDarkForest = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] coldOcean = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 15, 3, 6, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 15, 1, 5, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 3, 1, 4, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] frozenOcean = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.POLAR_BEAR.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 15, 1, 5, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 1, 1, 4, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] ocean = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 10, 3, 6, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 1, 1, 4, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 1, 1, 2, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] theEnd = new CorviSpawnDataEntry[] {new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 4, 4, MobCategory.MONSTER)};
		CorviSpawnDataEntry[] peaks = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.GOAT.getRegistryName(), 5, 1, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] taiga = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.FOX.getRegistryName(), 8, 2, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] coldPlains = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 10, 2, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.POLAR_BEAR.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 20, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.STRAY.getRegistryName(), 80, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] plains = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 5, 2, 6, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] savanna = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 1, 2, 6, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 1, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		CorviSpawnDataEntry[] windswept = new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.LLAMA.getRegistryName(), 5, 4, 6, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)};
		
		builderSettings.put(Biomes.BADLANDS.location(), new CorviSpawnDataPackage(undergroundOnly, Biomes.BADLANDS.location(), false));
		builderSettings.put(Biomes.BAMBOO_JUNGLE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PARROT.getRegistryName(), 40, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PANDA.getRegistryName(), 80, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.OCELOT.getRegistryName(), 2, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.BAMBOO_JUNGLE.location(), false));
		builderSettings.put(Biomes.BASALT_DELTAS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.GHAST.getRegistryName(), 40, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.MAGMA_CUBE.getRegistryName(), 100, 2, 5, MobCategory.MONSTER)
		}, Biomes.BASALT_DELTAS.location(), false));
		builderSettings.put(Biomes.BEACH.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.TURTLE.getRegistryName(), 5, 2, 5, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.BEACH.location(), false));
		builderSettings.put(Biomes.BIRCH_FOREST.location(), new CorviSpawnDataPackage(birchAndDarkForest, Biomes.BIRCH_FOREST.location(), false));
		builderSettings.put(Biomes.COLD_OCEAN.location(), new CorviSpawnDataPackage(coldOcean, Biomes.COLD_OCEAN.location(), false));
		builderSettings.put(Biomes.CRIMSON_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.ZOMBIFIED_PIGLIN.getRegistryName(), 1, 2, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.HOGLIN.getRegistryName(), 9, 3, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.PIGLIN.getRegistryName(), 5, 3, 4, MobCategory.MONSTER)
		}, Biomes.CRIMSON_FOREST.location(), false));
		builderSettings.put(Biomes.DARK_FOREST.location(), new CorviSpawnDataPackage(birchAndDarkForest, Biomes.DARK_FOREST.location(), false));
		builderSettings.put(Biomes.DEEP_COLD_OCEAN.location(), new CorviSpawnDataPackage(coldOcean, Biomes.DEEP_COLD_OCEAN.location(), false));
		builderSettings.put(Biomes.DEEP_FROZEN_OCEAN.location(), new CorviSpawnDataPackage(frozenOcean, Biomes.DEEP_FROZEN_OCEAN.location(), false));
		builderSettings.put(Biomes.DEEP_OCEAN.location(), new CorviSpawnDataPackage(ocean, Biomes.DEEP_OCEAN.location(), false));
		builderSettings.put(Biomes.DEEP_LUKEWARM_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 8, 3, 6, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.PUFFERFISH.getRegistryName(), 5, 1, 3, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 8, 1, 4, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 2, 1, 2, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DEEP_LUKEWARM_OCEAN.location(), false));
		builderSettings.put(Biomes.DESERT.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 19, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 1, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.HUSK.getRegistryName(), 80, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DESERT.location(), false));
		builderSettings.put(Biomes.DRIPSTONE_CAVES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.DRIPSTONE_CAVES.location(), false));
		builderSettings.put(Biomes.END_BARRENS.location(), new CorviSpawnDataPackage(theEnd, Biomes.END_BARRENS.location(), false));
		builderSettings.put(Biomes.END_MIDLANDS.location(), new CorviSpawnDataPackage(theEnd, Biomes.END_MIDLANDS.location(), false));
		builderSettings.put(Biomes.END_HIGHLANDS.location(), new CorviSpawnDataPackage(theEnd, Biomes.END_HIGHLANDS.location(), false));
		builderSettings.put(Biomes.ERODED_BADLANDS.location(), new CorviSpawnDataPackage(undergroundOnly, Biomes.ERODED_BADLANDS.location(), false));
		builderSettings.put(Biomes.FLOWER_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FLOWER_FOREST.location(), false));
		builderSettings.put(Biomes.FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 5, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FOREST.location(), false));
		builderSettings.put(Biomes.FROZEN_OCEAN.location(), new CorviSpawnDataPackage(frozenOcean, Biomes.FROZEN_OCEAN.location(), false));
		builderSettings.put(Biomes.FROZEN_PEAKS.location(), new CorviSpawnDataPackage(peaks, Biomes.FROZEN_PEAKS.location(), false));
		builderSettings.put(Biomes.FROZEN_RIVER.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 1, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 5, 1, 5, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 2, 1, 4, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.FROZEN_RIVER.location(), false));
		builderSettings.put(Biomes.GROVE.location(), new CorviSpawnDataPackage(taiga, Biomes.GROVE.location(), false));
		builderSettings.put(Biomes.ICE_SPIKES.location(), new CorviSpawnDataPackage(coldPlains, Biomes.ICE_SPIKES.location(), false));
		builderSettings.put(Biomes.JAGGED_PEAKS.location(), new CorviSpawnDataPackage(peaks, Biomes.JAGGED_PEAKS.location(), false));
		builderSettings.put(Biomes.JUNGLE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PARROT.getRegistryName(), 40, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PANDA.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.OCELOT.getRegistryName(), 2, 1, 3, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.JUNGLE.location(), false));
		builderSettings.put(Biomes.LUKEWARM_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.COD.getRegistryName(), 15, 3, 6, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.PUFFERFISH.getRegistryName(), 5, 1, 3, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 10, 1, 2, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 2, 1, 2, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.LUKEWARM_OCEAN.location(), false));
		builderSettings.put(Biomes.LUSH_CAVES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.AXOLOTL.getRegistryName(), 10, 4, 6, MobCategory.AXOLOTLS)
		}, Biomes.LUSH_CAVES.location(), false));
		builderSettings.put(Biomes.MEADOW.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 2, 2, 6, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 2, 2, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.MEADOW.location(), false));
		builderSettings.put(Biomes.MUSHROOM_FIELDS.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.MOOSHROOM.getRegistryName(), 8, 4, 8, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.MUSHROOM_FIELDS.location(), false));
		builderSettings.put(Biomes.NETHER_WASTES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.GHAST.getRegistryName(), 50, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIFIED_PIGLIN.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.MAGMA_CUBE.getRegistryName(), 2, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 1, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.PIGLIN.getRegistryName(), 15, 4, 4, MobCategory.MONSTER)
		}, Biomes.NETHER_WASTES.location(), false));
		builderSettings.put(Biomes.OCEAN.location(), new CorviSpawnDataPackage(ocean, Biomes.OCEAN.location(), false));
		builderSettings.put(Biomes.OLD_GROWTH_BIRCH_FOREST.location(), new CorviSpawnDataPackage(birchAndDarkForest, Biomes.OLD_GROWTH_BIRCH_FOREST.location(), false));
		builderSettings.put(Biomes.OLD_GROWTH_PINE_TAIGA.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.WOLF.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.FOX.getRegistryName(), 8, 2, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 25, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.OLD_GROWTH_PINE_TAIGA.location(), false));
		builderSettings.put(Biomes.OLD_GROWTH_SPRUCE_TAIGA.location(), new CorviSpawnDataPackage(taiga, Biomes.OLD_GROWTH_SPRUCE_TAIGA.location(), false));
		builderSettings.put(Biomes.PLAINS.location(), new CorviSpawnDataPackage(plains, Biomes.PLAINS.location(), false));
		builderSettings.put(Biomes.RIVER.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 100, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SALMON.getRegistryName(), 5, 1, 5, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 2, 1, 4, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.RIVER.location(), false));
		builderSettings.put(Biomes.SAVANNA.location(), new CorviSpawnDataPackage(savanna, Biomes.SAVANNA.location(), false));
		builderSettings.put(Biomes.SAVANNA_PLATEAU.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.HORSE.getRegistryName(), 1, 2, 6, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.DONKEY.getRegistryName(), 1, 1, 1, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.LLAMA.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SAVANNA_PLATEAU.location(), false));
		builderSettings.put(Biomes.SMALL_END_ISLANDS.location(), new CorviSpawnDataPackage(theEnd, Biomes.SMALL_END_ISLANDS.location(), false));
		builderSettings.put(Biomes.SNOWY_BEACH.location(), new CorviSpawnDataPackage(undergroundOnly, Biomes.SNOWY_BEACH.location(), false));
		builderSettings.put(Biomes.SNOWY_PLAINS.location(), new CorviSpawnDataPackage(coldPlains, Biomes.SNOWY_PLAINS.location(), false));
		builderSettings.put(Biomes.SNOWY_SLOPES.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.RABBIT.getRegistryName(), 4, 2, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.GOAT.getRegistryName(), 5, 1, 3, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SNOWY_SLOPES.location(), false));
		builderSettings.put(Biomes.SNOWY_TAIGA.location(), new CorviSpawnDataPackage(taiga, Biomes.SNOWY_TAIGA.location(), false));
		builderSettings.put(Biomes.SOUL_SAND_VALLEY.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 20, 5, 5, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GHAST.getRegistryName(), 50, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 1, 4, 4, MobCategory.MONSTER)
		}, Biomes.SOUL_SAND_VALLEY.location(), false));
		builderSettings.put(Biomes.SPARSE_JUNGLE.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SPARSE_JUNGLE.location(), false));
		builderSettings.put(Biomes.STONY_PEAKS.location(), new CorviSpawnDataPackage(undergroundOnly, Biomes.STONY_PEAKS.location(), false));
		builderSettings.put(Biomes.STONY_SHORE.location(), new CorviSpawnDataPackage(undergroundOnly, Biomes.STONY_SHORE.location(), false));
		builderSettings.put(Biomes.SUNFLOWER_PLAINS.location(), new CorviSpawnDataPackage(plains, Biomes.SUNFLOWER_PLAINS.location(), false));
		builderSettings.put(Biomes.SWAMP.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.SHEEP.getRegistryName(), 12, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.PIG.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.CHICKEN.getRegistryName(), 10, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.COW.getRegistryName(), 8, 4, 4, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 1, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.SWAMP.location(), false));
		builderSettings.put(Biomes.TAIGA.location(), new CorviSpawnDataPackage(taiga, Biomes.TAIGA.location(), false));
		builderSettings.put(Biomes.THE_END.location(), new CorviSpawnDataPackage(theEnd, Biomes.THE_END.location(), false));
		builderSettings.put(Biomes.WARM_OCEAN.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.BAT.getRegistryName(), 10, 8, 8, MobCategory.AMBIENT),
			new CorviSpawnDataEntry(EntityType.DROWNED.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SPIDER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE.getRegistryName(), 95, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ZOMBIE_VILLAGER.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SKELETON.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.CREEPER.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.SLIME.getRegistryName(), 100, 4, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 10, 1, 4, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.WITCH.getRegistryName(), 5, 1, 1, MobCategory.MONSTER),
			new CorviSpawnDataEntry(EntityType.PUFFERFISH.getRegistryName(), 15, 1, 3, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.TROPICAL_FISH.getRegistryName(), 25, 8, 8, MobCategory.WATER_AMBIENT),
			new CorviSpawnDataEntry(EntityType.SQUID.getRegistryName(), 10, 4, 4, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.DOLPHIN.getRegistryName(), 2, 1, 2, MobCategory.WATER_CREATURE),
			new CorviSpawnDataEntry(EntityType.GLOW_SQUID.getRegistryName(), 10, 4, 6, MobCategory.UNDERGROUND_WATER_CREATURE)
		}, Biomes.WARM_OCEAN.location(), false));
		builderSettings.put(Biomes.WARPED_FOREST.location(), new CorviSpawnDataPackage(new CorviSpawnDataEntry[] {
			new CorviSpawnDataEntry(EntityType.STRIDER.getRegistryName(), 60, 1, 2, MobCategory.CREATURE),
			new CorviSpawnDataEntry(EntityType.ENDERMAN.getRegistryName(), 1, 4, 4, MobCategory.MONSTER)
		}, Biomes.WARPED_FOREST.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_FOREST.location(), new CorviSpawnDataPackage(windswept, Biomes.WINDSWEPT_FOREST.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_GRAVELLY_HILLS.location(), new CorviSpawnDataPackage(windswept, Biomes.WINDSWEPT_GRAVELLY_HILLS.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_HILLS.location(), new CorviSpawnDataPackage(windswept, Biomes.WINDSWEPT_HILLS.location(), false));
		builderSettings.put(Biomes.WINDSWEPT_SAVANNA.location(), new CorviSpawnDataPackage(savanna, Biomes.WINDSWEPT_SAVANNA.location(), false));
		builderSettings.put(Biomes.WOODED_BADLANDS.location(), new CorviSpawnDataPackage(undergroundOnly, Biomes.WOODED_BADLANDS.location(), false));
		
    	return new CorviSpawnsManager(MODID, builderSettings.build(), new EntityType<?>[] {EntityType.AXOLOTL, EntityType.BAT, EntityType.BEE, EntityType.BLAZE,
    		EntityType.CAT, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY,
    		EntityType.DROWNED, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX, EntityType.GHAST, EntityType.GLOW_SQUID, EntityType.GOAT,
    		EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HORSE, EntityType.HUSK, EntityType.ILLUSIONER, EntityType.IRON_GOLEM, EntityType.LLAMA,
    		EntityType.MAGMA_CUBE, EntityType.MOOSHROOM, EntityType.MULE, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PHANTOM, EntityType.PIG,
    		EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.POLAR_BEAR, EntityType.PUFFERFISH, EntityType.RABBIT, EntityType.RAVAGER,
    		EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.SLIME,
    		EntityType.SNOW_GOLEM, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.STRIDER, EntityType.TROPICAL_FISH, EntityType.TURTLE,
    		EntityType.VEX, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.WOLF, EntityType.ZOGLIN, EntityType.ZOMBIE,
    		EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN}, true);
    }
}
