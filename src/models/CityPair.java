package models;


public class CityPair {
    private CityTotal city1;
    private CityTotal city2;
    private double Distance;
    private int times;
    private double loadindex;
    private double[] duration;
    private double[] distance;

    public CityPair(){}

    public void setCity1(CityTotal city1) {
        this.city1 = city1;
    }

    public void setCity2(CityTotal city2) {
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

    public void setduration(double[]duration){
        this.duration=duration;
    }
    public void setdistance(double[]distance){
        this.distance=distance;
    }

    public CityTotal getCity1(){
        return city1;
    }

    public CityTotal getCity2(){
        return city2;
    }

    public double getDistance() {
        return Distance;
    }

    public int getTimes() {
        return times;
    }

    public double getLoadindex() {
        return loadindex;
    }

    public double[] getDuration() {
        return duration;
    }


    public double[] getdistance() {
        return distance;
    }

    public String[][] getinfo(){
        String[][] returninfo = new String[12][2];
        returninfo[0][0] = "Name city 1:";
        returninfo[0][1] = city1.getName();
        returninfo[1][0] = "Country city 1";
        returninfo[1][1] = city1.getCountry();
        returninfo[2][0] = "Name city 2:";
        returninfo[2][1] = city2.getName();
        returninfo[3][0] = "Country city 2:";
        returninfo[3][1] = city2.getCountry();
        returninfo[4][0] = "Trips between both cities:";
        returninfo[4][1] = String.valueOf(getTimes());
        returninfo[5][0] = "Distance between cities:";
        returninfo[5][1] = String.valueOf(getDistance());
        returninfo[6][0] = "Average leg duration:";
        returninfo[6][1] = String.valueOf(getDuration()[0]);
        returninfo[7][0] = "Maximum leg duration:";
        returninfo[7][1] = String.valueOf(getDuration()[1]);
        returninfo[8][0] = "Minimum leg duration:";
        returninfo[8][1] = String.valueOf(getDuration()[0]);
        returninfo[9][0] = "Average leg distance:";
        returninfo[9][1] = String.valueOf(getdistance()[0]);
        returninfo[10][0] = "Maximum leg distance:";
        returninfo[10][1] = String.valueOf(getdistance()[1]);
        returninfo[11][0] = "Minimum leg distance:";
        returninfo[11][1] = String.valueOf(getdistance()[2]);
        return returninfo;

    }
    public CityPair(CityTotal city1, CityTotal city2){
        this.city1 = city1;
        this.city2 = city2;
    }
}
