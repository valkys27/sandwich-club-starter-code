package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.*;

import java.util.*;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return parseSandwichJSONObject(jsonObject);
        } catch (JSONException e) {
            return null;
        }
    }

    private static Sandwich parseSandwichJSONObject(JSONObject jsonObject) throws JSONException {
        JSONObject name = jsonObject.getJSONObject(SandwichJson.NAME);
        String mainName = name.getString(SandwichJson.MAIN_NAME);
        JSONArray alsoKnownAsJson = name.getJSONArray(SandwichJson.ALSO_KNOWN_AS);
        List<String> alsoKnownAs = parseStringList(alsoKnownAsJson);
        String placeOfOrigin = jsonObject.getString(SandwichJson.PLACE_OF_ORIGIN);
        if (placeOfOrigin.equals(""))
            placeOfOrigin = "Unknown";
        String description = jsonObject.getString(SandwichJson.DESCRIPTION);
        String image = jsonObject.getString(SandwichJson.IMAGE);
        JSONArray ingredientsJson = jsonObject.getJSONArray(SandwichJson.INGREDIENTS);
        List<String> ingredients = parseStringList(ingredientsJson);
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> parseStringList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++)
            list.add(jsonArray.getString(i));
        return list;
    }

    private static class SandwichJson {
        static final String NAME = "name";
        static final String MAIN_NAME = "mainName";
        static final String ALSO_KNOWN_AS = "alsoKnownAs";
        static final String PLACE_OF_ORIGIN = "placeOfOrigin";
        static final String DESCRIPTION = "description";
        static final String IMAGE = "image";
        static final String INGREDIENTS = "ingredients";
    }
}
