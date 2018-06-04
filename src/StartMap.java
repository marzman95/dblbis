import java.sql.ResultSet;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap.*;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

/**
 * Class that runs the map for the StartScreen.
 */
public class StartMap extends PApplet {
    private DataManager dataManager = DataManager.getDataManager();
    UnfoldingMap map;
    private ResultSet popularCities = dataManager.getHeatmapData(); // List of the 50 most popular cities

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
            while (popularCities.next()) {
                genericLocation = new Location(popularCities.getDouble("Latitude"), popularCities.getDouble("Longitude"));
                genericMarker = new SimplePointMarker(genericLocation);
                genericMarker.setRadius(((float)popularCities.getInt("Times-visited")/800)+5);
                genericMarker.setColor(color(34, 24, 155));
                map.addMarker(genericMarker);
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