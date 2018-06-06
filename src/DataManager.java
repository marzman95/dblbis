import models.City;
import models.anEdge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<City> getPopularCities() {
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
            resultSet = statement.executeQuery("SELECT `City-Name`,`Latitude`,`Longitude`,`Times-visited` FROM `city` ORDER BY `Times-visited` DESC LIMIT 50");
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

    public List<anEdge> getEdges() {
        List<anEdge> allEdges = new ArrayList<>();
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT `times-used`, `c1`.`Latitude`, `c1`.`Longitude`, `c2`.`Latitude`, `c2`.`Longitude` \n" +
                    "FROM `city` AS `c1`, `city` AS `c2`, `route` \n" +
                    "WHERE `c1`.`City-Name` = `city-name-from` AND `c2`.`City-Name` = `city-name-to` \n" +
                    "ORDER BY `route`.`times-used` DESC LIMIT 50");
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
            System.out.println("[DataManager-exception]: Exception on getPopularCities(): " + e);
        }
        return allEdges;
    }

    /**
     * Returns a list of cities.
     * @return the list of all cities as list of {@link models.City}-objects
     */
    public List<City> getAllCities() {
        return citiesList;
    }
}
