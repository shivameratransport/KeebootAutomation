package com.keeboot.utils.dataHelpers;

import java.util.ArrayList;
import java.util.List;

public class RoomMapper {

    private static List<String> roomDetails = new ArrayList<String>();

    /**
     * Processing details of room
     *
     * @author Pratik / Chidanand RG
     * @date 16/08/2018
     */
    private static String getRoomDetails(String roomNameIdCode) {
        populateRoomList();
        for (String room : roomDetails) {
            // Below is executed if room id or code is being passed
            if (room.contains(roomNameIdCode) && !roomNameIdCode.contains(" ")) {
                return room;
            }
            // Below is executed if room name is being passed
            if (room.split(":")[0].equalsIgnoreCase(roomNameIdCode) && roomNameIdCode.contains(" ")) {
                return room;
            }
        }
        return null;
    }

    /**
     * Get Room ID
     *
     * @author Pratik / Chidanand RG
     * @date 16/08/2018
     * @param room
     *            name or room id or room code
     * @return Room ID
     */
    public static String getRoomID(String roomNameOrIdOrCode) {
        return getRoomDetails(roomNameOrIdOrCode).split(":")[2];
    }

    /**
     * Get Room Name
     *
     * @author Pratik / Chidanand RG
     * @date 16/08/2018
     * @param room
     *            name or room id or room code
     * @return Room Name
     */
    public static String getRoomName(String roomNameOrIdOrCode) {
        return getRoomDetails(roomNameOrIdOrCode).split(":")[0];
    }

    /**
     * Get Room Code
     *
     * @author Pratik / Chidanand RG
     * @date 16/08/2018
     * @param room
     *            name or room id or room code
     * @return Room Code
     */
    public static String getRoomCode(String roomNameOrIdOrCode) {
        return getRoomDetails(roomNameOrIdOrCode).split(":")[1];
    }

