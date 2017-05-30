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
import java.time.format.DateTimeParseException;
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
        saveButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    // ---------------- button actions ----------------------------------

    // NEW
    public void addNewPhotographer() {
        isPhotographerNew = true;
        unselectPhotographers();
        clearInputFields();
        listAnchorPane.setDisable(true);
        makeTextfieldsEditable(true);
        deleteButton.setDisable(true);
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

        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String birthday = birthdayTextField.getText().trim();

        if(lastName.isEmpty()) {
            lastNameTextField.setStyle("-fx-text-box-border: red;");
            System.out.println("You have to enter last name!");
            return;
        } else {
            lastNameTextField.setStyle(null);
            photographerPM.setLastName(lastName);
        }
        if(firstName.isEmpty()) {
            firstName = "";
        }
        photographerPM.setFirstName(firstName);

        try {
            photographerPM.setBirthDay(parseDate(birthday));
        } catch (DateTimeParseException e) {
            birthdayTextField.setStyle("-fx-text-box-border: red;");
            System.out.println("Wrong date format!");
            return;
        }

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
        birthdayTextField.setStyle(null);
        updatePhotographersList();
    }

    // DELETE
    public void deletePhotographer(ActionEvent actionEvent) {
        try {
            photographerList.deleteCurrentPhotographer();
            bl.deletePhotographer(photographerList.getCurrentPhotographerIndex());
            updatePhotographersList();
            clearInputFields();;
        } catch(Exception e) {
            System.out.println("Cannot delete photographer");
            e.printStackTrace();
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

    private LocalDate parseDate(String dateString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    private void updatePhotographersList() {
        listAnchorPane.getChildren().clear();
        double layoutY = 10;
        List<PhotographerPresentationModel> list = photographerList.getList();
        for (PhotographerPresentationModel p : list) {
            String labelText = p.getLastName() + " " + p.getFirstName();
            Label label = new Label(labelText);
            label.setLayoutY(layoutY);
            label.setId(Integer.toString(p.getID()));
            label.setOnMouseClicked((MouseEvent event) -> {
                unselectPhotographers();
                Label l = (Label) event.getSource();
                label.setStyle("-fx-background-color: lightgreen");
                int index = Integer.parseInt(l.getId());
                System.out.println("index: " + index);
                photographerList.setCurrentPhotographerIndex(index);
                showCurrentPhotographerData(index);
                makeTextfieldsEditable(true);
                saveButton.setDisable(false);
                deleteButton.setDisable(false);
            });
            listAnchorPane.getChildren().add(label);
            layoutY += 20;
        }
    }

    private void showCurrentPhotographerData(int id) {
        List<PhotographerPresentationModel> list = photographerList.getList();
        for(PhotographerPresentationModel p : list) {
            if(p.getID()==id) {
                firstNameTextField.setText(p.getFirstName());
                lastNameTextField.setText(p.getLastName());
                birthdayTextField.setText(p.getBirthDay().toString());
                notesTextField.setText(p.getNotes());
                return;
            }
        }
    }

    private void unselectPhotographers() {
        ObservableList<Node> labelList = listAnchorPane.getChildren();
        for(Node node : labelList) {
            node.setStyle("-fx-background-color: none");
        }
    }

    private void clearInputFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        birthdayTextField.clear();
        notesTextField.clear();
    }
}

