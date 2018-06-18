package models;


public class CityPair {
    private City city1;
    private City city2;
    private double Distance;
    private int times;
    private double loadindex;

    public CityPair(){}

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setLoadindex(double loadindex) {
        this.loadindex = loadindex;
    }

    public City getCity1(){
        return city1;
    }

    public City getCity2(){
        return city2;
    }

    public CityPair(City city1, City city2){
        this.city1 = city1;
        this.city2 = city2;
    }
}
