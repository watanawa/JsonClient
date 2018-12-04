package Gui;

import Help.Element;
import Help.Interface;
import Json.JSONDebugDataMessage;
import Json.JSONDebugDataReadRequest;
import dsf.TypeCompilationUnit;
import dsf.TypeDebugSymbols;
import dsf.TypeEquipmentDescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sun.awt.image.ImageWatched;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ControllerRequest {
    @FXML
    private ListView<String> selectionRecordElementList = new ListView<>();
    @FXML
    private TextField pathField = new TextField();

    private ObservableList<String> observableList;

    private LinkedList<Element> path = new LinkedList<>();


    @FXML
    public void initialize(){
        ObservableList<String> observableList = FXCollections.observableList(elementListToStringList(DsfHandler.getVariables()));
        Collections.sort(observableList);
        selectionRecordElementList.setItems(observableList);
        pathField.setText("");
    }
    public void periodicJsonRequest(){
        JSONDebugDataReadRequest jsonDebugDataReadRequest = new JSONDebugDataReadRequest();
        LinkedList<String> list = elementListToStringList(path);
        list.addFirst("Periodic");
        jsonDebugDataReadRequest.addRecordElement(list);
        Interface.getSender().sendMessage(jsonDebugDataReadRequest.toString().getBytes());
        ((Stage)pathField.getScene().getWindow()).close();

    }
    public void onceJsonRequest(){
        JSONDebugDataReadRequest jsonDebugDataReadRequest = new JSONDebugDataReadRequest();
        LinkedList<String> list = elementListToStringList(path);
        list.addFirst("Once");
        jsonDebugDataReadRequest.addRecordElement(list);
        Interface.getSender().sendMessage(jsonDebugDataReadRequest.toString().getBytes());
        ((Stage)pathField.getScene().getWindow()).close();
    }
    public void cancelJsonRequest(){
        JSONDebugDataReadRequest jsonDebugDataReadRequest = new JSONDebugDataReadRequest();
        LinkedList<String> list = elementListToStringList(path);
        list.addFirst("Cancel");
        jsonDebugDataReadRequest.addRecordElement(list);
        Interface.getSender().sendMessage(jsonDebugDataReadRequest.toString().getBytes());
        ((Stage)pathField.getScene().getWindow()).close();

    }
    public void listItemClicked(MouseEvent event){
        if(event.getClickCount() == 2){
            LinkedList<Element> choices = null;
            if(path.size() == 0){
                choices = DsfHandler.getVariables();
            }else{
                choices = DsfHandler.getElements(path);
            }
            String selection = selectionRecordElementList.getSelectionModel().getSelectedItem();
            for(int i= 0; i< choices.size(); i++){
                if(selection.equals(choices.get(i).getName())){
                    path.add(choices.get(i));
                    break;
                }
            }

            LinkedList<String> list = elementListToStringList(DsfHandler.getElements(path));
            Collections.sort(list);
            observableList = FXCollections.observableList(list);
            selectionRecordElementList.setItems(observableList);
            selectionRecordElementList.refresh();

            String pathText = "";
            for(int i = 0; i< path.size(); i++){
                if(i == 0){
                    pathText = path.get(i).getName();
                }else {
                    pathText = pathText + " -> " + path.get(i).getName();
                }
            }
            pathField.setText(pathText);
        }
    }

    public LinkedList<String> elementListToStringList(LinkedList<Element> elementLinkedList){
        LinkedList<String> stringList = new LinkedList<>();
        for(int i=0; i<elementLinkedList.size();i++){
            stringList.add(elementLinkedList.get(i).getName());
        }
        return stringList;
    }
}
