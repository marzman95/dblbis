package models;
import java.util.ArrayList;


public class CityTotal extends City {
    double cargoPercentFrom;
    double cargoPercentTo;
    double avgLoadFrom;
    double avgLoadTo;
    String Country;
    ArrayList<City> destinations;


    public CityTotal(){}

    public CityTotal(String name, double longitude, double latitude, int timesVisited,String country){
        super(name, longitude,latitude, timesVisited);
        this.Country = country;
    }


    public void addDestination(String name,int dis) {
        City city = new City(name,dis);
        destinations.add(city);

    }
    public void setCargoPercentFrom(double cargoPercentFrom) {
        this.cargoPercentFrom = cargoPercentFrom;
    }

    public void setCargoPercentTo(double cargoPercentTo) {
        this.cargoPercentTo = cargoPercentTo;
    }

    public void setAvgLoadFrom(double avgLoadFrom){
        this.avgLoadFrom = avgLoadFrom;
    }

    public void setAvgLoadTo (double avgLoadTo) {
        this.avgLoadTo = avgLoadTo;
    }

    public String getCountry(){return Country;};

    public ArrayList<City> getDestinations() {
        return destinations;
    }
}
