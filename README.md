# WEATHERBEE
Weather generation application

# INFO
The weather dumps have been taken from  - https://www.wunderground.com

The cities covered are :
1. Bangalore
2. Chennai
3. Mumbai
4. Delhi
5. Kolkata
6. Kathmandu
7. Jaipur

The results will be written to a file called Results.txt

The result is displayed in a pipe "|" delimited format. The various parameters are :

1. Location code
2. Latitude.
3. Longitude.
4. Timestamp.
5. Elevation.
6. Weather Type.
7. Temperature.
8. Dew Point.
9. Humidity.
10. Precipitation.
11. Sea level pressure.
12. Horizon Visibility


Each city contains a data file:

The first line contains comma separated values containing the minimum and maximum average temperatures for all the four seasons.
The second line contains comma separated values containing the average rainfall and the probability of rainfall for each season.


#Seasons:(wiki)
1. Winter - December,January,February & March
2. Summer - April,May & June
3. Monsoon - July,August & Septemper
4. Post Monsoon - October & November

#How to run:
Run the main program(WeatherBeeApp.scala) and the test data for list of locations will be written to a file called Results.txt
