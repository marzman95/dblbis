import models.City;
import models.CityPair;
import models.anEdge;
import models.CityTotal;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static de.fhpotsdam.unfolding.utils.GeoUtils.getDistance;

/**
 * Class that handles the data and database connection.
 */
public class DataManager {
    private Connection dbConnection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private List<City> citiesList = null;

    /**
     * Code block that makes the data handling singleton.
     */
    private static final DataManager dataManager = new DataManager();
    private DataManager(){ getMains(); }
    public static DataManager getDataManager() {return dataManager;}

    /**
     * Gets the database connection.
     * @return connection with the database
     */
    private Connection getConnection() {
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://85.150.153.235:128/test","IFSystems", "test123");
        } catch (Exception e) {
        } finally {
            return dbConnection;
        }
    }

    /**
     * Fetches some main data
     */
    private void getMains() {
        // Gets the list of all cities
        if (citiesList == null) {
            citiesList = new ArrayList<>();
            try {
                dbConnection = getConnection();
                statement = dbConnection.createStatement();
                resultSet = statement.executeQuery("SELECT DISTINCT `City-Name`, `Longitude`, `Latitude`, `Times-visited` FROM `city`");
                while (resultSet.next()) {
                    City city = new City(
                            resultSet.getString("City-Name"),
                            resultSet.getDouble("Longitude"),
                            resultSet.getDouble("Latitude"),
                            resultSet.getInt("Times-visited")
                    );
                    citiesList.add(city);
                }
            } catch (Exception e) {
                System.out.println("[DataManager-exception]: Exception on getAllCities(): " + e);
            }
        }

        // Do more general data fetch
    }

    /**
     * Closes the database connection.
     */
    private void cleanCloseConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Runs a custom query on the database.
     * @param query a string with the SQL-query
     * @return the resulting {@link java.sql.ResultSet}
     */
    public ResultSet customQuery(String query) {
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println("[DataManager-exception ]: Exception on customQuery(): " + e);
        }
        return resultSet;
    }

    /**
     * Gets the 50 most popular cities.
     * TODO: Choose best option
     * @return the list of the 50 most popular cities as a list of {@link models.City}-objects
     */
    public List<City> getPopularCities(int infoAmount) {
        List<City> popularCities = new ArrayList<>();
//        for (int i = 0; i < citiesList.size(); i++) {
//            City curCity = citiesList.get(i);
//            if (curCity.getTimesVisited() > 50) {
//                popularCities.add(curCity);
//            }
//        }
//        return popularCities;
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT `City-Name`,`Latitude`,`Longitude`,`Times-visited` FROM `city` ORDER BY `Times-visited` DESC LIMIT " + infoAmount + "");
            while (resultSet.next()) {
                City city = new City(
                        resultSet.getString("City-Name"),
                        resultSet.getDouble("Longitude"),
                        resultSet.getDouble("Latitude"),
                        resultSet.getInt("Times-visited")
                );
                popularCities.add(city);
            }
        } catch (Exception e) {
            System.out.println("[DataManager-exception]: Exception on getPopularCities(): " + e);
        }
        return popularCities;
    }

    /**
     * Gets the 50 most popular edges
     * @return the list of the 50 most popular edges as a list of {@link models.anEdge}-objects
     */
    public List<anEdge> getPopularEdges(int infoAmount) {
        List<anEdge> allEdges = new ArrayList<>();
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT `times-used`, `c1`.`Latitude`, `c1`.`Longitude`, `c2`.`Latitude`, `c2`.`Longitude` \n" +
                    "FROM `city` AS `c1`, `city` AS `c2`, `route` \n" +
                    "WHERE `c1`.`City-Name` = `city-name-from` AND `c2`.`City-Name` = `city-name-to` \n" +
                    "ORDER BY `route`.`times-used` DESC LIMIT " + infoAmount);
            while (resultSet.next()) {
                anEdge edge = new anEdge(
                        resultSet.getDouble("c1.Latitude"),
                        resultSet.getDouble("c1.Longitude"),
                        resultSet.getDouble("c2.Latitude"),
                        resultSet.getDouble("c2.Longitude"),
                        resultSet.getInt("times-used")
                );

                allEdges.add(edge);
            }
        } catch (Exception e) {
            System.out.println("[DataManager-exception]: Exception on getEdges(): " + e);
        }
        return allEdges;
    }


    public CityTotal getCityStatistics (double lon, double lat, JProgressBar bar) {
        CityTotal city = new CityTotal();
        bar.setValue(0);
        try{
           dbConnection = getConnection();
           statement = dbConnection.createStatement();
           bar.setValue(0);
           resultSet= statement.executeQuery("SELECT" +
                   "  city.`City-Name`, city.Country," +
                   "  city.Longitude," +
                   "  city.Latitude," +
                   "  city.`Times-visited` " +
                   " FROM city" +
                   " WHERE city.Longitude BETWEEN "+ String.valueOf(lon-0.01)+" AND "+String.valueOf(lon+0.01) +
                   " AND city.Latitude BETWEEN "+ String.valueOf(lat-0.01)+" AND " +String.valueOf(lat+0.01) );
           resultSet.first();
           city = new CityTotal(resultSet.getString("City-Name"),
               resultSet.getDouble("Longitude"), resultSet.getDouble("Latitude"),
               resultSet.getInt("Times-visited"), resultSet.getString("Country"));
            bar.setValue(20);


           resultSet = statement.executeQuery("SELECT 100*a.cnt/b.cnt AS `p`" +
                   "FROM\n" +
                   "(SELECT COUNT(`Load index`) AS cnt FROM `leg` WHERE `To` = \""+city.getName()+"\" AND `Load index` <> 0) as a\n" +
                   "INNER JOIN\n" +
                   "(SELECT COUNT(`Load index`) AS cnt FROM `leg` WHERE `To` = \""+city.getName()+"\" ) as b\n");
           resultSet.first();
           city.setCargoPercentTo(resultSet.getDouble("p"));
           bar.setValue(40);
           resultSet = statement.executeQuery("SELECT 100*a.cnt/b.cnt AS `p`" +
                    "FROM\n" +
                    "(SELECT COUNT(`Load index`) AS cnt FROM `leg` WHERE `From` = \""+city.getName()+"\" AND `Load index` <> 0) as a\n" +
                    "INNER JOIN\n" +
                    "(SELECT COUNT(`Load index`) AS cnt FROM `leg` WHERE `From` = \""+city.getName()+"\") as b\n");
            resultSet.first();
            city.setCargoPercentFrom(resultSet.getDouble("p"));
            bar.setValue(60);
            resultSet = statement.executeQuery("SELECT AVG(`Load index`) AS `Load index` FROM `leg` WHERE `From` = \""+city.getName()+"\"");
            resultSet.first();
            bar.setValue(80);
            city.setAvgLoadFrom(resultSet.getDouble("Load index"));
            resultSet = statement.executeQuery("SELECT AVG(`Load index`) AS `Load index` FROM `leg` WHERE `To` = \""+city.getName()+"\"");
            resultSet.first();
            city.setAvgLoadTo(resultSet.getDouble("Load index"));
            bar.setValue(100);

        }
        catch (Exception e) {
            System.out.println("[DataManager-exception]: Exception on getCityStatistics(): " + e);
        }
        return city;
    }

