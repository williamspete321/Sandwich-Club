package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String sandwichJsonStr) throws JSONException {

        final String SW_NAME = "name";

        final String SW_MAIN_NAME = "mainName";
        final String SW_AKA = "alsoKnownAs";

        final String SW_POO = "placeOfOrigin";
        final String SW_DESCRIPTION = "description";
        final String SW_IMAGE = "image";
        final String SW_INGREDIENTS = "ingredients";

        final String SW_FALL_BACK = "Not Available";


        Sandwich sandwich = null;

        JSONObject sandwichJSON = new JSONObject(sandwichJsonStr);

        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        JSONObject jsonNameObject = sandwichJSON.getJSONObject(SW_NAME);
        mainName = jsonNameObject.getString(SW_MAIN_NAME);

        JSONArray akaArray = jsonNameObject.getJSONArray(SW_AKA);
        alsoKnownAs = parseJSONStringArray(akaArray);

        placeOfOrigin = sandwichJSON.optString(SW_POO, SW_FALL_BACK);

        description = sandwichJSON.optString(SW_DESCRIPTION, SW_FALL_BACK);

        image = sandwichJSON.getString(SW_IMAGE);

        JSONArray ingredientsArray = sandwichJSON.getJSONArray(SW_INGREDIENTS);
        ingredients = parseJSONStringArray(ingredientsArray);

        sandwich =
                new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }

    private static ArrayList parseJSONStringArray(JSONArray jsonArray) throws JSONException {
        ArrayList<String> array = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            String str = jsonArray.getString(i);
            array.add(str);
        }
        return array;
    }
}
