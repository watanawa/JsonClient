package Gui;

import Help.Interface;
import Json.JSONDebugDataMessage;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.json.JSONObject;

import javax.xml.soap.Text;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class TreeUpdateThread extends Thread {
    private TreeView viewer;
    private TextField timeField;
    private boolean working = true;

    public TreeUpdateThread(TreeView treeView, TextField timeField){
        this.viewer = treeView;
        this.timeField = timeField;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()){
            //Magie
            Platform.runLater(new Runnable() {
                    @Override
                public void run() {
                    if (working) {
                        //Greifen beide auf JavaFX Elemente zu
                        updateTree();
                        updateTimeField();
                    }
                }
            });
            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateTree() {
        JSONDebugDataMessage jsonDebugDataMessage = Interface.getJsonDebugDataMessage();
        TreeItem treeRoot = new TreeItem("JSONDebugDataMessage");
        if (jsonDebugDataMessage != null) {
            JSONObject jsonObject = jsonDebugDataMessage.getJsonDebugDataObject().getJSONObject("JSONDebugDataMessage");
            this.assembleTreeItem(jsonObject, treeRoot);
        }

        this.expandTreeView(treeRoot);
        viewer.setRoot(treeRoot);
        viewer.refresh();
    }

    private void assembleTreeItem(JSONObject object, TreeItem treeItem) {
        TreeItem subTreeItem;
        for(Iterator iterator = object.keys(); iterator.hasNext(); treeItem.getChildren().add(subTreeItem)) {
            String key = (String)iterator.next();
            subTreeItem = new TreeItem(key);
            if (object.get(key) instanceof JSONObject) {
                this.assembleTreeItem((JSONObject)object.get(key), subTreeItem);
            } else {
                TreeItem valueItem = new TreeItem(object.get(key));
                subTreeItem.getChildren().add(valueItem);
            }
        }

    }
    private void expandTreeView(TreeItem<?> item) {
        if (item != null && !item.isLeaf()) {
            item.setExpanded(true);
            Iterator var2 = item.getChildren().iterator();

            while(var2.hasNext()) {
                TreeItem<?> child = (TreeItem)var2.next();
                this.expandTreeView(child);
            }
        }

    }
    private void updateTimeField(){
        if (Interface.getTime() != null) {
            timeField.setText(Interface.getTime().toString());
        }
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
}