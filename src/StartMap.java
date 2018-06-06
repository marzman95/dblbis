import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import models.*;
import models.anEdge;


import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap.*;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.sql.Connection;
import java.util.List;

/**
 * Class that runs the map for the StartScreen.
 */
public class StartMap extends PApplet {
    private DataManager dataManager = DataManager.getDataManager();
    UnfoldingMap map;
    private List<City> popularCities = dataManager.getPopularCities(); // List of the 50 most popular cities
    private List<anEdge> popularEdges = dataManager.getEdges();

    // Float normalize was never used

    /**
     * Setups the map
     */
    public void setup() {
        size(800, 600);
        map = new UnfoldingMap(this, new OpenStreetMapProvider());
        MapUtils.createDefaultEventDispatcher(this, map);

        // Set center location (Brussels)
        Location centerLocation = new Location(50.85f, 4.35f);
        map.zoomAndPanTo(5, centerLocation);

        // Restricts the map panning
        float maxPanningDistance = 700; // Which is in km
        map.setPanningRestriction(centerLocation, maxPanningDistance);

        // Creates the actual markers
        try {
            Location genericLocation = new Location(0, 0);
            SimplePointMarker genericMarker = new SimplePointMarker();
            for (int i = 0; i < popularCities.size(); i++) {
                City curCity = popularCities.get(i);
                genericLocation = new Location(curCity.getLatitude(), curCity.getLongitude());
                genericMarker = new SimplePointMarker(genericLocation);
                genericMarker.setRadius(((float)curCity.getTimesVisited()/800)+5);
                genericMarker.setColor(color(34, 24, 155));
                map.addMarker(genericMarker);
            }
        } catch (Exception e) {
            System.out.println("[Exception StartMap]: Exception during creation of markers: " + e);
        }

        try {
            Location genericLocation1 = new Location(0, 0);
            Location genericLocation2 = new Location (0, 0);
            for (int i = 0; i < popularEdges.size(); i++) {
                anEdge connect = popularEdges.get(i);
                genericLocation1 = new Location(connect.getLatitude1(), connect.getLongitude1());
                genericLocation2 = new Location (connect.getLatitude2(), connect.getLongitude2());
                SimpleLinesMarker line = new SimpleLinesMarker(genericLocation1, genericLocation2);


                map.addMarker(line);
               // line.setColor(-10000);
                line.setStrokeWeight(connect.getFrequency()/1000);

            }
        } catch (Exception e) {
            System.out.println("[Exception StartMap]: Exception during creation of markers: " + e);
        }


    }

    /**
     * Draws the map
     */
    public void draw() {
        map.draw();
    }

    /**
     * Implements mouse hovering on markers.
     */
    public void mouseMoved() {
        Marker hitMarker = map.getFirstHitMarker(mouseX, mouseY);
        if (hitMarker != null) {
            hitMarker.setSelected(true);
        } else {
            for (Marker marker : map.getMarkers()) {
                marker.setSelected(false);
            }
        }
    }

    /**
     * Listens to a mouse click on a marker.
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
}
