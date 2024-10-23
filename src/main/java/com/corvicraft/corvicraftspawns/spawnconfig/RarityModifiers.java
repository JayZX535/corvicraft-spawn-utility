package com.corvicraft.corvicraftspawns.spawnconfig;

import java.util.Optional;

import com.google.gson.JsonObject;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;

public class RarityModifiers {
	
	public static final RarityModifiers NONE = new RarityModifiers(1D, MoonRarity.NONE, WeatherRarity.NONE);
	
	public static final String BASE_MODIFIER = "base_rarity";
	public static final String MOON_MODIFIER = "moon";
	public static final String WEATHER_MODIFIER = "weather";

	public final double baseRarity;
	public final MoonRarity moonRarity;
	public final WeatherRarity weatherRarity;
	
	public RarityModifiers(double baseRarityIn, MoonRarity moonRarityIn, WeatherRarity weatherRarityIn) {
		this.baseRarity = baseRarityIn;
		this.moonRarity = moonRarityIn;
		this.weatherRarity = weatherRarityIn;
	}

	public double getLocalRarity(ServerLevel serverLevelIn, BlockPos spawnPosIn) {
		double localRarity = this.baseRarity;
		if (serverLevelIn != null) {
			localRarity *= this.moonRarity.getRarityForPhase(serverLevelIn.getMoonPhase());
			localRarity *= this.weatherRarity.getRarityForWeather(serverLevelIn);
		}
		return localRarity;
	}
	
	public static RarityModifiers readJson(JsonObject jsonIn) {
		double base = Mth.clamp(GsonHelper.getAsDouble(jsonIn, BASE_MODIFIER, 1.0), 0D, 1D);
		MoonRarity moonRarity = jsonIn.has(MOON_MODIFIER) ? MoonRarity.readJson(GsonHelper.getAsJsonObject(jsonIn, MOON_MODIFIER)):  MoonRarity.NONE;
		WeatherRarity weatherRarity = jsonIn.has(WEATHER_MODIFIER) ? WeatherRarity.readJson(GsonHelper.getAsJsonObject(jsonIn, WEATHER_MODIFIER)):  WeatherRarity.NONE;
		return new RarityModifiers(base, moonRarity, weatherRarity);
	}
	
	public void toJson(JsonObject jsonIn) {
		jsonIn.addProperty(BASE_MODIFIER, this.baseRarity);
		Optional<JsonObject> moon = this.moonRarity.getAsJson();
		if (!moon.isEmpty()) jsonIn.add(MOON_MODIFIER, moon.get());
		Optional<JsonObject> weather = this.weatherRarity.getAsJson();
		if (!weather.isEmpty()) jsonIn.add(WEATHER_MODIFIER, weather.get());
	}
	
	public static double getMidpointRarity(double firstRarityIn, double secondRarityIn) {
		return firstRarityIn > secondRarityIn ? ((firstRarityIn - secondRarityIn) / 2) + secondRarityIn
			: firstRarityIn < secondRarityIn ? ((secondRarityIn - firstRarityIn) / 2) + firstRarityIn : firstRarityIn;
	}
	
	public String getDebugString() { return "[Base Rarity: " + this.baseRarity + "], " + this.moonRarity.getDebugString() + ", " + this.weatherRarity.getDebugString(); }
	
	public static class Builder {
		
		protected double base = 1.0;
		protected MoonRarity moon = MoonRarity.NONE;
		protected WeatherRarity weather = WeatherRarity.NONE;
		
		public Builder withBaseRarity(double rarityIn) {
			this.base = Mth.clamp(rarityIn, 0D, 1D);
			return this;
		}
		public Builder withMoonRarity(MoonRarity moonRarityIn) {
			if (moonRarityIn != null) this.moon = moonRarityIn;
			return this;
		}
		
		public Builder withWeatherRarity(WeatherRarity weatherRarityIn) {
			if (weatherRarityIn != null) this.weather = weatherRarityIn;
			return this;
		}
		
		public RarityModifiers build() { return new RarityModifiers(this.base, this.moon, this.weather); }
	}
	
	public record MoonRarity(double fullMoon, double waningGibbous, double lastQuarter, double waningCrescent, double newMoon, double waxingCrescent, double firstQuarter, double waxingGibbous) {
		
