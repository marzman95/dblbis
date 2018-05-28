import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;

public class Query implements Runnable{
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private File file;

    public void DBSetup(){
        long startTime = System.nanoTime();
        //(new Setup()).readDataBase();
        /*
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            connect = getConnection();
            statement = connect.createStatement();
            statement.executeUpdate("TRUNCATE `city`");
            statement.executeUpdate("TRUNCATE `leg`");
            statement.executeUpdate("TRUNCATE `route`");
            statement.executeUpdate("TRUNCATE `trip`");
            System.out.println("Truncated Tables");
            System.out.println("Loading File...");
            //String str = pathtostr(file);
            if(file.exists()){
                System.out.println("File is there");
            }
            System.out.println(str);
            String sql = "LOAD DATA LOCAL INFILE \'"+ str +"\' IGNORE INTO TABLE `test`.`leg` FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' ESCAPED BY '\"' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES (@ColVar0, `From`, `Country Code (Activity From)`, `To`, `Country Code (Activity To)`, `Start date`, `End date`, @ColVar7, @ColVar8, @ColVar9, @ColVar10, @ColVar11, @ColVar12, @ColVar13, @ColVar14, @ColVar15, @ColVar16, @ColVar17) SET `Trip Number` = REPLACE(REPLACE(@ColVar0, ',', ''), '.', '.'), `Duration` = REPLACE(REPLACE(@ColVar7, ',', ''), '.', '.'), `Load Time` = REPLACE(REPLACE(@ColVar8, ',', ''), '.', '.'), `Unload Time` = REPLACE(REPLACE(@ColVar9, ',', ''), '.', '.'), `Distance` = REPLACE(REPLACE(@ColVar10, ',', ''), '.', '.'), `Productive Running` = REPLACE(REPLACE(@ColVar11, ',', ''), '.', '.'), `Empty Running` = REPLACE(REPLACE(@ColVar12, ',', ''), '.', '.'), `Load Index` = REPLACE(REPLACE(@ColVar13, ',', ''), '.', '.'), `Latitude (from)` = REPLACE(REPLACE(@ColVar14, ',', ''), '.', '.'), `longitude (from)` = REPLACE(REPLACE(@ColVar15, ',', ''), '.', '.'), `Latitude (to)` = REPLACE(REPLACE(@ColVar16, ',', ''), '.', '.'), `Longtitude (to)` = REPLACE(REPLACE(@ColVar17, ',', ''), '.', '.')";
            resultSet = statement.executeQuery(sql);
            System.out.println("Loaded File");
            //writeResultSet(resultSet);
            resultSet = statement.executeQuery("SELECT `Trip Number`,SUM(`Distance`),SUM(`Duration`),MIN(`Start date`),MAX(`End date`) FROM leg GROUP BY `Trip Number`");
            System.out.println("Trip data retrieved");
            System.out.println("Start filling DB with data");
            statement.executeUpdate("INSERT INTO `city`(`City-Name`,`Country`,`Latitude`,`Longitude`,`Times-visited`) SELECT `From`,`Country Code (Activity From)`,`Latitude (from)`,`longitude (from)`,COUNT(`From`) FROM leg GROUP BY `From`");
            System.out.println("City done");
            statement.executeUpdate("INSERT INTO test.trip (`Trip Number`,total_distance,total_duration,start_date,end_date,`From`,`To`) SELECT `Trip Number`, `DisSum` AS total_distance, `DurSum` AS `total_duration`, `Start date` AS `start_date`, `End date` AS `end_date`,`Start` AS `From`, `End` AS `To` FROM (SELECT `Trip Number`, SUM(`Distance`) AS `DisSum`, SUM(`Duration`) AS `DurSum` FROM leg Group By `Trip Number`) AS  `a` NATURAL JOIN (SELECT `Trip Number`, `Start date`, `From` AS `Start` FROM leg NATURAL JOIN (SELECT `Trip Number`, MIN(`Start date`) AS `Start date` FROM leg GROUP BY `Trip Number`) AS `b` ) AS `c` NATURAL JOIN (SELECT `Trip Number`, `End date`, `To` AS `End` FROM leg NATURAL JOIN (SELECT `Trip Number`, MAX(`End date`) AS `End date` FROM leg GROUP BY `Trip Number`) AS `d`) AS `e`");
            System.out.println("trip done");
            statement.executeUpdate("INSERT INTO test.route(`city-name-from`,`city-name-to`,distance) SELECT `From`,`To`,AVG(distance) FROM leg GROUP BY `From`,`To`");
            System.out.println("route done");
            System.out.println("Filled DB with data");
        }
        catch (Exception e){
            System.out.println(e);
            statement = connect.createStatement();
        } finally {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000000000;
            String time = duration/60 +":"+duration%60;
            System.out.println(time);
            close();
        }
        */
    }
    public ResultSet Heatmapdata(){
        try{
            connect = getConnection();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT `Latitude`,`Longitude`,`Times-visited` FROM `city`");
        }
        catch (Exception e){
            System.out.println(e);
        } finally {
            close();

        }
        return resultSet;
    }


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
        } catch (Exception e) {

        }
    }
    private Connection getConnection(){
        try{
            connect = DriverManager.getConnection("jdbc:mysql://85.150.153.235:128/test", "IFSystems", "test123");
        }
        catch(Exception e){}
        finally {
            return connect;
        }
    }
    public Query(String type){

    }

    public void run(){
        DBSetup();
    }

}
