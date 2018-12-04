package UDP;

import Help.Interface;
import Json.JSONDebugDataMessage;
import Json.JSONDebugDataReadRequest;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;

public class MessageHandler {

    private UDPSender udpSender;
    private ArrayList<LinkedList<String>> requestedRecordElements;

    public MessageHandler(UDPSender sender){
        udpSender = sender;
        requestedRecordElements = new ArrayList<LinkedList<String>>();
    }

    public void handleMessage(byte[] data, int offset, int length){
        byte[]temp = Arrays.copyOfRange(data, offset, offset+length);
        String jsonMessage = null;
        try {
            jsonMessage = new String(temp, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(jsonMessage.startsWith("{\"JSONDebugDataMessage\"")){
            handleJSONDebugDataMessage(jsonMessage);
        }
    }

    public void addRecordElementPath(LinkedList<String> path){
        requestedRecordElements.add(path);
    }
    public ArrayList<LinkedList<String>> getRequestedRecordElements(){
        return requestedRecordElements;
    }
    public void sendRequests(){
        JSONDebugDataReadRequest jsonDebugDataReadRequest = new JSONDebugDataReadRequest();
        for(LinkedList<String> recordElementPath :requestedRecordElements ){
             jsonDebugDataReadRequest.addRecordElement(recordElementPath);
        }
        udpSender.sendMessage(jsonDebugDataReadRequest.toString().getBytes());
    }

    private void handleJSONDebugDataMessage(String jsonMessage) {
        Interface.setJsonDebugDataMessage(new JSONDebugDataMessage(jsonMessage));
    }


}
