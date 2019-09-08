package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
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

        final String SW_MESSAGE_COD = "cod";

        // Sandwich object to hold parsed JSON data
        Sandwich sandwich = null;

        JSONObject sandwichJSON = new JSONObject(sandwichJsonStr);

        if(sandwichJSON.has(SW_MESSAGE_COD)) {
            int errorCode = sandwichJSON.getInt(SW_MESSAGE_COD);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

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

        placeOfOrigin = sandwichJSON.getString(SW_POO);

        description = sandwichJSON.getString(SW_DESCRIPTION);

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
