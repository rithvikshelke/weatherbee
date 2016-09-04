package org.weatherbee.util

import java.util.concurrent.ThreadLocalRandom

import scala.io.Source
import scala.util.Random

/**
  *
  * This utility computes weather parameters like visibility, precipitation, sea level pressure and the weather summary
  */
object WeatherUtility {

  /**
    * This method computes the visibility from elevation and temperature
    * Increased temperature increases the visibility
    *
    * @param elevation
    * @param temperature
    * @return
    */
  def computeVisibilityFromElevationAndTemperature(elevation: Double, temperature: Double): Double = {
    var minHorizonVisibility = 1
    var maxHorizonVisibility = 10
    if (elevation < 500.0) {
      maxHorizonVisibility = 5
      if (temperature > 30) {
        maxHorizonVisibility = 3
      }
    } else {
      minHorizonVisibility = 4
      if (temperature > 30) {
        maxHorizonVisibility = 8
      }
    }
    val generated = ThreadLocalRandom.current().nextDouble(minHorizonVisibility, maxHorizonVisibility)
    generated
  }

  /**
    * This method calculated the precipitation based on the season and the probability of occurance of rain
    *
    * @param city
    * @param seasonIndex
    * @return
    */
  def generatePrecipitationBasedOnProbability(city: String, seasonIndex: Int): Double = {
    val arr: Array[String] = Source.fromFile("data/" + city + ".txt").getLines().toArray
    val averageRainfall = arr(1).split(",")(seasonIndex).split("\\|")(0).toDouble
    val probabilityOfRain = arr(1).split(",")(seasonIndex).split("\\|")(1).toDouble
    val randomNumber = ThreadLocalRandom.current().nextDouble(0.0, 1.0)
    if (randomNumber <= probabilityOfRain) {
      ThreadLocalRandom.current().nextDouble(0, averageRainfall)
    } else {
      0.0
    }
  }

  /** *
    * This method find the sea level pressure from the elevation varying the max and min sealevel pressure
    *
    * @param elevation
    * @return seaLevelPressure
    */
  def computeSeaLevelPressureFromElevation(elevation: Double): Integer = {
    var minimumSeaLevelPressure = 950
    var maximumSeaLevelPressure = 1030
    if (elevation < 20.0) {
      ThreadLocalRandom.current().nextInt(minimumSeaLevelPressure + 50, maximumSeaLevelPressure)
    } else if (elevation < 500.0) {
      ThreadLocalRandom.current().nextInt(minimumSeaLevelPressure, maximumSeaLevelPressure)
    } else {
      ThreadLocalRandom.current().nextInt(minimumSeaLevelPressure, maximumSeaLevelPressure - 25)
    }

  }

  /** *
    * This method finds the weather summary based on the essential weather parameters
    *
    * @param temp
    * @param dewPoint
    * @param precipitation
    * @param humidity
    * @param visibility
    * @return weatherSummary - Summary of the weather in a single word based on the above factors
    */
  def getWeatherTypeFromWeatherDetails(temp: Double, dewPoint: Double, precipitation: Double, humidity: Double, visibility: Double): String = {
    if (precipitation > 0.0 && temp < 4.0) {
      "Snow"
    }
    else if (precipitation >= 100.00) {
      "Thunder Storms"
    } else if (precipitation > 50.00) {
      "Showers"
    } else if (precipitation > 0.0) {
      "Drizzle"
    } else if (humidity > 70 && temp >= 28.0) {
      "Very Humid"
    } else if (humidity > 50 && temp < 28.0 && temp > 20.0) {
      "Humid"
    } else if (temp > 30.0 && visibility > 5) {
      "Hot"
    } else if (temp > 30.0) {
      "Sunny"
    } else if (precipitation == 0.0 && temp < 15.0) {
      "Dry"
    } else if (temp < 25.0) {
      "Mostly Cloudy"
    } else {
      "Clear"
    }

  }

}
