package Json;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JSONDebugDataMessage {
    private JSONObject jsonDebugDataObject;

    public JSONDebugDataMessage(String string){
        jsonDebugDataObject = new JSONObject(string);
    }

    public Object getObject(LinkedList<String> list){
        Object temp;
        temp = jsonDebugDataObject.getJSONObject("JSONDebugDataMessage");
        //Ignore the temporary stuff
        for(int i= 0; i< (list.size()-1); i++){
            if(((JSONObject) temp).has(list.get(i))){
                temp = ((JSONObject)temp).get(list.get(i));
            }
            else{
                return null;
            }
        }
        return temp;
    }

}
