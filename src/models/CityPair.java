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
    public String[][] getinfo(){
        String[][] returninfo = new String[9][2];
        returninfo[0][0] = "Name city 1:";
        returninfo[0][1] = city1.getName();
        returninfo[1][0] = "Country city 1";
        returninfo[1][1] = city1.getCountry();
        returninfo[3][0] = "Name city 2:";
        returninfo[3][1] = city2.getName();
        returninfo[4][0] = "Country city 2";
        returninfo[4][1] = city2.getCountry();
        returninfo[5][0] = "Times route taken";
        returninfo[5][1] = String.valueOf(times);
//        returninfo[][0]=;
//        returninfo[][1]=;
//        returninfo[][0]=;
//        returninfo[][1]=;
//        returninfo[][0]=;
//        returninfo[][1]=;
//        returninfo[][0]=;
//        returninfo[][1]=;
//        returninfo[][0]=;
//        returninfo[][1]=;
        return returninfo;

    }
    public CityPair(CityTotal city1, CityTotal city2){
        this.city1 = city1;
        this.city2 = city2;
    }
}
