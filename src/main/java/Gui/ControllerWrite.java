package Gui;

import Help.Element;
import Help.Interface;
import Json.JSONDebugDataWriteRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.LinkedList;

public class ControllerWrite {
    @FXML
    private ListView<String> selectionRecordElementList = new ListView();
    @FXML
    private TextField pathField = new TextField();
    @FXML
    private TextField datatypeField = new TextField();
    @FXML
    private TextField valueField = new TextField();
    private ObservableList<String> observableList;
    private LinkedList<Element> path = new LinkedList();

    public ControllerWrite() {
    }

    @FXML
    public void initialize() {
        ObservableList<String> observableList = FXCollections.observableList(this.elementListToStringList(DsfHandler.getVariables()));
        Collections.sort(observableList);
        this.selectionRecordElementList.setItems(observableList);
        this.pathField.setText("");
    }

    public void listItemClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            LinkedList<Element> choices = null;
            if (this.path.size() == 0) {
                choices = DsfHandler.getVariables();
            } else {
                choices = DsfHandler.getElements(this.path);
            }

            String selection = (String)this.selectionRecordElementList.getSelectionModel().getSelectedItem();

            for(int i = 0; i < choices.size(); ++i) {
                if (selection.equals(((Element)choices.get(i)).getName())) {
                    this.path.add(choices.get(i));
                    this.datatypeField.setText(((Element)choices.get(i)).getSubdataType());
                    break;
                }
            }

            LinkedList<String> list = this.elementListToStringList(DsfHandler.getElements(this.path));
            Collections.sort(list);
            this.observableList = FXCollections.observableList(list);
            this.selectionRecordElementList.setItems(this.observableList);
            this.selectionRecordElementList.refresh();
            String pathText = "";

            for(int i = 0; i < this.path.size(); ++i) {
                if (i == 0) {
                    pathText = ((Element)this.path.get(i)).getName();
                } else {
                    pathText = pathText + " -> " + ((Element)this.path.get(i)).getName();
                }
            }

            this.pathField.setText(pathText);
        }

    }

    public LinkedList<String> elementListToStringList(LinkedList<Element> elementLinkedList) {
        LinkedList<String> stringList = new LinkedList();

        for(int i = 0; i < elementLinkedList.size(); ++i) {
            stringList.add(((Element)elementLinkedList.get(i)).getName());
        }

        return stringList;
    }

    public void sendMessage() {
        JSONDebugDataWriteRequest jsonDebugDataWriteRequest = new JSONDebugDataWriteRequest();
        LinkedList<String> list = this.elementListToStringList(this.path);
        Object value = null;
        if (this.datatypeField.getText().equals("CHARACTER")) {
            value = this.valueField.getCharacters().charAt(0);
        } else if (this.datatypeField.getText().equals("INTEGER")) {
            value = Integer.valueOf(this.valueField.getText());
        } else if (this.datatypeField.getText().equals("POINTER")) {
            value = Integer.valueOf(this.valueField.getText());
        } else if (this.datatypeField.getText().equals("ENUM")) {
            value = String.valueOf(this.valueField.getText());
        } else if (this.datatypeField.getText().equals("FLOAT")) {
            value = Double.valueOf(this.valueField.getText());
        }

        jsonDebugDataWriteRequest.addRecordElement(list, value);
        Interface.getSender().sendMessage(jsonDebugDataWriteRequest.toString().getBytes());
        ((Stage)this.pathField.getScene().getWindow()).close();
    }
}
