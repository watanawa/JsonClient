package UDP;

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
        JSONDebugDataMessage jsonDebugDataMessage =  new JSONDebugDataMessage(jsonMessage);
        for(LinkedList<String> path : requestedRecordElements){
            System.out.println();
            //WE DONT PRINT THE PERIODIC/ONCE ARGUMENT
            for(int i = 1; i < path.size(); i++){
                System.out.println(path.get(i));
            }
            Object data = jsonDebugDataMessage.getObject(path);
            outputObject(data, 0);
        }
    }

    private void outputObject(Object object, int level){
        if(object instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) object;
            Iterator it = jsonObject.keys();
            while (it.hasNext()){
                String key = (String) it.next();
                for(int i = 0;i < level; i++){
                    System.out.print("\t");
                }
                System.out.print(key);
                if(jsonObject.has(key)){
                    outputObject(jsonObject.get(key), (level+1));
                }
            }
        }
        else if(object instanceof Integer){
            int integer = (Integer) object;
            System.out.print(" : ");
            System.out.println(integer);
        }
        else if(object instanceof String){
            String string = (String) object;
            System.out.print(" : ");
            System.out.println(string);
        }
        else if(object instanceof Long){
            Long longValue = (Long) object;
            System.out.print(" : ");
            System.out.println(longValue);
        }
        else if(object instanceof Float){
            Float floatValue = (Float) object;
            System.out.print(" : ");
            System.out.println(floatValue);
        }
        else if(object instanceof Double){
            Double doubleValue = (Double) object;
            System.out.print(" : ");
            System.out.println(doubleValue);
        }
        else if(object instanceof Character){
            char charValue = (Character) object;
            System.out.print(" : ");
            System.out.println(charValue);
        }
        else if(object instanceof Short){
            short shortvalue = (Short)object;
            System.out.print(" : ");
            System.out.println(shortvalue);
        }
    }

}
