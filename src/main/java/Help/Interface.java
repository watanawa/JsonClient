package Help;

import Json.JSONDebugDataMessage;
import UDP.UDPSender;
import dsf.TypeEquipmentDescription;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Interface {
    //IM TOO STUPID TO PASS OBJECT INSTANCES TO THE CONTROLLLERS, SO I SIMLPLY USE STATIC METHODS
    private static TypeEquipmentDescription typeEquipmentDescription;
    private static UDPSender sender;
    private static JSONDebugDataMessage jsonDebugDataMessage = null;
    private static String timeStamp;

    public static TypeEquipmentDescription getTypeEquipmentDescription() {
        return typeEquipmentDescription;
    }

    public static void setTypeEquipmentDescription(TypeEquipmentDescription typeEquipmentDescription) {
        Interface.typeEquipmentDescription = typeEquipmentDescription;
    }

    public static UDPSender getSender() {
        return sender;
    }

    public static void setSender(UDPSender sender) {
        Interface.sender = sender;
    }

    public static JSONDebugDataMessage getJsonDebugDataMessage() {
        return jsonDebugDataMessage;
    }

    public static void setJsonDebugDataMessage(JSONDebugDataMessage jsonDebugDataMessage) {
        Interface.jsonDebugDataMessage = jsonDebugDataMessage;
        setTimeStamp(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    public static String getTime() {
        return timeStamp;
    }

    public static void setTimeStamp(String timeStamp) {
        Interface.timeStamp = timeStamp;
    }
}