    /**
     * @summary Populate Room List
     * @author Pratik / Chidanand RG
     * @version 16/08/2018
     */
    private static void populateRoomList() {
        roomDetails.add("Two Bedroom Tower Suite:L2TR:6bac8500-a54a-4dbc-8ed4-08f5c5cfb930");
        roomDetails.add("One Bedroom Penthouse Suite:L1PE:fa1a10d4-486b-4c51-8e05-d0225b0f7c22");
        roomDetails.add("Volcano View Room:DLVV:9f032181-aec3-4d18-af29-151b9d36fd7e");
        roomDetails.add("Resort Tower King:DLTD:1b57bf13-c62e-459b-98b1-e3f046150bc0");
        roomDetails.add("Pool View Room:DLPV:4f7285ca-1d1b-4d55-a534-2c2d8896ea23");
        roomDetails.add("Stay Well Hospitality:SHWP:ace2f2dc-30cc-4790-9fbf-e1728437880d");
        roomDetails.add("Stay Well Mirage Suite:STWS:749578cd-ef68-4c56-9957-9a8d0d2d2ebe");
        roomDetails.add("Two Bedroom Penthouse:L2PE:77a92f9c-3df4-44d8-b231-1e23b5a15687");
        roomDetails.add("Stay Well King:DGWK:6b2372ed-d155-45e6-92e7-309a1d1f0ffe");
        roomDetails.add("Stay Well Queen:DGWQ:1f11ec5f-9ef7-4a8a-9dec-1a3a8382dd43");
        roomDetails.add("Stay Well Accessible King:DWAK:3958f1fb-527c-4c20-a5e0-b1a18f94d15b");
        roomDetails.add("Stay Well Accessible Queen:DWAQ:e3ae919d-e866-4da9-ac91-2d20cdac2c0b");
        roomDetails.add("Stay Well Hospitality:DHWC:34c190e9-62ca-4acc-8386-8bc6d5fa034d");
        roomDetails.add("Mirage Suite:SPET:b28770e4-7e1f-4a1b-b4f8-d1181621f6d3");
        roomDetails.add("Hospitality Suite:SHOP:ed3ce167-4ec4-4171-8ec6-1c541ea23b6b");
        roomDetails.add("Accessible Resort King:DLAC:b9784f87-7c17-44a2-a4c9-c28d316b74e1");
        roomDetails.add("One Bedroom Tower Suite:L1TR:45462c3e-24cb-464d-8d01-6e09e1393d2e");
        roomDetails.add("Accessible King with Handi-Move Lift:SLKA:8f958c9f-f897-4afe-b96c-a74366311cb9");
        roomDetails.add("Accessible Queen:DLQA:473e031c-2d40-4237-96f6-9875c11e223f");
        roomDetails.add("Accessible 1 Bedroom Penthouse Suite with Roll-In Shower:L1PA:67cef964-2e3d-4f6d-a8a1-4cb7f6eabfc3");
        roomDetails.add("Accessible 2 Bedroom Penthouse Suite with Roll-In Shower:L2PA:a32a5dba-ef9d-46dc-abf8-04245788725e");
        roomDetails.add("Accessible 2 Bedroom Hospitality Suite with Roll-In Shower:SHOA:eae4469d-e161-4752-b191-65da925fba60");
        roomDetails.add("Accessible 2 Bedroom Tower Suite with Roll-In Shower:L2TA:d923f936-cf45-4019-9b00-743e9336cecc");
        roomDetails.add("Resort Queen:DLXQ:76a8d974-96b7-4b41-9e9d-d4fb6d28f9e7");
        roomDetails.add("Accessible Mirage Suite with Roll-In Shower:SPEA:706b5b79-5a7b-417c-bc45-fba95617d2e2");
        roomDetails.add("Accessible Queen with Handi-Move Lift:SLQA:f52b8bbf-7d47-4c03-b88c-2f2043f68f2d");
        roomDetails.add("One bedroom Penthouse Suite with Hearing Impaired Fire Alarm:DONOTUSELIPI:d8909a7e-3d1b-4042-9550-9da1189659dc");
        roomDetails.add("One bedroom Tower Suite with Hearing Impaired Fire Alarm:DONOTUSELITI:4943bdc1-12d1-41ec-97f0-a3b32c7a27e9");
        roomDetails.add("Tower Suite Connector:LTRC:875e4c80-1651-4269-a2eb-7b48db2cf3ec");
        roomDetails.add("Accessible 2 bedroom Hospitality Suite Connector with Roll in Shower:DHCA:7d4d5f70-c94e-49af-be10-18e3122928cb");
        roomDetails.add("Deluxe Hospitality Suite connector:DHOC:74525a05-e58b-4364-ac8a-8dbe88ccea19");
        roomDetails.add("Resort Tower King with Hearing Impaired Fire Alarm:DONOTUSEDTKI:56d372a9-3a3e-439e-9e5a-fc6bea272c3d");
        roomDetails.add("Resort Tower Queen with Hearing Impaired Fire Alarm:DONOTUSEDTQI:02e6e0ec-78a1-4a85-b4ae-13f3000e0ae3");
        roomDetails.add("Deluxe Tower Suite connector:DTRC:4ee28368-f7c2-408e-beb9-11809ff22740");
        roomDetails.add("Accessible Deluxe Room with Lift:SACC:ee3f6fb9-880c-4465-a09f-247c4f568206");
        roomDetails.add("Hospitality Suite Connector:SHOC:d9c095aa-a4b6-428e-a089-0184279b2135");
        roomDetails.add("Accessible 2 bedroom Tower Suite Connector with Roll in Shower:TCKA:286531cb-1c66-4114-a0ac-f80cc3005fe5");
        roomDetails.add("Accessible 2 bedroom Tower Suite Connector with Roll in Shower:TCQA:03e5ed89-5a3a-45c9-8e83-69a716c8e165");
        roomDetails.add("Mirage Suite with Hearing Impaired Fire Alarm:DONOTUSESPEI:ca09dfe4-b669-4923-abba-d6975049f761");
        roomDetails.add("One Bedroom Lanai:V1LA:ad0e11b8-0b1d-4385-b98e-1d15cf649757");
        roomDetails.add("Two Bedroom Lanai:V2LA:62e0d404-7380-403d-a420-8a6880a29c19");
        roomDetails.add("Two Bedroom Villa:V2VI:0d4e2e58-8eda-4c8b-b6d9-99946a3ddb63");
        roomDetails.add("Two Bedroom Remodeled Villa:V2VR:770c65d4-683b-4883-883a-e6e012033977");
        roomDetails.add("Three Bedroom Lanai:V3LA:a8f32afa-55c3-48af-a1bf-e8930089a3d0");
        roomDetails.add("Three Bedroom Villa:V3VI:8be5d22f-3460-4c1f-ae6c-748227ca0156");
        roomDetails.add("Entourage Room:VENT:c5dcaa6a-2ef6-43b4-a179-4f8e2423592f");
        roomDetails.add("Run of House Room:DROH:81e67733-03d6-4015-8bba-f3210bf798e1");
        roomDetails.add("Three Bedroom Remodeled Villa:V3VR:09fbcf88-952f-4b86-8ee9-c2d4455fe9a2");
        roomDetails.add("Queen Strip View Room:DLSQ:7dd9c26f-2237-449a-bb01-bca713fafed0");
        roomDetails.add("King Volcano Room:DLVK:9f251ea7-a79d-4fd0-a6c5-8799c72c0a0e");
        roomDetails.add("Queen Volcano Room:DLVQ:700316a0-8b2d-4e12-b2d1-dfa133a81868");
        roomDetails.add("King Strip View Room:DLSK:41d103f3-db1e-4ead-891a-ea690500ee20");
        roomDetails.add("Resort King:DLUX:e3fe2131-0677-4d2e-aaa3-27653a90d757");
        roomDetails.add("Halo Daybed:HLO:a6c68950-b3b4-4bff-b19e-b43f2308dc3d");
    }
}