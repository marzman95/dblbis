import de.fhpotsdam.unfolding.marker.MarkerManager;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that runs the map for the StartScreen.
 */
public class StartMap extends PApplet {
    private DataManager dataManager = DataManager.getDataManager();
    UnfoldingMap map;
    private List<Marker> selectedMarkers = new ArrayList<Marker>();
    private List<CityTotal> checkedCities = new ArrayList<CityTotal>();
    private List<CityPair> checked2Cities = new ArrayList<CityPair>();
    /**
     * Setups the map
     */
    public void setup() {
        size(1000, 900);
        map = new UnfoldingMap(this, new OpenStreetMapProvider());
        MapUtils.createDefaultEventDispatcher(this, map);

        // Set center location (Brussels)
        Location centerLocation = new Location(50.85f, 4.35f);
        map.zoomAndPanTo(5, centerLocation);

        // Restricts the map panning
        float maxPanningDistance = 1000; // Which is in km
        map.setPanningRestriction(centerLocation, maxPanningDistance);

        List<City> popularCities = dataManager.getPopularCities(50); // List of the 50 most popular cities


        try {
           List<anEdge> popularEdges = dataManager.getPopularEdges(50);
            addEdges(map.getDefaultMarkerManager(), popularEdges);
        } catch (Exception e) {
            System.out.println("[Exception StartMap]: Exception during creation of markers: " + e);
        }

        // Drawing of the most 50 popular cities
        try {
            addMarkers(map.getDefaultMarkerManager(), popularCities);
        } catch (Exception e) {
            System.out.println("[Exception StartMap]: Exception during creation of markers: " + e);
        }

    }

    /**
     * Draws the map
     */
    public void draw() {
        try {
            map.draw();
        } catch (Exception e) {
            System.out.println("Problem drawing map: " + e);
        }
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
        boolean found = false;
        if (hitMarker != null) {
            if(selectedMarkers.size()==1){
                for(Iterator<Marker> iterator =selectedMarkers.iterator(); ((Iterator) iterator).hasNext();) {
                    Marker marker = iterator.next();
                    if(hitMarker.getLocation().x == marker.getLocation().x && hitMarker.getLocation().y == marker.getLocation().y) {
                        iterator.remove();
                        found = true;
                        hitMarker.setColor(color(34, 24, 155));
                        hitMarker.setSelected(false);
                    }
                }
                if(!found) {
                    for(CityTotal acity:checkedCities){
                        if(acity.getLongitude() == hitMarker.getLocation().y && acity.getLatitude() == hitMarker.getLocation().x ){
                            hitMarker.setColor(color(0, 255,0,255));
                            hitMarker.setSelected(true);
                            selectedMarkers.add(hitMarker);
                            Screen.getScreen().getStartScreen().filltable(acity);
                            return;
                        }
                    }
                    hitMarker.setColor(color(0, 255,0,255));
                    hitMarker.setSelected(true);
                    selectedMarkers.add(hitMarker);
                    float markerX = hitMarker.getLocation().x;
                    float markerY = hitMarker.getLocation().y;
                    CityTotal city = Screen.getScreen().getStartScreen().setMarker(markerX, markerY,dataManager);
                    checkedCities.add(city);
                }
            }
            else if(selectedMarkers.size()==2){
                for(Iterator<Marker> iterator =selectedMarkers.iterator(); ((Iterator) iterator).hasNext();) {
                    Marker marker = iterator.next();
                    if(hitMarker.getLocation().x == marker.getLocation().x && hitMarker.getLocation().y == marker.getLocation().y) {
                        hitMarker.setColor(color(34, 24, 155));
                        hitMarker.setSelected(false);
                        iterator.remove();
                    }
                }
            }
            else{
                for(CityTotal acity:checkedCities){
                    if(acity.getLongitude() == hitMarker.getLocation().y && acity.getLatitude() == hitMarker.getLocation().x ){
                        hitMarker.setColor(color(0, 255,0,255));
                        hitMarker.setSelected(true);
                        selectedMarkers.add(hitMarker);
                        Screen.getScreen().getStartScreen().filltable(acity);
                        return;
                    }
                }
               hitMarker.setColor(color(0, 255,0,255));
               hitMarker.setSelected(true);
               selectedMarkers.add(hitMarker);
               float markerX = hitMarker.getLocation().x;
               float markerY = hitMarker.getLocation().y;
               CityTotal city = Screen.getScreen().getStartScreen().setMarker(markerX, markerY,dataManager);
               checkedCities.add(city);
            }
        } else {
            for (Marker marker : map.getMarkers()) {
                marker.setSelected(false);
            }
        }
    }