//    public CityPair Select2cities(double lon1, double lat1,double lon2, double lat2, JProgressBar bar){
//        return;
//    }

    public CityPair twoCityStatistics(double lat1, double lon1, double lat2, double lon2, JProgressBar bar) {
        CityPair pair = new CityPair();
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();

            //get first city name and times-visited from location
            resultSet = statement.executeQuery("SELECT\n" +
                    "  city.`City-Name`, city.Country," +
                    "  city.Longitude," +
                    "  city.Latitude," +
                    "  city.`Times-visited` " +
                    " FROM city" +
                    " WHERE city.Longitude BETWEEN lon1-0.01 AND lon1+0.01" +
                    " AND city.Latitude BETWEEN lat1-0.01 AND lat1+0.01");
            //create city and add to cityPair
            City city1 = new City(resultSet.getString("City-Name"),
                    resultSet.getDouble("Longitude"), resultSet.getDouble("Latitude"),
                    resultSet.getInt("Times-visited"));
            pair.setCity1(city1);

            //get second city name and times-visited from location
            resultSet = statement.executeQuery("SELECT\n" +
                    "  city.`City-Name`, city.Country," +
                    "  city.Longitude," +
                    "  city.Latitude," +
                    "  city.`Times-visited` " +
                    " FROM city" +
                    " WHERE city.Longitude BETWEEN lon2-0.01 AND lon2+0.01" +
                    " AND city.Latitude BETWEEN lat2-0.01 AND lat2+0.01");
            //create city and add to cityPair
            City city2 = new City(resultSet.getString("City-Name"),
                    resultSet.getDouble("Longitude"), resultSet.getDouble("Latitude"),
                    resultSet.getInt("Times-visited"));
            pair.setCity2(city2);
            //get distance between cities and add to cityPair
            pair.setDistance(getDistance(lat1, lon1, lat2, lon2));




        } catch (Exception e) {
            System.out.println("[DataManager-exception]: Exception on twoCityStatistics(): " + e);}
        return pair;
    }



    /**
     * Returns a list of cities.
     * @return the list of all cities as list of {@link models.City}-objects
     */
    public List<City> getAllCities() {
        return citiesList;
    }
}
