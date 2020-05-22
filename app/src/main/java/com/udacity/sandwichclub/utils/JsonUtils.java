package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //transform our String into a JSON object
        JSONObject jsonObject = new JSONObject(json);

        //init new sandwich to deserialize data into model object
        Sandwich sandwich = new Sandwich();

        //name is two levels deep with one key/value pair being main name
        JSONObject names = jsonObject.getJSONObject("name");
        sandwich.setMainName(names.getString("mainName"));

        //second value is a JSON array with the key alsoKnownAs. As this array can be empty, we should skip assigning
        //new variables for the array and corresponding list in the model if empty
        if(names.getJSONArray("alsoKnownAs") != null && names.getJSONArray("alsoKnownAs").length() > 0) {
            List<String> akaList = new ArrayList<>();
            JSONArray akaArray = names.getJSONArray("alsoKnownAs");
            for (int position = 0; position < akaArray.length(); position++) {
                akaList.add(akaArray.get(position).toString());
            }
            sandwich.setAlsoKnownAs(akaList);
        }

        //more simple key/value pairs where we transform value to String
        sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
        sandwich.setDescription(jsonObject.getString("description"));
        sandwich.setImage(jsonObject.getString("image"));

        //we can skip this array list if null or empty
        if(jsonObject.getJSONArray("ingredients") != null && jsonObject.getJSONArray("ingredients").length() > 0) {
            JSONArray ingredientArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredientList = new ArrayList<>();
            for (int position = 0; position < ingredientArray.length(); position++) {
                ingredientList.add(ingredientArray.get(position).toString());
            }
            sandwich.setIngredients(ingredientList);
        }
        return sandwich;
    }
}
