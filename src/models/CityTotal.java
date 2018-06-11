package models;
import java.util.ArrayList;


public class CityTotal extends City {
    int avgLoadingtime;
    int avgUnloadingtime;
    String Country;
    ArrayList<City> destinations;


    public CityTotal(String name, double longitude, double latitude, int timesVisited,String country){
        super(name, longitude,latitude, timesVisited);
        this.Country = country;
    }


    public void addDestination(String name,int dis) {
        City city = new City(name,dis);
        destinations.add(city);

    }

    public String getCountry(){return Country;};

    public ArrayList<City> getDestinations() {
        return destinations;
    }
}
