package models;

public class Destination {
    private String name;
    private int distance;
    private int timesvisited;

    public Destination(String name,int distance,int timesvisited){
        this.name = name;
        this.distance = distance;
        this.timesvisited = timesvisited;
    }

    public String getName(){
        return name;
    }

    public int getDistance(){
        return distance;
    }
    public int getTimesvisited(){
        return  timesvisited;
    }
}
