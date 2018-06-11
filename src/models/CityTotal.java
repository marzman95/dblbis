package models;

public class CityTotal extends City {
    int avgLoadingtime;
    int avgUnloadingtime;
    String Country;

    public CityTotal(String name, double longitude, double latitude, int timesVisited,int avgLoadingtime,int avgUnloadingtime,String country){
        super(name, longitude,latitude, timesVisited);
        this.avgLoadingtime = avgLoadingtime;
        this.avgUnloadingtime = avgUnloadingtime;
        this.Country = country;
    }

    public int getAvgLoadingtime() {
        return avgLoadingtime;
    }

    public int getAvgUnLoadingtime() { return avgUnloadingtime; }

    public String getCountry(){return Country;};
}
