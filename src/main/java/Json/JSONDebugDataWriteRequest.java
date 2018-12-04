package Json;

import Help.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class JSONDebugDataWriteRequest {
    private JSONObject jsonDebugDataWriteRequestObject;
    private JSONArray data;
    public JSONDebugDataWriteRequest(){
        jsonDebugDataWriteRequestObject = new JSONObject();
        data = new JSONArray();
        jsonDebugDataWriteRequestObject.put("JSONDebugDataWriteRequest", data);
    }

    public void addRecordElement(LinkedList<String> list, Object object){
        JSONArray subArray = new JSONArray();
        for(String name: list){
            subArray.put(name);
        }
        subArray.put(object);
        data.put(subArray );
    }

    public String toString(){
        return jsonDebugDataWriteRequestObject.toString();
    }
}