		public static final MoonRarity NONE = new MoonRarity(1D, 1D, 1D, 1D, 1D, 1D, 1D, 1D);
		
		public double getRarityForPhase(int phaseIn) {
			switch(phaseIn) {
				default: return this.fullMoon;
				case 1: return this.waningGibbous;
				case 2: return this.lastQuarter;
				case 3: return this.waningCrescent;
				case 4: return this.newMoon;
				case 5: return this.waxingCrescent;
				case 6: return this.firstQuarter;
				case 7: return this.waxingGibbous;
			}
		}
		
		public static final MoonRarity readJson(JsonObject jsonIn) {
			double fullMoon = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "full", 1.0), 0D, 1D);
			double newMoon = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "new", 1.0), 0D, 1D);
			double quarterDefault = getMidpointRarity(fullMoon, newMoon);
			double lastQuarter = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "last_quarter", quarterDefault), 0D, 1D);
			double waningGibbous = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "waning_gibbous", getMidpointRarity(fullMoon, lastQuarter)), 0D, 1D);
			double waningCrescent = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "waning_crescent", getMidpointRarity(lastQuarter, newMoon)), 0D, 1D);
			double firstQuarter = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "first_quarter", quarterDefault), 0D, 1D);
			double waxingGibbous = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "waxing_gibbous", getMidpointRarity(fullMoon, firstQuarter)), 0D, 1D);
			double waxingCrescent = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "waxing_crescent", getMidpointRarity(firstQuarter, newMoon)), 0D, 1D);
			return new MoonRarity(fullMoon, waningGibbous, lastQuarter, waningCrescent, newMoon, waxingCrescent, firstQuarter, waxingGibbous);
		}
		
		public Optional<JsonObject> getAsJson() {
			boolean modified = false;
			JsonObject jsonObj = new JsonObject();
			if (this.fullMoon < 1D) {
				jsonObj.addProperty("full", Math.max(this.fullMoon, 0D));
				modified = true;
			}
			if (this.waningGibbous < 1D) {
				jsonObj.addProperty("waning_gibbous", Math.max(this.waningGibbous, 0D));
				modified = true;
			}
			if (this.lastQuarter < 1D) {
				jsonObj.addProperty("last_quarter", Math.max(this.lastQuarter, 0D));
				modified = true;
			}
			if (this.waningCrescent < 1D) {
				jsonObj.addProperty("waning_crescent", Math.max(this.waningCrescent, 0D));
				modified = true;
			}
			if (this.newMoon < 1D) {
				jsonObj.addProperty("new", Math.max(this.newMoon, 0D));
				modified = true;
			}
			if (this.waxingCrescent < 1D) {
				jsonObj.addProperty("waxing_crescent", Math.max(this.waxingCrescent, 0D));
				modified = true;
			}
			if (this.firstQuarter < 1D) {
				jsonObj.addProperty("first_quarter", Math.max(this.firstQuarter, 0D));
				modified = true;
			}
			if (this.waxingGibbous < 1D) {
				jsonObj.addProperty("waxing_gibbous", Math.max(this.waxingGibbous, 0D));
				modified = true;
			}
			return modified ? Optional.of(jsonObj) : Optional.empty();
		}
		
		public String getDebugString() { return "[Moon Rarities: " + this.fullMoon + " (full), " + this.waningGibbous + " (waning gibbous), " 
			+ this.lastQuarter + " (last quarter), " + this.waningCrescent + " (waning crescent), " + this.newMoon + " (new), "
			+ this.waxingCrescent + " (waxing crescent), " + this.firstQuarter + " (first quarter), " + this.waxingGibbous + " (waxing gibbous)]"; }
		
		public static class Builder {
			protected double fullMoon = 1.0D;
			protected double waningGibbous = -1D;
			protected double lastQuarter = -1D;
			protected double waningCrescent = -1D;
			protected double newMoon = 1D;
			protected double waxingCrescent = -1D;
			protected double firstQuarter = -1D;
			protected double waxingGibbous = -1D;
			
			public Builder fullMoon(double rarityIn) {
				this.fullMoon = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			public Builder waningGibbous(double rarityIn) {
				this.waningGibbous = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			public Builder lastQuarter(double rarityIn) {
				this.lastQuarter = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			public Builder waningCrescent(double rarityIn) {
				this.waningCrescent = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			public Builder newMoon(double rarityIn) {
				this.newMoon = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			public Builder waxingCrescent(double rarityIn) {
				this.waxingCrescent = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			public Builder firstQuarter(double rarityIn) {
				this.firstQuarter = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			public Builder waxingGibbous(double rarityIn) {
				this.waxingGibbous = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			
			public MoonRarity build() {
				double adjustedLastQuarter = this.lastQuarter == -1D ? getMidpointRarity(this.fullMoon, this.newMoon) : this.lastQuarter;
				double adjustedFirstQuarter = this.firstQuarter == -1D ? getMidpointRarity(this.fullMoon, this.newMoon) : this.firstQuarter;
				return new MoonRarity(this.fullMoon, this.waningGibbous == -1 ? getMidpointRarity(adjustedLastQuarter, this.fullMoon) : this.waningGibbous, adjustedLastQuarter,
					this.waningCrescent == -1 ? getMidpointRarity(adjustedLastQuarter, this.newMoon) : this.waningCrescent, this.newMoon,
					this.waxingCrescent == -1 ? getMidpointRarity(adjustedFirstQuarter, this.newMoon) : this.waxingCrescent, adjustedFirstQuarter,
					this.waxingGibbous == -1 ? getMidpointRarity(adjustedFirstQuarter, this.fullMoon) : this.waxingGibbous);
			}
		}
	}
	
	public record WeatherRarity(double clear, double rain, double thunderstorm) {
		public static final WeatherRarity NONE = new WeatherRarity(1D, 1D, 1D);
		
		public double getRarityForWeather(ServerLevel serverLevelIn) {
			if (serverLevelIn.isThundering()) return this.thunderstorm;
			else if (serverLevelIn.isRaining()) return this.rain;
			else return this.clear;
		}
		
		public double getRarityForWeather(ServerLevel serverLevelIn, BlockPos spawnPosIn) {
			if (serverLevelIn.isThundering()) return this.thunderstorm;
			else if (serverLevelIn.isRainingAt(spawnPosIn)) return this.rain;
			else return this.clear;
		}
		
		public static final WeatherRarity readJson(JsonObject jsonIn) {
			double clear = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "clear", 1.0), 0D, 1D);
			double rain = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "rain", 1.0), 0D, 1D);
			double thunderstorm = Mth.clamp(GsonHelper.getAsDouble(jsonIn, "thunderstorm", rain), 0D, 1D);
			return new WeatherRarity(clear, rain, thunderstorm);
		}
		
		public Optional<JsonObject> getAsJson() {
			boolean modified = false;
			JsonObject jsonObj = new JsonObject();
			if (this.clear < 1D) {
				jsonObj.addProperty("clear", Math.max(this.clear, 0D));
				modified = true;
			}
			if (this.rain < 1D) {
				jsonObj.addProperty("rain", Math.max(this.rain, 0D));
				modified = true;
			}
			if (this.thunderstorm < 1D) {
				jsonObj.addProperty("thunderstorm", Math.max(this.thunderstorm, 0D));
				modified = true;
			}
			return modified ? Optional.of(jsonObj) : Optional.empty();
		}
		
		public String getDebugString() { return "[Weather Rarities: " + this.clear + " (clear), " + this.rain + " (rain), " + this.thunderstorm + " (thunderstorm)]"; }
		
		public static class Builder {
			protected double clear = 1D;
			protected double rain = 1D;
			protected double thunderstorm = -1D;
			
			public Builder clear(double rarityIn) {
				this.clear = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			
			public Builder rain(double rarityIn) {
				this.rain = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			
			public Builder thunderstorm(double rarityIn) {
				this.thunderstorm = Mth.clamp(rarityIn, 0D, 1D);
				return this;
			}
			
			public WeatherRarity build() {
				return new WeatherRarity(this.clear, this.rain, this.thunderstorm == -1 ? this.rain : this.thunderstorm);
			}
		}
	}
}
