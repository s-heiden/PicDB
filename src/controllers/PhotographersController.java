package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class PhotographersController  implements Initializable {

    @FXML private VBox person_data_vbox;
    private Set<Node> dataTextFields = new HashSet<>();

    /**
     * It is not possible to access fxml elements in controller ctor
     * => use initialize() of Initializable interface
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Node childNode : person_data_vbox.getChildren()) {
            if(childNode instanceof TextArea) {
                dataTextFields.add(childNode);
            } else if (childNode instanceof GridPane) {
                for(Node grandChildNode : ((GridPane)childNode).getChildren()) {
                    if(grandChildNode instanceof TextField) {
                       dataTextFields.add(grandChildNode);
                    }
                }
            }
        }
    }

    public void addNewPhotographer() {
        // TODO: implement
    }

    public void editPhotographer(ActionEvent actionEvent) {
        makeTextfieldsEditable(true);
    }

    public void savePhotographerData(ActionEvent actionEvent) {
        makeTextfieldsEditable(false);
        // TODO: save data to DB
    }

    public void deletePhotographer(ActionEvent actionEvent) {
        // TODO: implement
    }

    private void makeTextfieldsEditable(Boolean makeEditable) {
        for (Node textField : dataTextFields) {
            textField.setDisable(!makeEditable);
        }
    }
}

