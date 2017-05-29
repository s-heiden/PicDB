package controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BL.BL;
import Models.Photographer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import viewModels.PhotographerPM;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class PhotographersController  implements Initializable {

    private Set<Node> dataEntryFields = new HashSet<>();
    private static BusinessLayer bl;

    @FXML private VBox person_data_vbox;
    @FXML private TextField vorname_text;
    @FXML private TextField nachname_text;
    @FXML private TextField geburtstag_text;
    @FXML private TextArea notizen;


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
    }


    // ---------------- button actions ----------------------------------

    public void addNewPhotographer() {

    }

    public void editPhotographer(ActionEvent actionEvent) {
        makeTextfieldsEditable(true);
    }

    public void savePhotographerData(ActionEvent actionEvent) {
        makeTextfieldsEditable(false);
        String firstName = vorname_text.getText();
        String lastName = nachname_text.getText();
        String birthday = geburtstag_text.getText();
        String notes = notizen.getText();
        System.out.println(firstName);

        Photographer photographerM = new Photographer();
        PhotographerPM photographerPM = new PhotographerPM(photographerM);
        photographerPM.setFirstName(firstName);
        photographerPM.setLastName(lastName);
        photographerPM.setBirthDay(parseDate(birthday));
        photographerPM.setNotes(notes);

        if(photographerPM.isValid()) {
            try {
                bl.save(photographerM);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Photographer not valid");
        }
    }

    public void deletePhotographer(ActionEvent actionEvent) {
        // TODO: implement
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy"); // 01.12.2014
        return LocalDate.parse(dateString, formatter); // 2014-12-01
    }
}

