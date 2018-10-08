package com.keeboot.api.utils;

public class JSONUtils {

    /**
     * This method will handle any json string which is having duplicate keys
     * all the duplicate keys will be retained and put inside one json array
     *
     * @author Pratik
     * @date 07/24/2018
     * @param jsonString
     *            with duplicate keys
     * @return jsonString after updating all the duplicate keys inside one json
     *         array
     */
    public static String JSONObjectRetainDuplicates(String json) {
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(json); // FromObject
        return jsonObject.toString();
    }
}