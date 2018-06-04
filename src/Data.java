import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Class that handles the data and database connection.
 */
public class Data {
    private Connection dbConnection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;

    /**
     * Code block that makes the data handling singleton.
     * Use Data.useData() to get the instance.
     */
    private static final Data data = new Data();
    private Data(){}
    public static Data useData() {return data;}

    /**
     * Gets the database connection.
     * @return Connection with the database
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
     * Function the get the heatmap data.
     * @return resultSet with heatmap data
     */
    public ResultSet getHeatmapData() {
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT `City-Name`,`Latitude`,`Longitude`,`Times-visited` FROM `city` ORDER BY `Times-visited` DESC LIMIT 50");
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultSet;
    }

    /**
     * Returns a list of cities.
     */
    public List<String> getCitiesList() {
        List<String> citiesList = new ArrayList<String>();
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT DISTINCT `City-Name` FROM `city`");
            while (resultSet.next()) {
                citiesList.add(resultSet.getString("City-Name"));
            }

        } catch (Exception e) {
            System.out.println("Exception on getCitiesList(): " + e);
        }
        return citiesList;
    }
}
