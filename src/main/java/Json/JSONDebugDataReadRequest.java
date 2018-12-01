package Json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JSONDebugDataReadRequest {
    private JSONObject jsonDebugDataReadRequestObject;
    private JSONArray data;
    public JSONDebugDataReadRequest(){
        jsonDebugDataReadRequestObject = new JSONObject();
        data = new JSONArray();
        jsonDebugDataReadRequestObject.put("JSONDebugDataReadRequest", data);
    }

    public void addRecordElement(LinkedList<String> list){
        JSONArray subArray = new JSONArray();
        for(String name: list){
            subArray.put(name);
        }
        data.put(subArray );
    }

    public String toString(){
        return jsonDebugDataReadRequestObject.toString();
    }
}
