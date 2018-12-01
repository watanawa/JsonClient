package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowReadRequest extends Application {
    @Override
    public void start(Stage subStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ReadRequest.fxml"));
        subStage.setTitle("JSONDebugDataReadRequest");
        subStage.setScene(new Scene(root, 500, 200));
        subStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
