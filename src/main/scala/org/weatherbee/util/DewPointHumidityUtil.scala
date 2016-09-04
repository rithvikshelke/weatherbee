package org.weatherbee.util

import java.util.concurrent.ThreadLocalRandom

/**
  * Created by rithvikgopishelke on 04/09/16.
  * This utility is used to get dew point and humidity details
  */
object DewPointHumidityUtil {

  /**
    * Values of average relative humidity for different cities
    */
  val avgRH_BLR = 56;
  val avgRH_CHN = 70;
  val avgRH_MUM = 75;
  val avgRH_DEL = 54;
  val avgRH_KAT = 70;
  val avgRH_KOL = 71;
  val avgRH_JPR = 44;

  /**
    * This method is used to get the dew point value based on the city
    *
    * @param city
    * @param temperature
    * @return dew point value
    */
  def getDewPointFromTemperature(city: String, temperature: Double): Double = {
    if (city.equalsIgnoreCase("BANGALORE")) {
      computeDewPointFromRelativeHumidity(avgRH_BLR, temperature)
    }
    else if (city.equalsIgnoreCase("CHENNAI")) {
      computeDewPointFromRelativeHumidity(avgRH_CHN, temperature)

    } else if (city.equalsIgnoreCase("MUMBAI")) {
      computeDewPointFromRelativeHumidity(avgRH_MUM, temperature)

    } else if (city.equalsIgnoreCase("DELHI")) {
      computeDewPointFromRelativeHumidity(avgRH_DEL, temperature)
    } else if (city.equalsIgnoreCase("KATHMANDU")) {
      computeDewPointFromRelativeHumidity(avgRH_KAT, temperature)

    } else if (city.equalsIgnoreCase("KOLKATA")) {
      computeDewPointFromRelativeHumidity(avgRH_KOL, temperature)
    } else {
      computeDewPointFromRelativeHumidity(avgRH_JPR, temperature)
    }

  }


  /**
    * Get the dew point based on the relative humidity and the temperature
    *
    * @param relativeHumidity
    * @param temperature
    * @return dew point
    */
  def computeDewPointFromRelativeHumidity(relativeHumidity: Int, temperature: Double): Double = {
    val rh = relativeHumidity - 20
    if (temperature < 12) {
      temperature
    }
    else {
      temperature - ((100 - relativeHumidity) / 5)
    }
  }

  /**
    * compute humidity based on the temperature and dew point
    *
    * @param dewPoint
    * @param temperature
    * @return humidity
    */
  def computeHumidity(dewPoint: Double, temperature: Double): Double = {
    if (temperature < 12.0) {
      ThreadLocalRandom.current().nextDouble(0.0, 30.0)
    } else if (temperature < 20) {
      ThreadLocalRandom.current().nextDouble(30.0, 60.0)
    } else {
      (dewPoint / temperature) * 100
    }
  }
}
