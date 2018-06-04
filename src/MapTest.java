/**
 * DEPRECATED!
 */

import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap.*;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;



public class MapTest extends PApplet {
    private DataManager dataManager = DataManager.getDataManager();


    UnfoldingMap map;
    
    public ResultSet test() throws Exception{
//    	ExecutorService executor = Executors.newCachedThreadPool();
//    	Future<ResultSet> futureCall = executor.submit(new Query("heat"));
    	ResultSet result = dataManager.getHeatmapData();
    	return result;
    }
    
    public ResultSet test1() throws Exception{
    	ExecutorService executor = Executors.newCachedThreadPool();
    	Future<ResultSet> futureCall = executor.submit(new Query("routes"));
    	ResultSet result = futureCall.get();
    	return result;
    }
    
    
    public float normalize(int value, int minValue, int maxValue){
    	float res = (value - minValue / (maxValue - minValue));
    	return res;
    }
    
    //public int getRGB val(int select, float value) {
    	
    	
    //	return 0;
   // }
    
    
   // public String heatMapColors(int value) {
    //	int h = 
    //}
    
    public void setup() {
    	
    	   	
    	//set window size
        size(800, 600);
        //create map object
        map = new UnfoldingMap(this, new OpenStreetMapProvider());
        MapUtils.createDefaultEventDispatcher(this, map);

        //create location, have map center on said location
        Location brusselsLocation = new Location(50.85f, 4.35f);
        map.zoomAndPanTo(5, brusselsLocation);

        //restrict the panning (so the map is only in Europe, not the
        //whole world)
        float maxPanningDistance = 700; // in km
        map.setPanningRestriction(brusselsLocation, maxPanningDistance);
        
       
        
        //Working with ResultSet
        ResultSet heatmap = null;
        try {
        	//Color color = new Color();
			heatmap = test();
			Location genericLocation = new Location(0, 0);
			SimplePointMarker genericMarker = new SimplePointMarker();
			 while (heatmap.next()) {
//				 	if (counter == 0) {timesMax = heatmap.getInt("Times-visited");};
//				 	if (counter == 49) {timesMin = heatmap.getInt("Times-visited");};
				 	genericLocation = new Location (heatmap.getDouble("Latitude"), heatmap.getDouble("Longitude"));
				 	genericMarker = new SimplePointMarker(genericLocation);
				 	genericMarker.setRadius(((float)heatmap.getInt("Times-visited")/800)+5);
				 	genericMarker.setColor(color(34, 24, 155));
				 	//genericMarker.setStrokeWeight(heatmap.getInt("Times-visited"));
				 	map.addMarker(genericMarker);
//				 	counter++;

				 	//}

		        }
//
//
//
//
//
//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		 
//	ResultSet heatmap1 = null;
//    try {
//    	
//		heatmap1 = test1();
//		Location genericLocation1 = new Location(0, 0);
//		Location genericLocation2 = new Location (0, 0);
//		SimpleLinesMarker line = new SimpleLinesMarker(genericLocation1, genericLocation2);
//		
//	
//		 while (heatmap1.next()) {
//			 	
//			 	//if (heatmap.getInt("Times-visited")>50) {
//			 	
//			 	genericLocation1 = new Location (heatmap1.getDouble("Latitude"), heatmap1.getDouble("Longitude"));
//			 	genericLocation2 = new Location (heatmap1.getDouble("Latitude1"), heatmap1.getDouble("Longitude1"));
//			 	line = new SimpleLinesMarker(genericLocation1, genericLocation2);
//			 	//}	
//	        } 
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//    
        
       
        
        
        //Create list with locations (to-do: add locations 
        //automatically from database )
//        ArrayList<Location> loclist = new ArrayList<Location>();
//        loclist.add(new Location (54.4, 16.3));
//        loclist.add(new Location (46.1, 10.3));
//        SimplePointMarker marker = new SimplePointMarker();
//        
//        //Add a marker for each location in list, and add these
//        //markers to the map
//        for (Location l: loclist) {
//        	marker = new SimplePointMarker(l);
//        	//map.addMarker(marker);
//        
//        }
        
        //add new marker with and set it to selected
//        SimplePointMarker brusselsMarker = new SimplePointMarker(brusselsLocation);
//        map.addMarker(brusselsMarker);
       // brusselsMarker.setSelected(true);
             
        
        //to-do: interactions

        
      
    }

    public void draw() {
        map.draw();
    }

    /**
     * Gives visual feedback on hovering over a marker
     */
    public void mouseMoved() {
        Marker hitMarker = map.getFirstHitMarker(mouseX, mouseY);
        if ( hitMarker != null) {
            hitMarker.setSelected(true);
        } else {
            for (Marker marker : map.getMarkers()) {
                marker.setSelected(false);
            }
        }
    }

    /**
     * Listens to a mouse click for the markers.
     */
    public void mouseClicked() {
        Marker hitMarker = map.getFirstHitMarker(mouseX, mouseY);
        if (hitMarker != null) {
            hitMarker.setColor(color(0, 255,0,255));
            hitMarker.setSelected(true);
            float markerX = hitMarker.getLocation().x;
            float markerY = hitMarker.getLocation().y;
            Screen.getScreen().getStartScreen().setMarker(markerX, markerY);
        } else {
            for (Marker marker : map.getMarkers()) {
                marker.setSelected(false);
            }
        }
    }

    /**
     * TODO: delete this function to prevent bugs
     * Starts the map as a separate instance; has no function in the project itself, and can even break since
     * it does not necessarily have the required dependencies of other classes.
     * @param args
     */
    public static void main(String[] args) {
       PApplet.main(new String[] { "MapTest" });
   }

}
