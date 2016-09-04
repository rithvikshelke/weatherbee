package org.weatherbee.container

/**
  * This case class is used to hold the weather item object. It contains the details of the city and the weather attributes
  */
case class WeatherVO(cityDetails: CityVO, timestamp: String, temperature: Double, dewPoint: Double, humidity: Double, seaLevelPressure: Integer, visibility: Double, precipitation: Double, weatherType: String)