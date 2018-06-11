package models;

/**
 * Defines the structure of a city
 */
public class City {
    private String name;
    private double longitude;
    private double latitude;
    private int timesVisited;
    private int distance;

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
    public City(String name, int dis){
        this.name = name;
        this.distance = dis;
    }
    public void setDistance(int dis){
        this.distance = dis;
    }

    public int getDistance(){
        return distance;
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

}