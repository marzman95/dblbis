package models;

public class Destination {
    private String name;
    private int distance;

    public Destination(String name,int distance){
        this.name = name;
        this.distance = distance;
    }

    public String getName(){
        return name;
    }

    public int getDistance(){
        return distance;
    }
}
