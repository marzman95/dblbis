import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.io.InputStream;
import java.util.Properties;
/**
 * Used for setting up the database. Reads from a CSV file.
 */
public class Setup implements Runnable {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private File file;
    private Properties prop = new Properties();
    private InputStream input = null;
    private String db;
    private String dbuser;
    private String dbpass;

    /**
     * Fills the database with the data from the CSV file.
     * Already interferes some general statistics about the data upon construction
     */
    @Override
     public void run(){
         long startTime = System.nanoTime(); // measures start time to check built time.
         try { // writes to output to indicate progress.
             connect = getConnection();
             statement = connect.createStatement();
             statement.executeUpdate("TRUNCATE `city`");
             statement.executeUpdate("TRUNCATE `leg`");
             statement.executeUpdate("TRUNCATE `route`");
             statement.executeUpdate("TRUNCATE `trip`");
             System.out.println("Truncated Tables");

             System.out.println("Loading File...");
             String str = pathtostr(file);
             if(!file.exists()){
                 throw new FileNotFoundException("invalid path to data");
             }
             System.out.println(str);
             String sql = "LOAD DATA LOCAL INFILE \'"+ str +"\' IGNORE INTO TABLE `test`.`leg` FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' ESCAPED BY '\"' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES (@ColVar0, `From`, `Country Code (Activity From)`, `To`, `Country Code (Activity To)`, `Start date`, `End date`, @ColVar7, @ColVar8, @ColVar9, @ColVar10, @ColVar11, @ColVar12, @ColVar13, @ColVar14, @ColVar15, @ColVar16, @ColVar17) SET `Trip Number` = REPLACE(REPLACE(@ColVar0, ',', ''), '.', '.'), `Duration` = REPLACE(REPLACE(@ColVar7, ',', ''), '.', '.'), `Load Time` = REPLACE(REPLACE(@ColVar8, ',', ''), '.', '.'), `Unload Time` = REPLACE(REPLACE(@ColVar9, ',', ''), '.', '.'), `Distance` = REPLACE(REPLACE(@ColVar10, ',', ''), '.', '.'), `Productive Running` = REPLACE(REPLACE(@ColVar11, ',', ''), '.', '.'), `Empty Running` = REPLACE(REPLACE(@ColVar12, ',', ''), '.', '.'), `Load Index` = REPLACE(REPLACE(@ColVar13, ',', ''), '.', '.'), `Latitude (from)` = REPLACE(REPLACE(@ColVar14, ',', ''), '.', '.'), `Longitude (from)` = REPLACE(REPLACE(@ColVar15, ',', ''), '.', '.'), `Latitude (to)` = REPLACE(REPLACE(@ColVar16, ',', ''), '.', '.'), `Longitude (to)` = REPLACE(REPLACE(@ColVar17, ',', ''), '.', '.')";
             resultSet = statement.executeQuery(sql);
             System.out.println("Loaded File");

             resultSet = statement.executeQuery("SELECT `Trip Number`,SUM(`Distance`),SUM(`Duration`),MIN(`Start date`),MAX(`End date`) FROM leg GROUP BY `Trip Number`");
             System.out.println("Trip data retrieved");

             System.out.println("Start filling DB with data");
             statement.executeUpdate("INSERT INTO test.trip (`Trip Number`,total_distance,total_duration,start_date,end_date,`From`,`To`) SELECT `Trip Number`, `DisSum` AS total_distance, `DurSum` AS `total_duration`, `Start date` AS `start_date`, `End date` AS `end_date`,`Start` AS `From`, `End` AS `To` FROM (SELECT `Trip Number`, SUM(`Distance`) AS `DisSum`, SUM(`Duration`) AS `DurSum` FROM leg Group By `Trip Number`) AS  `a` NATURAL JOIN (SELECT `Trip Number`, `Start date`, `From` AS `Start` FROM leg NATURAL JOIN (SELECT `Trip Number`, MIN(`Start date`) AS `Start date` FROM leg GROUP BY `Trip Number`) AS `b` ) AS `c` NATURAL JOIN (SELECT `Trip Number`, `End date`, `To` AS `End` FROM leg NATURAL JOIN (SELECT `Trip Number`, MAX(`End date`) AS `End date` FROM leg GROUP BY `Trip Number`) AS `d`) AS `e`");
             System.out.println("trip done");

             statement.executeUpdate("INSERT INTO test.route(`city-name-from`,`city-name-to`,`distance`,`times-used`) SELECT `From`,`To`,AVG(distance),COUNT(*) FROM leg GROUP BY `From`,`To`");
             System.out.println("route done");

             statement.executeUpdate("INSERT INTO `city` (`City-Name`,`Country`,`Latitude`,`Longitude`,`Times-visited`,`out-degree`,`in-degree`,`tot-degree`) SELECT `City-Name`, `Country`, `Latitude`, `Longitude`, `Times-visited`, `out-degree`, `in-degree`, `tot-degree` FROM ( SELECT `city-name-from` AS `City-Name`, COUNT(`city-name-to`) AS `out-degree` FROM route GROUP BY `city-name-from` ) AS `out` NATURAL JOIN ( SELECT `city-name-to` AS `City-Name`, COUNT(`city-name-from`) AS `in-degree` FROM route GROUP BY `city-name-to` ) AS `in` NATURAL JOIN ( SELECT `City-Name`, COUNT(`other-city`) AS `tot-degree` FROM ( SELECT `city-name-from` AS `City-Name`, `city-name-to` AS `other-city` FROM route UNION ( SELECT `city-name-to` AS `City-Name`, `city-name-from` AS `other-city` FROM route ) ) AS `a` GROUP BY `City-Name` ) AS `tot`  NATURAL JOIN ( SELECT `From` AS `City-Name`, `Country Code (Activity From)` AS `Country`, `Latitude (from)` AS `Latitude`, `Longitude (from)` AS `Longitude`, Count(`From`) AS `Times-visited` FROM leg GROUP BY `From` ) AS `gen`");
             System.out.println("models.City done");

             System.out.println("Filled DB with data");
         }
         catch (Exception e){
            System.out.println(e);
         } finally {
             // outputs the time used to built the database
             long endTime = System.nanoTime();
             long duration = (endTime - startTime)/1000000000;
             System.out.println(duration/60 +":"+duration%60);

             // closes all connections
             close();
         }
     }

    /**
     * Converts the path to a CSV file to a readable string
     * @param /* a CSV file with input data
     * @return the input data as a string
     */
    public String pathtostr(File file){
        char[] chars = file.toString().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars){
            if(c == '\\'){
                sb.append("\\\\");
            } else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Closes all active JDBC connections
     */
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) { }
    }

    /**
     * Singleton method to make a connection
     * @return an active connection
     */
    private Connection getConnection() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://"+db+"?autoReconnect=true&useSSL=false",dbuser, dbpass);
        } catch (Exception e) {
        } finally {
            return connect;
        }
    }
    /**
     * Sets the data file
     * @param file CSV file containing the data
     */
    public Setup(File file){
        this.file = file;
        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            db = prop.getProperty("database");
            dbuser = prop.getProperty("dbuser");
            dbpass = prop.getProperty("dbpassword");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

