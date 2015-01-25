package org.verfallen.bestbusstopintheworld.googleplaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.verfallen.bestbusstopintheworld.http.HttpUtil;
import org.verfallen.bestbusstopintheworld.model.GooglePlacesJSON;

import android.location.Location;

public class GooglePlacesUtil {

    private static final String GOOGLE_PLACES_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String KEY = "AIzaSyAHdSSq4-SbUv_8kS2fORsQeJvJ4KRDlNg";

    public static class Status {
        public static final String ERROR = "ERROR";
        public static final String INVALID_REQUEST = "INVALID_REQUEST";
        public static final String OK = "OK";
        public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
        public static final String NOT_FOUND = "NOT_FOUND";
        public static final String REQUEST_DENIED = "REQUEST_DENIED";
        public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
        public static final String ZERO_RESULTS = "ZERO_RESULTS";
    }

    public static class Types {
        public static final String ACCOUNTING = "accounting";
        public static final String AIRPORT = "airport";
        public static final String AMUSEMENT_PARK = "amusement_park";
        public static final String AQUARIUM = "aquarium";
        public static final String ART_GALLERY = "art_gallery";
        public static final String ATM = "atm";
        public static final String BAKERY = "bakery";
        public static final String BANK = "bank";
        public static final String BAR = "bar";
        public static final String BEAUTY_SALON = "beauty_salon";
        public static final String BICYCLE_STORE = "bicycle_store";
        public static final String BOOK_STORE = "book_store";
        public static final String BOWLING_ALLEY = "bowling_alley";
        public static final String BUS_STATION = "bus_station";
        public static final String CAFE = "cafe";
        public static final String CAMPGROUND = "campground";
        public static final String CAR_DEALER = "car_dealer";
        public static final String CAR_RENTAL = "car_rental";
        public static final String CAR_REPAIR = "car_repair";
        public static final String CAR_WASH = "car_wash";
        public static final String CASINO = "casino";
        public static final String CEMETERY = "cemetery";
        public static final String CHURCH = "church";
        public static final String CITY_HALL = "city_hall";
        public static final String CLOTHING_STORE = "clothing_store";
        public static final String CONVENIENCE_STORE = "convenience_store";
        public static final String COURTHOUSE = "courthouse";
        public static final String DENTIST = "dentist";
        public static final String DEPARTMENT_STORE = "department_store";
        public static final String DOCTOR = "doctor";
        public static final String ELECTRICIAN = "electrician";
        public static final String ELECTRONICS_STORE = "electronics_store";
        public static final String EMBASSY = "embassy";
        public static final String ESTABLISHMENT = "establishment";
        public static final String FINANCE = "finance";
        public static final String FIRE_STATION = "fire_station";
        public static final String FLORIST = "florist";
        public static final String FOOD = "food";
        public static final String FUNERAL_HOME = "funeral_home";
        public static final String FURNITURE_STORE = "furniture_store";
        public static final String GAS_STATION = "gas_station";
        public static final String GENERAL_CONTRACTOR = "general_contractor";
        public static final String GROCERY_OR_SUPERMARKET = "grocery_or_supermarket";
        public static final String GYM = "gym";
        public static final String HAIR_CARE = "hair_care";
        public static final String HARDWARE_STORE = "hardware_store";
        public static final String HEALTH = "health";
        public static final String HINDU_TEMPLE = "hindu_temple";
        public static final String HOME_GOODS_STORE = "home_goods_store";
        public static final String HOSPITAL = "hospital";
        public static final String INSURANCE_AGENCY = "insurance_agency";
        public static final String JEWELRY_STORE = "jewelry_store";
        public static final String LAUNDRY = "laundry";
        public static final String LAWYER = "lawyer";
        public static final String LIBRARY = "library";
        public static final String LIQUOR_STORE = "liquor_store";
        public static final String LOCAL_GOVERNMENT_OFFICE = "local_government_office";
        public static final String LOCKSMITH = "locksmith";
        public static final String LODGING = "lodging";
        public static final String MEAL_DELIVERY = "meal_delivery";
        public static final String MEAL_TAKEAWAY = "meal_takeaway";
        public static final String MOSQUE = "mosque";
        public static final String MOVIE_RENTAL = "movie_rental";
        public static final String MOVIE_THEATER = "movie_theater";
        public static final String MOVING_COMPANY = "moving_company";
        public static final String MUSEUM = "museum";
        public static final String NIGHT_CLUB = "night_club";
        public static final String PAINTER = "painter";
        public static final String PARK = "park";
        public static final String PARKING = "parking";
        public static final String PET_STORE = "pet_store";
        public static final String PHARMACY = "pharmacy";
        public static final String PHYSIOTHERAPIST = "physiotherapist";
        public static final String PLACE_OF_WORSHIP = "place_of_worship";
        public static final String PLUMBER = "plumber";
        public static final String POLICE = "police";
        public static final String POST_OFFICE = "post_office";
        public static final String REAL_ESTATE_AGENCY = "real_estate_agency";
        public static final String RESTAURANT = "restaurant";
        public static final String ROOFING_CONTRACTOR = "roofing_contractor";
        public static final String RV_PARK = "rv_park";
        public static final String SCHOOL = "school";
        public static final String SHOE_STORE = "shoe_store";
        public static final String SHOPPING_MALL = "shopping_mall";
        public static final String SPA = "spa";
        public static final String STADIUM = "stadium";
        public static final String STORAGE = "storage";
        public static final String STORE = "store";
        public static final String SUBWAY_STATION = "subway_station";
        public static final String SYNAGOGUE = "synagogue";
        public static final String TAXI_STAND = "taxi_stand";
        public static final String TRAIN_STATION = "train_station";
        public static final String TRAVEL_AGENCY = "travel_agency";
        public static final String UNIVERSITY = "university";
        public static final String VETERINARY_CARE = "veterinary_care";
        public static final String ZOO = "zoo";
    }