    /**
     * Adds markers to the map
     * @param mm the MarkerManager that manages the markers
     * @param citiesToAdd the list of {@link models.City} to be added
     */
    private void addMarkers(MarkerManager mm, List<City> citiesToAdd) {
            Location genericLocation = new Location(0, 0);
            SimplePointMarker genericMarker = new SimplePointMarker();
            for (int i = 0; i < citiesToAdd.size(); i++) {
                City curCity = citiesToAdd.get(i);
                genericLocation = new Location(curCity.getLatitude(), curCity.getLongitude());
                genericMarker = new SimplePointMarker(genericLocation);
                int drawRadiusMode = Screen.getScreen().getStartScreen().curInfoMode;
                if (drawRadiusMode == 1) {
                    genericMarker.setRadius(((float) curCity.getTimesVisited() / 800) + 5);
                } else if (drawRadiusMode == 2) {
                    genericMarker.setRadius(((float) curCity.getIndegree() / 20) + 5);
                } else if (drawRadiusMode == 3) {
                    genericMarker.setRadius(((float) curCity.getOutdegree() / 20) + 5);
                } else if (drawRadiusMode == 4) {
                    genericMarker.setRadius(((float) curCity.getTotaldegree() / 20) + 5);
                }
                genericMarker.setColor(color(0,0,255,255));
                mm.addMarker(genericMarker);
            }
    }

    private void addEdges(MarkerManager mm, List<anEdge> edgesToAdd) {
        Location genericLocation1 = new Location(0, 0);
        Location genericLocation2 = new Location (0, 0);
        for (int i = 0; i < edgesToAdd.size(); i++) {
            anEdge connect = edgesToAdd.get(i);
            genericLocation1 = new Location(connect.getLatitude1(), connect.getLongitude1());
            genericLocation2 = new Location (connect.getLatitude2(), connect.getLongitude2());
            SimpleLinesMarker line = new SimpleLinesMarker(genericLocation1, genericLocation2);
            mm.addMarker(line);
            // line.setColor(-10000);
            line.setStrokeWeight(connect.getFrequency()/1000);
        }
    }

    /**
     * Removes all the markers from the map
     * @param mm the MarkerManager that manages the markers
     */
    private void removeAll(MarkerManager mm) {
        for(Iterator<Marker> iterator = mm.getMarkers().iterator(); ((Iterator) iterator).hasNext();) {
            iterator.next();
            iterator.remove();
        }
        for(Iterator<Marker> iterator =selectedMarkers.iterator(); ((Iterator) iterator).hasNext();) {
            iterator.next();
            iterator.remove();
        }
    }

    private void removeEdges() {

    }

    /**
     * Changes the map
     */
    public void changeMap() {
        MarkerManager mm = map.getDefaultMarkerManager();
        StartScreen ss = Screen.getScreen().getStartScreen();
        for(Iterator<Marker> iterator = mm.getMarkers().iterator(); ((Iterator) iterator).hasNext();) {
            iterator.next();
            iterator.remove();
        }
        try {
            removeAll(mm);
        } catch (Exception e) {
            System.out.println("Problem removing all during [changeMap()]: " + e);
        }
        try {
            if (ss.routesDisplayed) {
                List<anEdge> edgeList = dataManager.getPopularEdges(Screen.getScreen().getStartScreen().routesAmount);
                addEdges(mm, edgeList);
            }
            if (ss.citiesDisplayed) {
                List<City> citiesList = dataManager.getPopularCities(Screen.getScreen().getStartScreen().citiesAmount);
                addMarkers(mm, citiesList);
            }

        } catch (Exception e) {
            System.out.println("Problem adding all during [changeMap()]: " + e);
        }
    }


}
