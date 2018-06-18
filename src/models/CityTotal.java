package models;

import java.util.ArrayList;


public class CityTotal extends City {
    double cargoPercentFrom;
    double cargoPercentTo;
    double avgLoadFrom;
    double avgLoadTo;
    String Country;
    ArrayList<Destination> destinations = new ArrayList<Destination>();


    public CityTotal(){}

    public CityTotal(String name, double longitude, double latitude, int timesVisited,String country){
        super(name, longitude,latitude, timesVisited);
        this.Country = country;
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

    public void addDestination(Destination d) {destinations.add(d); }

    public String getCountry(){return Country;}

    public String[][] getinfo(){
        String[][] returninfo = new String[9][2];
        returninfo[0][0] = "Name";
        returninfo[0][1] = getName();
        returninfo[1][0] = "Country";
        returninfo[1][1] =  this.Country;
        returninfo[2][0] = "Latitude";
        returninfo[2][1] = String.valueOf(getLatitude());
        returninfo[3][0] = "Longtitude";
        returninfo[3][1] = String.valueOf(getLongitude());
        returninfo[4][0] = "Times Visited";
        returninfo[4][1] = String.valueOf(getTimesVisited());
        returninfo[5][0] = "Percentage Filled incoming";
        returninfo[5][1] = String.valueOf(this.cargoPercentTo)+"%";
        returninfo[6][0] = "Percentage Filled outgoing";
        returninfo[6][1] = String.valueOf(this.cargoPercentFrom)+"%";
        returninfo[7][0] = "Average Load Index incoming";
        returninfo[7][1] = String.valueOf(this.avgLoadTo);
        returninfo[8][0] = "Average Load Index Outgoing";
        returninfo[8][1] = String.valueOf(this.avgLoadFrom);
        return returninfo;

    }
    public String[][] getDestinations() {
        System.out.println(destinations.size());
        String[][] returnlist = new String[destinations.size()][3];
        int i=0;
        for(Destination d:destinations){
            returnlist[i][0] = d.getName();
            returnlist[i][1] = String.valueOf(d.getDistance());
            returnlist[i][2] = String.valueOf(d.getTimesvisited());
            i++;
        }
        return returnlist;

    }
    public Destination[][] testDestinations(){
        Destination[][] returnlist = new Destination[1][destinations.size()];
        int i = 0;
        for(Destination d:destinations){
            returnlist[0][i] = d;
        }
        return returnlist;
    }

}