    public static class Parametor {
        public static final String KEY = "key";
        public static final String LOCATION = "location";
        public static final String RADIUS = "radius";
        public static final String SENSOR = "sensor";
        public static final String KEYWORD = "keyword";
        public static final String LANGUAGE = "language";
        public static final String NAME = "name";
        public static final String RANKBY = "rankby";
        public static final String TYPES = "types";
        public static final String PAGETOKEN = "pagetoken";
    }

    public static class JSON {
        public static final String HTML_ATTRIBUTIONS = "html_attributions";
        public static final String STATUS = "status";
        public static final String RESULTS = "results";
        public static final String FORMATTED_ADDRESS = "formatted_address";
        public static final String GEOMETRY = "geometry";
        public static final String VIEWPORT = "viewport";
        public static final String LOCATION = "location";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String ICON = "icon";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TYPES = "types";
        public static final String OPENING_HOURS = "opening_hours";
        public static final String OPEN_NOW = "open_now";
        public static final String RATING = "rating";
        public static final String ASPECTS = "aspects";
        public static final String REFERENCE = "reference";
    }

    public static List<GooglePlacesJSON> getGooglePlaces( double aLatitude, double aLongitude, String aTypes,
            String aLanguage ) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add( new BasicNameValuePair( GooglePlacesUtil.Parametor.LOCATION, aLatitude + "," + aLongitude ) );
        params.add( new BasicNameValuePair( GooglePlacesUtil.Parametor.TYPES, aTypes ) );
        params.add( new BasicNameValuePair( GooglePlacesUtil.Parametor.SENSOR, "false" ) );
        params.add( new BasicNameValuePair( GooglePlacesUtil.Parametor.LANGUAGE, aLanguage ) );
        // Do not included the "radius" parameter with rankBy=distance
        params.add( new BasicNameValuePair( GooglePlacesUtil.Parametor.RANKBY, "distance" ) );
        // params.add( new BasicNameValuePair( "radius", "500" ) );
        params.add( new BasicNameValuePair( GooglePlacesUtil.Parametor.KEY, KEY ) );
        String googlePlacesResponseJSON = HttpUtil.doGet( GOOGLE_PLACES_URL, params );

        List<GooglePlacesJSON> googlePlacesJSONList = createGooglePlacesJSON( googlePlacesResponseJSON );
        for ( GooglePlacesJSON googlePlace : googlePlacesJSONList ) {
            float[] results = new float[1]; // 0:Distance 1:bearing
            Location.distanceBetween( aLatitude, aLongitude, googlePlace.mLocationLat, googlePlace.mLocationLng,
                    results );
            googlePlace.mDistance = Math.round( results[0] ); // 0:Distance
        }
        return googlePlacesJSONList;
    }

    public static List<GooglePlacesJSON> createGooglePlacesJSON( String aGooglePlacesJson ) {
        List<GooglePlacesJSON> googlePlacesJSONList = new ArrayList<GooglePlacesJSON>();
        try {
            JSONObject jsonObj = new JSONObject( aGooglePlacesJson );
            String status = jsonObj.getString( GooglePlacesUtil.JSON.STATUS );
            if ( !status.equals( GooglePlacesUtil.Status.OK ) ) {
                return null;
            }
            JSONArray results = jsonObj.getJSONArray( GooglePlacesUtil.JSON.RESULTS );
            for ( int i = 0; i < results.length(); i++ ) {
                GooglePlacesJSON googlePlaces = new GooglePlacesJSON();
                JSONObject result = results.getJSONObject( i );
                JSONObject geometry = result.getJSONObject( GooglePlacesUtil.JSON.GEOMETRY );
                JSONObject location = geometry.getJSONObject( GooglePlacesUtil.JSON.LOCATION );
                googlePlaces.mLocationLat = location.getDouble( GooglePlacesUtil.JSON.LAT );
                googlePlaces.mLocationLng = location.getDouble( GooglePlacesUtil.JSON.LNG );
                googlePlaces.mIcon = result.getString( GooglePlacesUtil.JSON.ICON );
                googlePlaces.mId = result.getString( GooglePlacesUtil.JSON.ID );
                googlePlaces.mName = result.getString( GooglePlacesUtil.JSON.NAME );
                googlePlaces.mReference = result.getString( GooglePlacesUtil.JSON.REFERENCE );
                JSONArray types = result.getJSONArray( GooglePlacesUtil.JSON.TYPES );
                ArrayList<String> typeList = new ArrayList<String>();
                for ( int j = 0; j < types.length(); j++ ) {
                    typeList.add( types.getString( j ) );
                }
                googlePlaces.mTypes = typeList.toArray( new String[0] );
                googlePlacesJSONList.add( googlePlaces );
            }
        } catch ( JSONException e ) {
            e.printStackTrace();
        }
        return googlePlacesJSONList;

    }
}
