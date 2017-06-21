package controllers;

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
import viewModels.PhotographerListPM;
import viewModels.PhotographerPM;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * The controller for the photographer view.
 */
public class PhotographersController implements Initializable {

    static Logger log = Logger.getLogger(PhotographersController.class.getName());
    private static BL bl;
    private static PhotographerListPM photographerList;
    private boolean isPhotographerNew = false;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField birthdayTextField;
    @FXML
    private TextArea notesTextField;
    @FXML
    private AnchorPane listAnchorPane;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button newButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bl == null) {
            bl = BL.getInstance();
        }

        // fetch all photographers from DB into the photographerList
        List<PhotographerModel> photographerModels = null;
        try {
            photographerModels = (List<PhotographerModel>) bl.getPhotographers();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
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
    /**
     * Adds a new Photographer.
     */
    public void addNewPhotographer() {
        isPhotographerNew = true;
        unselectPhotographers();
        clearInputFields();
        listAnchorPane.setDisable(true);
        makeTextfieldsEditable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(false);
    }

    /**
     * Saves a photographer with the info given in the respective text fields.
     */
    public void savePhotographerData(ActionEvent actionEvent) {
        // reset GUI
        lastNameTextField.setStyle(null);
        birthdayTextField.setStyle(null);

        //  do we create new photographer or editing existing one?
        PhotographerPM photographerPM;
        if (isPhotographerNew) {
            photographerPM = new PhotographerPM(new Photographer());
        } else {
            photographerPM = (PhotographerPM) photographerList.getCurrentPhotographer();
        }

        if (!saveUnserInputIntoPhotographer(photographerPM)) {
            return;
        }

        if (photographerPM.isValid()) {
            saveIntoDB(photographerPM);
        } else {
            showInputError(photographerPM);
            return;
        }

        // update photographer list and GUI
        if (isPhotographerNew) {
            photographerList.addNewPhotographer(photographerPM);
            isPhotographerNew = false;
            listAnchorPane.setDisable(false);
        }
        updatePhotographersList();
    }

    /**
     * Deletes the photographer with the current photographer index.
     */
    public void deletePhotographer(ActionEvent actionEvent) {
        try {
            photographerList.deleteCurrentPhotographer();
            bl.deletePhotographer(photographerList.getCurrentPhotographerIndex());
            updatePhotographersList();
            clearInputFields();
        } catch(Exception e) {
            log.error("Cannot delete photographer");
            e.printStackTrace();
        }
    }

    // ---------------- SAVE helper methods ----------------------------------
    private void saveIntoDB(PhotographerPM photographerPM) {
        try {
            bl.save(photographerPM.getPhotographerModel());
            log.info("photographer saved to DB");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not save Photographer to DB " + e);
        }
    }

    private void showInputError(PhotographerPM photographerPM) {
        if (!photographerPM.isValidLastName()) {
            lastNameTextField.setStyle("-fx-text-box-border: red;");
        }
        if (!photographerPM.isValidBirthDay()) {
            birthdayTextField.setStyle("-fx-text-box-border: red;");
        }
    }

    /**
     * Returns false if the input birthday date has wrong format
     */
    private boolean saveUnserInputIntoPhotographer(PhotographerPM photographerPM) {
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String birthday = birthdayTextField.getText().trim();
        String notes = notesTextField.getText().trim();

        photographerPM.setFirstName(firstName);
        photographerPM.setLastName(lastName);
        photographerPM.setNotes(notes);

        try {
            photographerPM.setBirthDay(parseDate(birthday)); // validate date format
        } catch (DateTimeParseException e) {
            birthdayTextField.setStyle("-fx-text-box-border: red;");
            log.error("birthday is not valid");
            return false;
        }
        return true;
    }

    // ----------------other helper methods ----------------------------------
    private void makeTextfieldsEditable(Boolean makeEditable) {
        firstNameTextField.setDisable(!makeEditable);
        lastNameTextField.setDisable(!makeEditable);
        birthdayTextField.setDisable(!makeEditable);
        notesTextField.setDisable(!makeEditable);
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
        for (PhotographerPresentationModel p : list) {
            if (p.getID() == id) {
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
        for (Node node : labelList) {
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
