package org.weatherbee.util

import org.scalatest.FlatSpec

/**
  * Test case to test the temperature generation based on the season
  */
class TemperatureUtilTest extends FlatSpec {


  "Generate temperature" should "return temperature based on the season" in {
    val winterInKathmandu = TemperatureUtil.generateTemperature(0, 1, "KAT")
    assert(winterInKathmandu < 28)
    val summerInJaipur = TemperatureUtil.generateTemperature(1, 12, "JPR")
    assert(summerInJaipur > 24)
  }


}
