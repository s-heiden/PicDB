package controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import BL.BL;
import Models.Photographer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import viewModels.PhotographerListPM;
import viewModels.PhotographerPM;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhotographersController  implements Initializable {

    private Set<Node> dataEntryFields = new HashSet<>();
    private static BL bl;
    private static PhotographerListPM photographerList;
    private boolean isPhotographerNew = false;

    @FXML private VBox person_data_vbox;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField birthdayTextField;
    @FXML private TextArea notesTextField;
    @FXML private AnchorPane listAnchorPane;
    @FXML private Button editButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Button newButton;

    /**
     * It is not possible to access fxml elements in controller ctor
     * => use initialize() of Initializable interface
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveDataEntryFieldsIntoSet();
        if (bl == null) {
            bl = BL.getInstance();
        }

        // fetch all photographers from DB into the photographerList
        List<PhotographerModel> photographerModels = null;
        try {
            photographerModels = (List<PhotographerModel>) bl.getPhotographers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (photographerList == null) {
            photographerList = PhotographerListPM.getInstance(photographerModels);
        }

        updatePhotographersList();
        makeTextfieldsEditable(false);

        editButton.setDisable(true);
        saveButton.setDisable(true);
        deleteButton.setDisable(true);
    }


    // ---------------- button actions ----------------------------------

    // NEW
    public void addNewPhotographer() {
        isPhotographerNew = true;
        unselectPhotographers();
        listAnchorPane.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(false);
        makeTextfieldsEditable(true);
    }

    // EDIT
    public void editPhotographer(ActionEvent actionEvent) {
        makeTextfieldsEditable(true);
        newButton.setDisable(true);
        saveButton.setDisable(false);
    }

    // SAVE
    public void savePhotographerData(ActionEvent actionEvent) {
        PhotographerPM photographerPM;
        if(isPhotographerNew) {
            photographerPM = new PhotographerPM(new Photographer());
        } else {
            photographerPM = (PhotographerPM) photographerList.getCurrentPhotographer();
        }

        photographerPM.setFirstName(firstNameTextField.getText());
        photographerPM.setLastName(lastNameTextField.getText());
        photographerPM.setBirthDay(parseDate(birthdayTextField.getText()));
        photographerPM.setNotes(notesTextField.getText());

        if(photographerPM.isValid()) {
            try {
                bl.save(photographerPM.getPhotographerModel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Photographer not valid");
        }

        if(isPhotographerNew) { // save into list
            photographerList.addNewPhotographer(photographerPM);
            isPhotographerNew = false;
            listAnchorPane.setDisable(false);
        }

        updatePhotographersList();
        makeTextfieldsEditable(false);
        saveButton.setDisable(true);
    }

    // DELETE
    public void deletePhotographer(ActionEvent actionEvent) {
        try {
            photographerList.deleteCurrentPhotographer();
            bl.vacuum();
            List<PhotographerPresentationModel> l =  photographerList.getList();
            for (PhotographerPresentationModel p : l) {
                bl.save(((PhotographerPM) p).getPhotographerModel());
            }

            updatePhotographersList();
            editButton.setDisable(true);
            saveButton.setDisable(true);
            newButton.setDisable(false);
        } catch(Exception e) {
            System.out.println("Cannot delete photographer");
        }
    }

    // ---------------- helper methods ----------------------------------

    private void saveDataEntryFieldsIntoSet() {
        for(Node childNode : person_data_vbox.getChildren()) {
            if(childNode instanceof TextArea) { // TextArea Notizen
                dataEntryFields.add(childNode);
            } else if (childNode instanceof GridPane) {
                for(Node grandChildNode : ((GridPane)childNode).getChildren()) {
                    if(grandChildNode instanceof TextField) { // TextFields vorname, nachname, gt
                        dataEntryFields.add(grandChildNode);
                    }
                }
            }
        }
    }

    private void makeTextfieldsEditable(Boolean makeEditable) {
        for (Node textField : dataEntryFields) {
            textField.setDisable(!makeEditable);
        }
    }

    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 2014-12-01
        return LocalDate.parse(dateString, formatter); // 2014-12-01
    }

    private void updatePhotographersList() {
        listAnchorPane.getChildren().clear();
        double layoutY = 10;
        List<PhotographerPresentationModel> list = photographerList.getList();
        int i = 0;
        for (PhotographerPresentationModel p : list) {
            String labelText = p.getLastName() + " " + p.getFirstName();
            Label label = new Label(labelText);
            label.setLayoutY(layoutY);
            label.setId(Integer.toString(i));
            label.setOnMouseClicked((MouseEvent event) -> {
                unselectPhotographers();
                Label l = (Label) event.getSource();
                label.setStyle("-fx-background-color: lightgreen");
                int index = Integer.parseInt(l.getId());
                photographerList.setCurrentPhotographerIndex(index);
                showCurrentPhotographerData(index);
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            });

            listAnchorPane.getChildren().add(label);
            layoutY += 20;
            i++;
        }
    }

    private void showCurrentPhotographerData(int id) {
        List<PhotographerPresentationModel> list = photographerList.getList();
        PhotographerPresentationModel photographerPM = list.get(id);
        firstNameTextField.setText(photographerPM.getFirstName());
        lastNameTextField.setText(photographerPM.getLastName());
        birthdayTextField.setText(photographerPM.getBirthDay().toString());
        notesTextField.setText(photographerPM.getNotes());
    }

    private void unselectPhotographers() {
        ObservableList<Node> labelList = listAnchorPane.getChildren();
        for(Node node : labelList) {
            node.setStyle("-fx-background-color: none");
        }
    }
}

