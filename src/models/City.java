package models;

/**
 * Defines the structure of a city
 */
public class City {
    private String name;
    private double longitude;
    private double latitude;
    private int timesVisited;

    public City(String name, double longitude, double latitude, int timesVisited) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timesVisited = timesVisited;
    }
    public City() {
        this.name = "test";
        this.longitude = 0;
        this.latitude = 0;
        this.timesVisited = 0;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() { return latitude; }

    public int getTimesVisited() { return timesVisited; }

}