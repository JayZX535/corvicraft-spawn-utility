package com.corvicraft.corvicraftspawns;

import com.corvicraft.corvicraftspawns.config.CorvicraftConfig;
import com.corvicraft.corvicraftspawns.spawnconfig.CorvicraftSpawnEntry;
import com.corvicraft.corvicraftspawns.spawnconfig.CorvicraftSpawnHandler;
import com.corvicraft.corvicraftspawns.spawnconfig.CorvicraftSpawnSet;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
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
    //private static final CorviSpawnsManager VANILLA_SPAWNS = getNewCorviCraftSpawnsVanilla();
    
    private static final CorvicraftSpawnHandler HANDLER = buildSpawns();

    public CorviCraftSpawns(){
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::addSpawn);
        
        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CorvicraftConfig.SPEC, MODID + "-common.toml");
    }
    
    /**
	 * Biome load event
	 * Used to add spawns for vanilla mobs
	 */
    public void addSpawn(BiomeLoadingEvent eventIn) {
    	HANDLER.addSpawns(eventIn);
	}
    
    /**
     * Event handler for mod events-- NOT forge events!
     */
    @Mod.EventBusSubscriber(modid = CorviCraftSpawns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class CorviCraftEvents {
    	
    	@SubscribeEvent
    	 public static void loadConfig(ModConfigEvent.Loading eventIn) {
    		HANDLER.loadSpawns();
    	 }
    }
    
    public static Path getConfigPath() {return configPath;}
    public static Logger getLogger() {return LOGGER;}
    
    @SuppressWarnings("unchecked")
	private static CorvicraftSpawnHandler buildSpawns() {
    	CorvicraftSpawnEntry[] darkDefaults = new CorvicraftSpawnEntry[] {
    		new CorvicraftSpawnEntry.Builder(EntityType.BAT).withWeight(10).withPackSize(8).withSpawnCategory(MobCategory.AMBIENT).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.SPIDER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE).withWeight(95).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE_VILLAGER).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.SKELETON).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.CREEPER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.SLIME).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(10).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.WITCH).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.GLOW_SQUID).withWeight(10).withPackMin(4).withPackMax(6).withSpawnCategory(MobCategory.UNDERGROUND_WATER_CREATURE).build()
    	};
    	CorvicraftSpawnEntry[] passiveDefaults = new CorvicraftSpawnEntry[] {
    		new CorvicraftSpawnEntry.Builder(EntityType.SHEEP).withWeight(12).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.PIG).withWeight(10).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.CHICKEN).withWeight(10).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build(),
    		new CorvicraftSpawnEntry.Builder(EntityType.COW).withWeight(8).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build()
    	};
    	
    	CorvicraftSpawnSet darkSet = new CorvicraftSpawnSet.Builder("dark_only").withSpawns(darkDefaults).replaceExisting(true).build();
    	CorvicraftSpawnSet birchAndDarkForest = new CorvicraftSpawnSet.Builder("birch_and_dark_forest").withSpawns(passiveDefaults).withSpawns(darkDefaults).replaceExisting(true).build();
    	CorvicraftSpawnSet coldOcean = new CorvicraftSpawnSet.Builder("cold_ocean").withSpawns(darkDefaults)
    		.withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
    	    	new CorvicraftSpawnEntry.Builder(EntityType.COD).withWeight(15).withPackMin(3).withPackMax(6).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.SALMON).withWeight(15).withPackMin(1).withPackMax(5).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
    	    	new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(3).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.WATER_CREATURE).build()
    		).replaceExisting(true).build();
    	CorvicraftSpawnSet frozenOcean = new CorvicraftSpawnSet.Builder("frozen_ocean").withSpawns(darkDefaults).withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.POLAR_BEAR).withWeight(1).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SALMON).withWeight(15).withPackMin(1).withPackMax(5).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(1).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.WATER_CREATURE).build()
        	).replaceExisting(true).build();
    	CorvicraftSpawnSet ocean = new CorvicraftSpawnSet.Builder("ocean").withSpawns(darkDefaults).withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.COD).withWeight(10).withPackMin(3).withPackMax(6).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(1).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.WATER_CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.DOLPHIN).withWeight(1).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.WATER_CREATURE).build()).replaceExisting(true).build();
    	CorvicraftSpawnSet theEnd = new CorvicraftSpawnSet.Builder("the_end").withSpawns(new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(10).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build()).replaceExisting(true).build();
    	CorvicraftSpawnSet peaks = new CorvicraftSpawnSet.Builder("peaks").withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.GOAT).withWeight(5).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build();
    	CorvicraftSpawnSet taiga = new CorvicraftSpawnSet.Builder("taiga").withSpawns(passiveDefaults).withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.WOLF).withWeight(8).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.RABBIT).withWeight(4).withPackMin(2).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.FOX).withWeight(8).withPackMin(2).withPackMax(4).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build();
    	CorvicraftSpawnSet coldPlains = new CorvicraftSpawnSet.Builder("cold_plains").withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.RABBIT).withWeight(10).withPackMin(2).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.POLAR_BEAR).withWeight(1).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.BAT).withWeight(10).withPackSize(8).withSpawnCategory(MobCategory.AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SPIDER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE).withWeight(95).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE_VILLAGER).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SKELETON).withWeight(20).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.CREEPER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SLIME).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(10).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.WITCH).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.STRAY).withWeight(80).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.GLOW_SQUID).withWeight(10).withPackMin(4).withPackMax(6).withSpawnCategory(MobCategory.UNDERGROUND_WATER_CREATURE).build()
        	).replaceExisting(true).build();
    	CorvicraftSpawnSet plains = new CorvicraftSpawnSet.Builder("plains").withSpawns(passiveDefaults).withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.HORSE).withWeight(5).withPackMin(2).withPackMax(6).withSpawnCategory(MobCategory.CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.DONKEY).withWeight(1).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build()
            ).withSpawns(darkDefaults).replaceExisting(true).build();
    	CorvicraftSpawnSet savanna = new CorvicraftSpawnSet.Builder("savanna").withSpawns(passiveDefaults).withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.HORSE).withWeight(1).withPackMin(2).withPackMax(6).withSpawnCategory(MobCategory.CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.DONKEY).withWeight(1).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build()
            ).withSpawns(darkDefaults).replaceExisting(true).build();
    	CorvicraftSpawnSet windswept = new CorvicraftSpawnSet.Builder("windswept").withSpawns(passiveDefaults).withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.LLAMA).withWeight(5).withPackMin(4).withPackMax(6).withSpawnCategory(MobCategory.CREATURE).build()
            ).withSpawns(darkDefaults).replaceExisting(true).build();
    	
    	CorvicraftSpawnHandler.Builder spawnBuilder = new CorvicraftSpawnHandler.Builder(MODID)
    		.withSpawnSet(darkSet, Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.SNOWY_BEACH, Biomes.STONY_PEAKS, Biomes.STONY_SHORE, Biomes.WOODED_BADLANDS)
    		.withSpawnSet(birchAndDarkForest, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST)
    		.withSpawnSet(coldOcean, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN)
    		.withSpawnSet(frozenOcean, Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN)
    		.withSpawnSet(ocean, Biomes.DEEP_OCEAN, Biomes.OCEAN)
    		.withSpawnSet(theEnd, Biomes.END_BARRENS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS, Biomes.SMALL_END_ISLANDS, Biomes.THE_END)
    		.withSpawnSet(peaks, Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS)
    		.withSpawnSet(taiga, Biomes.GROVE, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA, Biomes.TAIGA)
    		.withSpawnSet(coldPlains, Biomes.ICE_SPIKES, Biomes.SNOWY_PLAINS)
    		.withSpawnSet(plains, Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS)
    		.withSpawnSet(savanna, Biomes.SAVANNA, Biomes.WINDSWEPT_SAVANNA)
    		.withSpawnSet(windswept, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("bamboo_jungle").withSpawns(passiveDefaults).withSpawns(
    	    	new CorvicraftSpawnEntry.Builder(EntityType.CHICKEN).withWeight(10).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.PARROT).withWeight(40).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.PANDA).withWeight(80).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.OCELOT).withWeight(2).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build()
            ).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.BAMBOO_JUNGLE)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("basalt_deltas").withSpawns(
                new CorvicraftSpawnEntry.Builder(EntityType.STRIDER).withWeight(60).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.GHAST).withWeight(40).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.MAGMA_CUBE).withWeight(100).withPackMin(2).withPackMax(5).withSpawnCategory(MobCategory.MONSTER).build()
    		).replaceExisting(true).build(), Biomes.BASALT_DELTAS)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("beach").withSpawns(
                new CorvicraftSpawnEntry.Builder(EntityType.TURTLE).withWeight(5).withPackMin(2).withPackMax(5).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.BEACH)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("crimson_forest").withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.STRIDER).withWeight(60).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIFIED_PIGLIN).withWeight(1).withPackMin(2).withPackMax(4).withSpawnCategory(MobCategory.MONSTER).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.HOGLIN).withWeight(9).withPackMin(3).withPackMax(4).withSpawnCategory(MobCategory.MONSTER).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.PIGLIN).withWeight(5).withPackMin(3).withPackMax(4).withSpawnCategory(MobCategory.MONSTER).build()
    		).replaceExisting(true).build(), Biomes.CRIMSON_FOREST)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("deep_lukewarm_ocean").withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
    	    	new CorvicraftSpawnEntry.Builder(EntityType.COD).withWeight(8).withPackMin(3).withPackMax(6).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.PUFFERFISH).withWeight(5).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.TROPICAL_FISH).withWeight(25).withPackSize(8).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
	    		new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(8).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.WATER_CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.DOLPHIN).withWeight(2).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.WATER_CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.DEEP_LUKEWARM_OCEAN)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("desert").withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.RABBIT).withWeight(4).withPackMin(2).withPackMax(3).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.BAT).withWeight(10).withPackSize(8).withSpawnCategory(MobCategory.AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SPIDER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE).withWeight(19).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE_VILLAGER).withWeight(1).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SKELETON).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.CREEPER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SLIME).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(10).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.WITCH).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.HUSK).withWeight(80).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.GLOW_SQUID).withWeight(10).withPackMin(4).withPackMax(6).withSpawnCategory(MobCategory.UNDERGROUND_WATER_CREATURE).build()
            ).replaceExisting(true).build(), Biomes.DESERT)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("dripstone_caves").withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(95).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build()
            ).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.DRIPSTONE_CAVES)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("flower_forest").withSpawns(passiveDefaults).withSpawns(
                new CorvicraftSpawnEntry.Builder(EntityType.RABBIT).withWeight(4).withPackMin(2).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.FLOWER_FOREST)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("forest").withSpawns(passiveDefaults).withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.WOLF).withWeight(5).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.FOREST)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("frozen_river").withSpawns(darkDefaults).withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(1).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SALMON).withWeight(5).withPackMin(1).withPackMax(5).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(2).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.WATER_CREATURE).build()
        	).replaceExisting(true).build(), Biomes.FROZEN_RIVER)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("jungle").withSpawns(passiveDefaults).withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.CHICKEN).withWeight(10).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.PARROT).withWeight(40).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.PANDA).withWeight(1).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.OCELOT).withWeight(2).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.MONSTER).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.JUNGLE)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("lukewarm_ocean").withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
    	    	new CorvicraftSpawnEntry.Builder(EntityType.COD).withWeight(15).withPackMin(3).withPackMax(6).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.PUFFERFISH).withWeight(5).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.TROPICAL_FISH).withWeight(25).withPackSize(8).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
    	    	new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(10).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.WATER_CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.DOLPHIN).withWeight(2).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.WATER_CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.LUKEWARM_OCEAN)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("lush_caves").withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.TROPICAL_FISH).withWeight(25).withPackSize(8).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.AXOLOTL).withWeight(10).withPackMin(4).withPackMax(6).withSpawnCategory(MobCategory.AXOLOTLS).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.LUSH_CAVES)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("meadow").withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.DONKEY).withWeight(1).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.RABBIT).withWeight(2).withPackMin(2).withPackMax(6).withSpawnCategory(MobCategory.CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SHEEP).withWeight(2).withPackMin(2).withPackMax(4).withSpawnCategory(MobCategory.CREATURE).build()
        	).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.MEADOW)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("mushroom_fields").withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.MOOSHROOM).withWeight(8).withPackMin(4).withPackMax(8).withSpawnCategory(MobCategory.CREATURE).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.BAT).withWeight(10).withPackSize(8).withSpawnCategory(MobCategory.AMBIENT).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.GLOW_SQUID).withWeight(10).withPackMin(4).withPackMax(6).withSpawnCategory(MobCategory.UNDERGROUND_WATER_CREATURE).build()
            ).replaceExisting(true).build(), Biomes.MUSHROOM_FIELDS)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("nether_wastes").withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.STRIDER).withWeight(60).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.GHAST).withWeight(50).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIFIED_PIGLIN).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.MAGMA_CUBE).withWeight(2).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(1).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.PIGLIN).withWeight(15).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build()
    		).replaceExisting(true).build(), Biomes.NETHER_WASTES)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("old_growth_pine_taiga").withSpawns(passiveDefaults).withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.WOLF).withWeight(8).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.RABBIT).withWeight(4).withPackMin(2).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.FOX).withWeight(8).withPackMin(2).withPackMax(4).withSpawnCategory(MobCategory.CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.BAT).withWeight(10).withPackSize(8).withSpawnCategory(MobCategory.AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SPIDER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ZOMBIE_VILLAGER).withWeight(25).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SKELETON).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.CREEPER).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SLIME).withWeight(100).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(10).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.WITCH).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.GLOW_SQUID).withWeight(10).withPackMin(4).withPackMax(6).withSpawnCategory(MobCategory.UNDERGROUND_WATER_CREATURE).build()
            ).replaceExisting(true).build(), Biomes.OLD_GROWTH_PINE_TAIGA)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("river").withSpawns(
            	new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(100).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.SALMON).withWeight(5).withPackMin(1).withPackMax(5).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(2).withPackMin(1).withPackMax(4).withSpawnCategory(MobCategory.WATER_CREATURE).build()
            ).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.RIVER)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("savanna_plateau").withSpawns(passiveDefaults).withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.HORSE).withWeight(1).withPackMin(2).withPackMax(6).withSpawnCategory(MobCategory.CREATURE).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.DONKEY).withWeight(1).withPackSize(1).withSpawnCategory(MobCategory.CREATURE).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.LLAMA).withWeight(8).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.SAVANNA_PLATEAU)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("snowy_slopes").withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.RABBIT).withWeight(4).withPackMin(2).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.GOAT).withWeight(5).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.SNOWY_SLOPES)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("soul_sand_valley").withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.STRIDER).withWeight(60).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SKELETON).withWeight(20).withPackSize(5).withSpawnCategory(MobCategory.MONSTER).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.GHAST).withWeight(50).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build(),
            	new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(1).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build()
        	).replaceExisting(true).build(), Biomes.SOUL_SAND_VALLEY)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("sparse_jungle").withSpawns(passiveDefaults).withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.CHICKEN).withWeight(10).withPackSize(4).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.SPARSE_JUNGLE)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("swamp").withSpawns(passiveDefaults).withSpawns(
    			new CorvicraftSpawnEntry.Builder(EntityType.SLIME).withWeight(1).withPackSize(1).withSpawnCategory(MobCategory.CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.SWAMP)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("warm_ocean").withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.DROWNED).withWeight(5).withPackSize(1).withSpawnCategory(MobCategory.MONSTER).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.PUFFERFISH).withWeight(15).withPackMin(1).withPackMax(3).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.TROPICAL_FISH).withWeight(25).withPackSize(8).withSpawnCategory(MobCategory.WATER_AMBIENT).build(),
        		new CorvicraftSpawnEntry.Builder(EntityType.SQUID).withWeight(10).withPackSize(4).withSpawnCategory(MobCategory.WATER_CREATURE).build(),
    			new CorvicraftSpawnEntry.Builder(EntityType.DOLPHIN).withWeight(2).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.WATER_CREATURE).build()
    		).withSpawns(darkDefaults).replaceExisting(true).build(), Biomes.WARM_OCEAN)
    		.withSpawnSet(new CorvicraftSpawnSet.Builder("warped_forest").withSpawns(
        		new CorvicraftSpawnEntry.Builder(EntityType.STRIDER).withWeight(60).withPackMin(1).withPackMax(2).withSpawnCategory(MobCategory.CREATURE).build(),
                new CorvicraftSpawnEntry.Builder(EntityType.ENDERMAN).withWeight(1).withPackSize(4).withSpawnCategory(MobCategory.MONSTER).build()
            ).replaceExisting(true).build(), Biomes.WARPED_FOREST)
    		.withEntityTypes(EntityType.AXOLOTL, EntityType.BAT, EntityType.BEE, EntityType.BLAZE,
    			EntityType.CAT, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY,
    			EntityType.DROWNED, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX, EntityType.GHAST, EntityType.GLOW_SQUID, 
    			EntityType.GOAT, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HORSE, EntityType.HUSK, EntityType.ILLUSIONER, EntityType.IRON_GOLEM, 
    			EntityType.LLAMA, EntityType.MAGMA_CUBE, EntityType.MOOSHROOM, EntityType.MULE, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, 
    			EntityType.PHANTOM, EntityType.PIG, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.POLAR_BEAR, EntityType.PUFFERFISH, 
    			EntityType.RABBIT, EntityType.RAVAGER, EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, 
    			EntityType.SKELETON_HORSE, EntityType.SLIME, EntityType.SNOW_GOLEM, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.STRIDER, 
    			EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.WOLF, 
    			EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN);
    	return spawnBuilder.build();
    }
}
