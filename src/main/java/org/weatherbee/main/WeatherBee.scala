/*
 * 
 */

package org.weatherbee.main

import scala.io.Source
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.StandardScaler
import java.util.Date
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.io.PrintWriter
import java.io.File

object WeatherBee {

  def main(args: Array[String]) {

    val sc = new SparkContext(new SparkConf().setAppName("Weather linear regression SGD").setMaster("local"))
    val locations = Array[String]("BLR", "CHN", "DEL", "MUM","KOL","JPR","KAT")
    val latitudes = Array[Double](12.9716, 13.0827, 28.7041,19.0760,22.5726,26.9124,27.7172)
    val longitude = Array[Double](77.5946, 80.2707, 77.1025,72.8777,88.3639,75.7873,85.3240)
    val elevation = Array[Double](920, 6.70, 219.00,14.00,9.10,431.00,1400.00)

    val pw = new PrintWriter(new File("Results.txt"))
    for (i <- 0 to locations.length - 1) {

      val res = whatMightTheWeatherBee(sc, locations(i), latitudes(i), longitude(i), elevation(i), System.currentTimeMillis().toDouble)
      pw.println(res)

    }
    pw.close
  }

  def whatMightTheWeatherBee(sc: SparkContext, location: String, lat: Double, long: Double, elev: Double, date: Double): String = {

    val filename = "data/" + location + ".txt"
    val lines = Source.fromFile(filename).getLines().toArray
    println("location:" + location)
    val temp = predictItem(sc, lines, lat, long, elev, date, "Temprature", 1)
    val dewPoint = predictItem(sc, lines, lat, long, elev, date, "Dew Point", 2)
    val humidity = predictItem(sc, lines, lat, long, elev, date, "Humidity", 3)
    val seaLevelPressure = predictItem(sc, lines, lat, long, elev, date, "Sea Level Pressure", 4)
    val visibility = predictItem(sc, lines, lat, long, elev, date, "Visibility", 5)
    val windSpeed = predictItem(sc, lines, lat, long, elev, date, "Wind Speed", 6)
    val precipitation = predictItem(sc, lines, lat, long, elev, date, "Rain", 7)
    val weatherType = predictWeatherType(temp, precipitation, windSpeed, humidity,visibility)

    val res = location.toUpperCase() + "|" + lat + "|" + long + "|" + elev + "|" + new Date(date.toLong) + "|" + weatherType + "|" + temp + "|" + dewPoint + "|" + humidity + "|" + seaLevelPressure + "|" + visibility + "|" + windSpeed + "|" + precipitation
    return res

  }

  def predictItem(sc: SparkContext, lines: Array[String], lat: Double, long: Double, elev: Double, date: Double, parameterName: String, index: Int): Double = {

    val algo = new LinearRegressionWithSGD()
    algo.optimizer.
      setNumIterations(500).
      setStepSize(0.1)

    /* val maxVal =  lines.map{
      x => x.split(",")(index).toDouble
    }.max*/

    val maxDate = lines.map {
      x => x.split(",")(0).toDouble
    }.max

    val labeledPoints = lines.map {
      line =>
        {
          val interest = line.split(",")(index).toDouble
          LabeledPoint(interest, Vectors.dense(date / maxDate))
        }
    }

    val data = sc.parallelize(labeledPoints)
    val splits = data randomSplit Array(0.8, 0.2)

    val training = splits(0) cache
    val test = splits(1) cache

    val model = algo run training
    val prediction = model predict (test map (_ features))

    val predictionAndLabel = prediction zip (test map (_ label))

    val predictionResult = predictionAndLabel.take(1)

    return predictionResult(0)._1
  }

  def predictWeatherType(temp: Double, precipitation: Double, windspeed: Double, humidity: Double,visibility:Double): String = {
    if (temp <= 4.00 && (precipitation > 3.00 || windspeed > 6.0))
      "Snow"
    else if (visibility <=5 && temp < 20.00)
      "Fog"
    else if (precipitation > 30.00)
      "Thunderstorms"
    else if (precipitation > 5.00)
      "Rain"
    else if (temp > 20.00)
      "Sunny"
    else if (temp > 30.00)
      "Hot"
    else if(humidity > 75.0)
      "Humid"
    else
      "??:("
  }

}