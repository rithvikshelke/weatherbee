package org.weatherbee.util

import java.util.concurrent.ThreadLocalRandom

import scala.io.Source
import scala.util.Random

/**
  * This is a utility to generate temperature from the city name and the season based on the month
  * the hour is used to vary the temperature between the day
  */
object TemperatureUtil {

  /**
    * This method is used to generate the random temperature
    *
    * @param seasonIndex
    * @param hour
    * @param city
    * @return generatedTemperature
    */
  def generateTemperature(seasonIndex: Int, hour: Int, city: String): Double = {
    val random = new Random()
    val arr: Array[String] = Source.fromFile("data/" + city + ".txt").getLines().toArray // Get the temperature details from the city file
    val seasonTemperatureRange = arr(0).split(",")(seasonIndex)
    var min: Double = seasonTemperatureRange.split("\\|")(0).toString.toDouble // Get the min avg temperature value in centigrade
    var max: Double = seasonTemperatureRange.split("\\|")(1).toString.toDouble // Get the max avg temperature value in centigrade
    if (min > 3.0) {
      //If min value is greater than 3.0
      if (hour.toInt >= 0 && hour.toInt <= 6 && max - min > 3.0) {
        // The temperature during the early hours of the day (before sunrise) is lower.
        max = max - 3.0 // Average maximum is 3 degrees lesser before sunrise
      } else if (hour.toInt > 6 && hour.toInt <= 16 & max - min > 3.0) {
        // The temperature between sunrise and sunset is the maximum during the day
        min = min + 3.0 // The average min is 3 degrees more during the mid day
      } else if (max - min > 2.0) {
        max = max - 2.0 // Considering the temperature decrease - reducing the max value and increasing the min value
        min = min + 2.0
      }
    }
    val generated = ThreadLocalRandom.current().nextDouble(min, max)
    generated
  }

}
