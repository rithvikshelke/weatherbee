package org.weatherbee.util

import org.scalatest.FlatSpec

/**
  * Created by rithvikgopishelke on 05/09/16.
  */
class WeatherUtilityTest extends FlatSpec {

  "Sea Level pressure" must "be based on the elevation" in {
    assert(WeatherUtility.computeSeaLevelPressureFromElevation(10.0) >= 1000)
    assert(WeatherUtility.computeSeaLevelPressureFromElevation(980.0) <= 1005)
  }

  "Horizon visibility" should "always be lesser value at the coastal areas" in {
    assert(WeatherUtility.computeVisibilityFromElevationAndTemperature(9.5, 30) < 5.0)
    assert(WeatherUtility.computeVisibilityFromElevationAndTemperature(1000, 27.0) > 4.0)
  }

  "Weather summary" should "always return based on priority of weather factors" in {
    assert(WeatherUtility.getWeatherTypeFromWeatherDetails(3.2, 2.9, 30.5, 30.3, 8.2).equalsIgnoreCase("Snow"))
    assert(WeatherUtility.getWeatherTypeFromWeatherDetails(25.22, 2.9, 330.5, 60.3, 8.2).equalsIgnoreCase("Thunder Storms"))
    assert(WeatherUtility.getWeatherTypeFromWeatherDetails(35.22, 32.9, 0.0, 73.3, 8.2).equalsIgnoreCase("Very Humid"))
  }
}
