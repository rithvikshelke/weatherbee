package org.weatherbee.main

import java.io.{File, PrintWriter}
import java.util.logging.Logger

import org.weatherbee.container.{CityVO, WeatherVO}
import org.weatherbee.util.{DateUtil, DewPointHumidityUtil, TemperatureUtil, WeatherUtility}

import scala.io.Source

/**
  * This is the main class to generate test data for weather
  */
object WeatherBeeApp {

  val logger: Logger = Logger.getAnonymousLogger

  def main(args: Array[String]) {
    logger.info("WeatherBee Application")

    // Load the static data from the city.csv file and store it in the cityVOCollection
    val cityVOCollection = Source.fromFile("data/city.csv").getLines().map(
      x => {
        val cityDetails = x.split(",")
        CityVO(cityDetails(0), cityDetails(1), cityDetails(2), cityDetails(3), cityDetails(4))
      }
    ).toArray


    //Generation of random timestamps
    val randomTimeStamps: Array[String] = cityVOCollection.map(x => {
      DateUtil.getRandomTimeStamp()
    })

    val w = new PrintWriter("Results.txt")
    w.print("") // Clean up the file


    randomTimeStamps.map(x=> {
      //The list of weatherVO is stored in the result
      val result = for (i <- cityVOCollection.indices)
      // method to generate weather test data
        yield generateWeatherTestData(cityVOCollection(i), x)

      result.map(x => {
        w.append(x.cityDetails.cityCode + "|" + x.cityDetails.latitude + "|" + x.cityDetails.longitude + "|" + x.timestamp + "|"
          + x.cityDetails.elevation + "|" + x.weatherType + "|" + "%2.2f".format(x.temperature) + "|" + "%2.2f".format(x.dewPoint) + "|" + "%2.2f".format(x.humidity) + "|"
          + "%3.2f".format(x.precipitation) + "|" + x.seaLevelPressure + "|" + "%2.2f".format(x.visibility) + "\n"
        )
      })


    })
    w.close()
  }

  /**
    * The method used to generate test data for weather
    *
    * @param cityVO - value object containing the attributes of the city
    * @param timestamp - timestamp as a string
    * @return WeatherVO
    */
  def generateWeatherTestData(cityVO: CityVO, timestamp: String): WeatherVO = {
    val month = DateUtil.getMonth(timestamp)
    val hour = DateUtil.getHour(timestamp)
    val seasonIndex = getSeasonIndexFromMonth(month)
    val temp = TemperatureUtil.generateTemperature(seasonIndex, hour.toInt, cityVO.cityCode) // find the temperature
    val dewPoint = DewPointHumidityUtil.getDewPointFromTemperature(cityVO.cityName, temp) // find the dew point
    val humidity = DewPointHumidityUtil.computeHumidity(dewPoint, temp) // find the humidity
    val visibility = WeatherUtility.computeVisibilityFromElevationAndTemperature(cityVO.elevation.toDouble, temp) // find the visibility
    val precipitation = WeatherUtility.generatePrecipitationBasedOnProbability(cityVO.cityCode, seasonIndex) // find the precipitation
    val seaLevelPressure = WeatherUtility.computeSeaLevelPressureFromElevation(cityVO.elevation.toDouble) // find sea level pressure
    val weatherSummary = WeatherUtility.getWeatherTypeFromWeatherDetails(temp, dewPoint, precipitation, humidity, visibility) // get the weather summary
    WeatherVO(cityVO, timestamp, temp, dewPoint, humidity, seaLevelPressure, visibility, precipitation, weatherSummary) // Set it into weather value object
  }


  /**
    * The method gets the season index based on the month
    *
    * @param month - Month of the year
    * @return seasonIndex
    */
  def getSeasonIndexFromMonth(month: String): Int = {
    val winter: Array[String] = Array[String]("12", "01", "02", "03")
    val summer: Array[String] = Array[String]("04", "05", "06")
    val monsoon: Array[String] = Array[String]("07", "08", "09")
    val postMonsoon: Array[String] = Array[String]("10", "11")
    if (winter.contains(month)) {
      0 // Winter month
    }
    else if (summer.contains(month)) {
      1 // Summer month
    }
    else if (monsoon.contains(month)) {
      2 // Monsoon month
    }
    else {
      3 //Post Monsoon month
    }
  }

}
