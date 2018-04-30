package com.udacity.sandwichclub.utils;

//Imports:

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws RuntimeException {
        Sandwich sandwich = null;
        try {
            //Parses each appropriate string from the json string.
            JSONObject sandwichJSON = new JSONObject(json);
            //Gets the all the object details and mainName from the JSON string.
            JSONObject name = sandwichJSON.getJSONObject("name");
            String mainName = name.getString("mainName");

            //Creates a JSON Array of AKA & Ingredients data. Calls Method to convert to List<String>.
            JSONArray alsoKnownAsJSON = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = convertJsonArray(alsoKnownAsJSON);
            Log.d("List Contents:", alsoKnownAs.toString());

            JSONArray ingredientsJson = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredients = convertJsonArray(ingredientsJson);
            Log.d("Ingredients List", ingredients.toString());



            //Gets the remaining details from the JSON Object needed for the sandwich Constructor.

            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");
            String description = sandwichJSON.getString("description");
            String image = sandwichJSON.getString("image");

            //Creates sandwich Object with parsed data.
            sandwich = new Sandwich(mainName, alsoKnownAs,placeOfOrigin,description,image,ingredients);
            } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
    /* Converts JSON Array to a List of Type String. */
    private static List<String> convertJsonArray(JSONArray jsonArray) {
        int length = jsonArray.length();
        List<String> converted = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            try {
                converted.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return converted;
    }//End Convert Json Array Method.
}
