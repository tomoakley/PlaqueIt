package com.example.tom.plaqueit;

/**
 * Created by Tom on 08/12/2015.
 */
public class Plaque {

    String title;
    String description;
    String points;
    // String thumbnailUrl;
    Double latitude;
    Double longtitude;

    Plaque(String title, String description, String points, Double latitude, Double longtitude) {
        this.title = title;
        this.description = description;
        // this.thumbnailUrl = thumbnailUrl;
        this.points = points;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

}
