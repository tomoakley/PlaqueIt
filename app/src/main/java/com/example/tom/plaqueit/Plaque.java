package com.example.tom.plaqueit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created by Tom on 08/12/2015.
 */
public class Plaque implements Serializable {

    int id;
    String title;
    String description;
    String points;
    // String thumbnailUrl;
    Double latitude;
    Double longtitude;
    static MyDatabase database;

    Plaque(int id, String title, String description, String points, Double latitude, Double longtitude) {
        this.id = id;
        this.title = title;
        this.description = description;
        // this.thumbnailUrl = thumbnailUrl;
        this.points = points;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public static void setDatabase(Context context) {
        database = new MyDatabase(context);
    }

    public static Plaque getPlaqueByID(int id) {
        SQLiteDatabase db = database.getReadableDatabase();

        String colInscription = "inscription";
        String colSubject = "subject";
        // String colThumbnail = "thumbnail";
        String colLatitude = "latitude";
        String colLongitude = "longitude";

        String inscription = "";
        String subject = "";
        //  String thumbnail = "";
        Double latitude = 0.0;
        Double longitude = 0.0;

        String selectQuery = "SELECT " +
                colInscription + ", " +
                colSubject + ", " +
                // colThumbnail + ", " +
                colLatitude + ", " +
                colLongitude + " " +
                "FROM plaques " +
                "WHERE plaque_id = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                try {
                    inscription = new String(cursor.getBlob(cursor.getColumnIndex(colInscription)), "UTF-8");
                    subject = new String(cursor.getBlob(cursor.getColumnIndex(colSubject)), "UTF-8");
                    // thumbnail = new String(cursor.getBlob(cursor.getColumnIndex(colThumbnail)));
                    latitude = cursor.getDouble(cursor.getColumnIndex(colLatitude));
                    longitude = cursor.getDouble(cursor.getColumnIndex(colLongitude));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return new Plaque(id, subject, inscription, "20 points", latitude, longitude);
    }

}
