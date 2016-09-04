package org.weatherbee.util

import org.scalatest.FlatSpec

/**
  * Test classes for dew point humidity utility
  */
class DewPointHumidityUtilTest extends FlatSpec {


  "Compute Humidity" should "be lesser than 30%" in {
    assert(DewPointHumidityUtil.computeHumidity(9.8, 11.5) <= 30.0)
    val humidity1 = DewPointHumidityUtil.computeHumidity(17.8, 19.2)
    assert(humidity1 <= 60.0 && humidity1 >= 30.0)
    val humidity2 = DewPointHumidityUtil.computeHumidity(30.0, 35.5)
    assert(humidity2 <= 100.0 && humidity2 >= 60.0)
  }

  "Compute RH" should "return RH value based on temperature" in {
    assert(DewPointHumidityUtil.computeDewPointFromRelativeHumidity(65, 11.0) == 11.0)
    assert(DewPointHumidityUtil.computeDewPointFromRelativeHumidity(65, 34.0) == 27.0)
  }

}
