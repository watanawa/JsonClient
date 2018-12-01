package Gui;


import UDP.UDPSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import javax.swing.text.html.ListView;
import java.io.IOException;

public class ControllerMain {
    private UDPSender sender;

    @FXML
    private TreeView viewer = new TreeView();

    @FXML
    public void initialize(){
        Thread t = new Thread(new Runnable() { public void run() {
            updateTree();
        }});
        t.start();
    }

    public void createJSONDebugDataReadRequest(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("ReadRequest.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage subStage = new Stage();
        subStage.setScene(new Scene(root));
        subStage.setTitle("JSONDebugDataReadRequest");
        subStage.show();
    }
    public void createJSONDebugDataWriteRequest(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("WriteRequest.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage subStage = new Stage();
        subStage.setScene(new Scene(root));
        subStage.setTitle("JSONDebugDataReadRequest");
        subStage.show();

    }
    public void updateTree(){

    }
}
