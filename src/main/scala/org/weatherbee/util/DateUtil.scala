package org.weatherbee.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

import scala.io.Source
import scala.util.Random

/**
  * Created by rithvikgopishelke on 03/09/16.
  */
object DateUtil {


  /**
    * This method is used to format the date object to ISO date standard formatting
    *
    * @param inputDate
    * @return formatted date
    */
  def toISOStandardFormat(inputDate: Date): String = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return formatter.format(inputDate)
  }

  /**
    * This method is used to return a random date
    *
    * @return local date object
    */
  def getRandomDate(): LocalDate = {
    val random = new Random()
    val minDay = LocalDate.of(2016, 1, 1).toEpochDay().toInt
    val maxDay = LocalDate.of(2016, 8, 1).toEpochDay().toInt
    val randomDay = minDay + random.nextInt(maxDay - minDay)
    LocalDate.ofEpochDay(randomDay)
  }


  /**
    * This method is used to return a random timestamp between a range
    *
    * @return timestamp
    */
  def getRandomTimeStamp(): String = {
    val offset = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
    val end = Timestamp.valueOf("2016-09-01 00:00:00").getTime();
    val diff = end - offset + 1;
    val rand = new Timestamp(offset + (Math.random() * diff).toLong);
    val date = new Date(rand.getTime)
    val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    formatter.format(date)
  }

  /**
    * Get the month from timestamp value
    *
    * @param timestamp
    * @return month
    */
  def getMonth(timestamp: String): String = {
    val month = timestamp.split("-")(1)
    month
  }

  /**
    * Get the hour from timestamp
    *
    * @param timestamp
    * @return hour
    */
  def getHour(timestamp: String): String = {
    val hour = timestamp.split("T")(1).split(":")(0)
    hour
  }

}
