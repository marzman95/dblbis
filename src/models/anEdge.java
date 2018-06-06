package models;

public class anEdge {

    private double latitude1;
    private double longitude1;
    private double latitude2;
    private double longitude2;


    public anEdge(double la1, double lo1, double la2, double lo2) {
        this.latitude1 = la1;
        this.longitude1 = lo1;
        this.latitude2 = la2;
        this.longitude2 = lo2;

    }

    public double getLongitude1() {
        return longitude1;
    }

    public double getLatitude1() {
        return latitude1;
    }

    public double getLongitude2() {
        return longitude2;
    }

    public double getLatitude2() {
        return latitude2;
    }


}

