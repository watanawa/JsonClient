package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowWriteRequest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("WriteRequest.fxml"));
        primaryStage.setTitle("JSONDebugDataWriteRequest");
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}