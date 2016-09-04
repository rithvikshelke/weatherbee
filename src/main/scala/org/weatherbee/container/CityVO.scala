package org.weatherbee.container

/**
  *
  * case class CityVO is a value object containing the details associated with the city - city name, latitude, longitude and the elevation
  */
case class CityVO(cityCode: String, cityName: String, latitude: String, longitude: String, elevation: String)
