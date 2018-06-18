package models;

/**
 * Defines the structure of a city
 */
public class City {
    private String name;
    private double longitude;
    private double latitude;
    private int timesVisited;
    private int outdegree;
    private int indegree;
    private int totaldegree;

    public City(String name, double longitude, double latitude, int timesVisited, int out, int in, int tot) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timesVisited = timesVisited;
        outdegree = out;
        indegree = in;
        totaldegree = tot;
    }

    public City() {
        this.name = "test";
        this.longitude = 0;
        this.latitude = 0;
        this.timesVisited = 0;
        outdegree = 0;
        indegree = 0;
        totaldegree = 0;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() { return latitude; }

    public int getTimesVisited() { return timesVisited; }

    public int getOutdegree() { return outdegree; }

    public int getIndegree() { return indegree; }

    public int getTotaldegree() { return totaldegree; }

}