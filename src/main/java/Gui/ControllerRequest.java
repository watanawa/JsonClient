package Gui;

import dsf.TypeCompilationUnit;
import dsf.TypeDebugSymbols;
import dsf.TypeEquipmentDescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import sun.awt.image.ImageWatched;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ControllerRequest {
    @FXML
    private ListView<String> selectionRecordElementList = new ListView<>();
    @FXML
    private Text pathField = new Text();

    private ObservableList<String> observableList;
    private LinkedList<String> path = new LinkedList<>();


    @FXML
    public void initialize(){
        LinkedList<String> list = DsfHandler.getVariables();
        Collections.sort(list);
        ObservableList<String> observableList = FXCollections.observableList(list);
        selectionRecordElementList.setItems(observableList);
        pathField.setText("");
    }
    public void periodicJsonRequest(){

    }
    public void onceJsonRequest(){

    }
    public void cancelJsonRequest(){

    }
    public void listItemClicked(MouseEvent event){
        if(event.getClickCount() == 2){
            path.add(selectionRecordElementList.getSelectionModel().getSelectedItem());
            LinkedList<String> list = DsfHandler.getElements(path);
            Collections.sort(list);
            observableList = FXCollections.observableList(list);
            selectionRecordElementList.setItems(observableList);
            selectionRecordElementList.refresh();

            String pathText = "";
            for(int i = 0; i< path.size(); i++){
                if(i == 0){
                    pathText = path.get(i);
                }else {
                    pathText = pathText + " -> " + path.get(i);
                }
            }
            pathField.setText(pathText);
        }
    }
}
