package Json;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JSONDebugDataMessage {
    private JSONObject jsonDebugDataObject;

    public JSONDebugDataMessage(String string){
        jsonDebugDataObject = new JSONObject(string);
    }


    public JSONObject getJsonDebugDataObject() {
        return jsonDebugDataObject;
    }

    public void setJsonDebugDataObject(JSONObject jsonDebugDataObject) {
        this.jsonDebugDataObject = jsonDebugDataObject;
    }
}
