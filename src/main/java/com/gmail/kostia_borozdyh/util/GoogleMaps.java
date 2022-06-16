package com.gmail.kostia_borozdyh.util;

import com.gmail.kostia_borozdyh.dto.InfoTableDTO;
import com.gmail.kostia_borozdyh.dto.PointDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class GoogleMaps {
    public static final String API_KEY = "&key=AIzaSyDZ_4ASyzLdt1d16-mekZg5W4X24P0zIR4";
    public static final String HTTP_MAPS = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
    public static final String HTTP_GEOCODE = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static final String DESTINATIONS = "&destinations=";
    public static final String LANGUAGE = "&language=uk&departure_time=now";

    public static List<InfoTableDTO> getDistance(String cityFrom, String cityTo) throws ParseException {
        List<InfoTableDTO> distanceList = new ArrayList<>();
        try {
            log.info("making request to GoggleAPI distance between cityFrom - "+cityFrom+" and cityTo - "+cityTo);
            OkHttpClient client = new OkHttpClient();
            String url = HTTP_MAPS + cityFrom + DESTINATIONS + cityTo + LANGUAGE + API_KEY;
            Request googleRequest = new Request.Builder()
                    .url(url)
                    .build();
            Response googleResponse = client.newCall(googleRequest).execute();
            assert googleResponse.body() != null;
            String js = googleResponse.body().string();
            distanceList = JsonParser.parseGoogleApiDistance(js);
            googleResponse.close();
        } catch (Exception ex) {
            log.error("problem with request that we send GoogleAPI distance");
            log.error("Exception - "+ex);
        }
        return distanceList;
    }

    public static PointDTO getCityCoordinates(String city) {
        PointDTO point = new PointDTO();
        try {
            log.info("making request to GoggleAPI geocode in city - "+city);
            OkHttpClient client = new OkHttpClient();
            String url = HTTP_GEOCODE + city + API_KEY;
            Request googleRequest = new Request.Builder()
                    .url(url)
                    .build();
            Response googleResponse = client.newCall(googleRequest).execute();
            assert googleResponse.body() != null;
            String js = googleResponse.body().string();
            point = JsonParser.parseGoogleApiGeocode(js);
            googleResponse.close();
        } catch (Exception ex) {
            log.error("problem with request that we send GoogleAPI geocode");
            log.error("Exception - "+ex);
        }
        return point;
    }
}
