package Gui;


import Help.Interface;
import Json.JSONDebugDataMessage;
import UDP.UDPSender;
import javafx.application.Platform;
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



    private TreeUpdateThread treeUpdateThread;
    public ControllerMain() {
    }

    @FXML
    public void initialize() {
        this.updateButton.setText("Resume");
        treeUpdateThread = new TreeUpdateThread(viewer, timeField);
        treeUpdateThread.start();
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

    public void buttonClicked() {
        updateButton.setText("Test");
        if(treeUpdateThread.isWorking()){
            treeUpdateThread.setWorking(false);
            updateButton.setText("Resume");
        }
        else{
            treeUpdateThread.setWorking(true);
            updateButton.setText("Pause");
        }
    }

}

