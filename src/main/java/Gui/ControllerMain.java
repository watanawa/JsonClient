package Gui;


import Help.Interface;
import Json.JSONDebugDataMessage;
import UDP.UDPSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import org.json.JSONObject;

import javax.swing.text.html.ListView;
import javax.xml.stream.Location;
import java.io.IOException;
import java.util.Iterator;

public class ControllerMain {
    @FXML
    private TreeView viewer = new TreeView();
    @FXML
    private Button updateButton = new Button();
    @FXML
    private TextField timeField = new TextField();

    public ControllerMain() {
    }

    @FXML
    public void initialize() {
        this.updateButton.setText("Update");
    }

    public void createJSONDebugDataReadRequest() {
        Parent root = null;

        try {
            root = (Parent)FXMLLoader.load(getClass().getResource("/Gui/ReadRequest.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage subStage = new Stage();
        subStage.setScene(new Scene(root));
        subStage.setTitle("JSONDebugDataReadRequest");
        subStage.show();
    }

    public void createJSONDebugDataWriteRequest() {
        Parent root = null;

        try {
            root = (Parent)FXMLLoader.load(getClass().getResource("/Gui/WriteRequest.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage subStage = new Stage();
        subStage.setScene(new Scene(root));
        subStage.setTitle("JSONDebugDataWriteRequest");
        subStage.show();
    }

    public void updateTree() {
        JSONDebugDataMessage jsonDebugDataMessage = Interface.getJsonDebugDataMessage();
        TreeItem treeRoot = new TreeItem("JSONDebugDataMessage");
        if (jsonDebugDataMessage != null) {
            JSONObject jsonObject = jsonDebugDataMessage.getJsonDebugDataObject().getJSONObject("JSONDebugDataMessage");
            this.assembleTreeItem(jsonObject, treeRoot);
        }

        this.expandTreeView(treeRoot);
        this.viewer.setRoot(treeRoot);
        this.viewer.refresh();
    }

    public void buttonClicked() {
        this.updateTree();
        if (Interface.getTime() != null) {
            this.timeField.setText(Interface.getTime().toString());
        }

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
}

