package org.example.tourplanner.backend.app;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.tourplanner.backend.model.Tour;
import org.example.tourplanner.backend.service.OpenRouteService;

import java.io.IOException;
import java.util.ArrayList;

public class OpenStreetMap {
    private final OpenRouteService service = new OpenRouteService();

    public void createImage(Tour tour) {
        ArrayList<Double> bbox = getBbox(tour);
        if (bbox == null) {
            return;
        }

        MapCreator mapCreator = new MapCreator(bbox.get(0), bbox.get(1), bbox.get(2), bbox.get(3));
        mapCreator.setZoom(14);
        mapCreator.getMarkers().add(new MapCreator.GeoCoordinate(bbox.get(0), bbox.get(1)));

        try {
            mapCreator.generateImage();
            mapCreator.saveImage("tour_id_" + tour.getTourid() + ".png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Double> getBbox(Tour tour) {
        ArrayList<Double> returnBbox = new ArrayList<>();

        JsonNode fromAddress = service.searchAddress(tour.getFromPlace()).block();
        JsonNode toAddress = service.searchAddress(tour.getToPlace()).block();

        ArrayList<Double> fromBbox = extractBbox(fromAddress);
        ArrayList<Double> toBbox = extractBbox(toAddress);

        if (fromBbox == null || fromBbox.isEmpty() || toBbox == null || toBbox.isEmpty()) {
            return null;
        }

        // adding minLon
        returnBbox.add(Math.min(fromBbox.get(0), toBbox.get(0)));

        // adding minLat
        returnBbox.add(Math.min(fromBbox.get(1), toBbox.get(1)));

        // adding maxLon
        returnBbox.add(Math.max(fromBbox.get(2), toBbox.get(2)));

        // adding maxLat
        returnBbox.add(Math.max(fromBbox.get(3), toBbox.get(3)));

        return returnBbox;
    }

    private ArrayList<Double> extractBbox(JsonNode address) {
        if (address != null && address.has("bbox")) {
            ArrayList<Double> bbox = new ArrayList<>();
            address.get("bbox").forEach(b -> bbox.add(b.asDouble()));
            return bbox;
        }
        return null;
    }
}
