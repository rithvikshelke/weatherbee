package org.weatherbee.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import org.scalatest.FlatSpec


/**
  * Created by rithvikgopishelke on 05/09/16.
  */
class DateUtilTest extends FlatSpec {

  "A timestamp object" must "be converted to ISO standard format" in {
    val date = new Date()
    val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val timestamp = DateUtil.getRandomTimeStamp()
    assert(timestamp != null)
    assert(formatter.parse(timestamp) != null)
  }

  "Month" must "be returned from the getMonth" in {
    val timestamp = Timestamp.valueOf("2015-01-01 00:00:00").getTime()
    val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val timestampStr = formatter.format(timestamp)
    assert(DateUtil.getMonth(timestampStr) == "01")
  }

  "Hour" must "be returned from getHour" in {
    val timestamp = Timestamp.valueOf("2015-01-01 15:00:00").getTime()
    val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val timestampStr = formatter.format(timestamp)
    assert(DateUtil.getHour(timestampStr) == "15")
  }


}
