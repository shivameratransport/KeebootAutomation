package com.keeboot.utils.dataHelpers;

import java.util.HashMap;
import java.util.Map;

import com.keeboot.AutomationException;

public class HotelMapper {

    private static Map<String, String> hotelNameToCode = new HashMap<String, String>();
    private static Map<String, String> hotelCodeToName = new HashMap<String, String>();

    public static String getHotelCode(String hotelCode) {
        populateCodeMap();
        return hotelNameToCode.get(hotelCode.toLowerCase());
    }

    public static String getHotelName(String hotelCode) {
        populateHotelMap();
        return hotelCodeToName.get(hotelCode);
    }

    public static String getHotelCodePartial(String partialHotelName) {
        populateCodeMap();
        for (Map.Entry<String, String> entry : hotelNameToCode.entrySet()) {
            if (entry.getKey().toUpperCase().contains(partialHotelName.toUpperCase())
                    || partialHotelName.toUpperCase().contains(entry.getKey().toUpperCase())) {
                return entry.getValue();
            }
        }

        throw new AutomationException("The hotel name: " + partialHotelName + " was not found in list of hotels");

    }

    public static void populateCodeMap() {
        hotelNameToCode.put("MGM GRAND", "001");
        hotelNameToCode.put("THE SIGNATURE AT MGM GRAND", "005");
        hotelNameToCode.put("MGM GRAND DETROIT", "016");
        hotelNameToCode.put("NEW YORK NEW YORK", "021");
        hotelNameToCode.put("MIRAGE", "160");
        hotelNameToCode.put("BEAU RIVAGE", "180");
        hotelNameToCode.put("BELLAGIO", "190");
        hotelNameToCode.put("MANDALAY BAY RESORT AND CASINO", "275");
        hotelNameToCode.put("MGM RESORTS POOL CABANAS", "278");
        hotelNameToCode.put("LUXOR HOTEL CASINO LAS VEGAS", "280");
        hotelNameToCode.put("EXCALIBUR HOTEL CASINO LAS VEGAS", "285");
        hotelNameToCode.put("MONTE CARLO RESORT AND CASINO", "290");
        hotelNameToCode.put("CIRCUS CIRCUS LAS VEGAS", "295");
        hotelNameToCode.put("MGM NATIONAL HARBOR", "307");
        hotelNameToCode.put("GOLD STRIKE CASINO RESORT", "345");
        hotelNameToCode.put("AIRA VDARA POOL AND CABANAS", "905");
        hotelNameToCode.put("ARIA RESORT AND CASINO", "930");
        hotelNameToCode.put("VDARA CONDO HOTEL", "938");
    }

    public static void populateHotelMap() {
        hotelCodeToName.put("001", "MGM GRAND");
        hotelCodeToName.put("005", "THE SIGNATURE AT MGM GRAND");
        hotelCodeToName.put("016", "MGM GRAND DETROIT");
        hotelCodeToName.put("021", "NEW YORK NEW YORK");
        hotelCodeToName.put("160", "MIRAGE");
        hotelCodeToName.put("180", "BEAU RIVAGE");
        hotelCodeToName.put("190", "BELLAGIO");
        hotelCodeToName.put("275", "MANDALAY BAY RESORT AND CASINO");
        hotelCodeToName.put("278", "MGM RESORTS POOL CABANAS");
        hotelCodeToName.put("280", "LUXOR HOTEL CASINO LAS VEGAS");
        hotelCodeToName.put("285", "EXCALIBUR HOTEL CASINO LAS VEGAS");
        hotelCodeToName.put("290", "MONTE CARLO RESORT AND CASINO");
        hotelCodeToName.put("295", "CIRCUS CIRCUS LAS VEGAS");
        hotelCodeToName.put("307", "MGM NATIONAL HARBOR");
        hotelCodeToName.put("345", "GOLD STRIKE CASINO RESORT");
        hotelCodeToName.put("905", "AIRA VDARA POOL AND CABANAS");
        hotelCodeToName.put("930", "ARIA RESORT AND CASINO");
        hotelCodeToName.put("938", "VDARA CONDO HOTEL");
    }

}
