package controllers;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.IPTCPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import BL.BL;
import at.twif.picturenav.PictureNav;
import at.twif.picturenav.PictureNavHandler;
import at.twif.picturenav.PictureNavNotifier;
import helpers.Constants;
import helpers.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import viewModels.MainWindowPM;
import viewModels.PicturePM;

/**
 * The controller for the main window.
 */
public class MainWindowController implements Initializable, PictureNavHandler {

    private MainWindowPresentationModel mainWindowPM;
    private Stage primaryStage;

    @FXML
    private GridPane iptcGridpane;
    @FXML
    private GridPane exifGridpane;
    @FXML
    private AnchorPane selectedImagePane;
    @FXML
    private AnchorPane navAnchorPane;
    @FXML
    private BorderPane rootPane;
    @FXML
    private SplitPane splitPane;
    @FXML
    private HBox searchHBox;

    private PictureNav pictureNav;

    @FXML
    protected void quitAction(ActionEvent event) {
        getPrimaryStage().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainWindowPM = new MainWindowPM();

        // Populate a HashMap containing IDs and their picture paths
        Map<Integer, String> pathsForIDs = new HashMap<>();
        for (PicturePresentationModel picturePM : mainWindowPM.getList().getList()) {
            pathsForIDs.put(picturePM.getID(), picturePM.getFilePath());
        }

        // Create PictureNav
        pictureNav = new PictureNav(pathsForIDs);

        // Register to get notified of clicks
        PictureNavNotifier.getInstance().register(this);

        // Add PictureNav to BorderPane
        // rootPane.setBottom(pictureNav);
        pictureNav.setMaxHeight(125);
        splitPane.getItems().add(pictureNav);

        drawSelectedPicturePane();
        drawCopyrightComboBox();
        drawExposureProgramComboBox();
        fillSelectedPictureControls();
    }

    // TODO: (nice to have) refactor and simplify
    @FXML
    protected void saveIptcAction(ActionEvent event) {
        PictureModel newPictureModel = null;
        IPTCPresentationModel iptcPM = mainWindowPM.getCurrentPicture().getIPTC();
        try {
            // a new instance is generated from the database serving as a prototype for the saving action
            newPictureModel = BL.getInstance().getPicture(mainWindowPM.getCurrentPicture().getID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (newPictureModel != null) {
            IPTCModel iptc = newPictureModel.getIPTC();
            String headlineString = ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 0, 1)).getText();
            String captionString = ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 1, 1)).getText();
            String keywordsString = ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 2, 1)).getText();
            String byLineString = ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 3, 1)).getText();
            String copyrightString = (String) ((ComboBox) Helpers.getGridpaneNodeAt(iptcGridpane, 4, 1)).getSelectionModel().getSelectedItem();

            // the prototype we created earlier is updated
            if (iptc != null) {
                iptc.setHeadline(headlineString);
                iptc.setCaption(captionString);
                iptc.setKeywords(keywordsString);
                iptc.setByLine(byLineString);
                iptc.setCopyrightNotice(copyrightString);
            }

            // updating the presentation model
            if (iptcPM != null) {
                iptcPM.setHeadline(headlineString);
                iptcPM.setCaption(captionString);
                iptcPM.setKeywords(keywordsString);
                iptcPM.setByLine(byLineString);
                iptcPM.setCopyrightNotice(copyrightString);
            }
        }
        try {
            // save the updated prototype to the database
            BL.getInstance().save(newPictureModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a help dialog.
     */
    @FXML
    public void showHelpAction(ActionEvent actionEvent) {
        showDialog("Help", "Please visit our help page at:\n\nhttps://github.com/s-heiden/PicDB");
    }

    /**
     * Shows an about dialog.
     */
    @FXML
    public void showAboutAction(ActionEvent actionEvent) {
        showDialog("About", "PicDB 1.0\n(c) Team\n2017");
    }

    /**
     * Resets the search text field.
     */
    @FXML
    public void resetButtonAction(ActionEvent actionEvent) {
        TextField searchTF = (TextField) searchHBox.getChildren().get(0);
        searchTF.setText("");

        Map<Integer, String> pathsForIDs = new HashMap<>();
        for (PicturePresentationModel picturePM : mainWindowPM.getList().getList()) {
            pathsForIDs.put(picturePM.getID(), picturePM.getFilePath());
        }
        pictureNav.setPathsForIDs(pathsForIDs);
        pictureNav.update();
    }

    /**
     * Searches for the strings given in the search text field.
     */
    @FXML
    public void searchButtonAction(ActionEvent actionEvent) {
        String searchString = ((TextField) searchHBox.getChildren().get(0)).getText();
        mainWindowPM.getSearch().setSearchText(searchString);
        Collection<PicturePresentationModel> picturePMs = new ArrayList<>();
        try {
            BL.getInstance().getPictures(searchString, null, null, null).forEach((p) -> {
                picturePMs.add(new PicturePM(p));
            });

            Map<Integer, String> searchPathsAndIDs = new HashMap<>();
            for (PicturePresentationModel picturePM : picturePMs) {
                searchPathsAndIDs.put(picturePM.getID(), picturePM.getFilePath());
            }
            pictureNav.setPathsForIDs(searchPathsAndIDs);
            pictureNav.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a report for all images.
     */
    @FXML
    public void generateImageReportAction(ActionEvent actionEvent) {
        // TODO:
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Generates a report for all tags.
     */
    @FXML
    public void generateTagReportAction(ActionEvent actionEvent) {
        // TODO:
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Shows the photographer view.
     */
    @FXML
    public void showPhotographersAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PhotographersView.fxml"));
        try {
            Pane mainPane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Photographers");
            stage.setScene(new Scene(mainPane, 560, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawSelectedPicturePane() {
        selectedImagePane.getChildren().clear();
        Image selectedPicture = new Image(mainWindowPM.getCurrentPicture().getFilePath());
        ImageView selectedImageView = new ImageView();
        selectedImageView.setPreserveRatio(true);
        selectedImageView.setSmooth(true);
        selectedImageView.setCache(true);
        selectedImageView.setImage(selectedPicture);
        selectedImageView.fitWidthProperty().bind(selectedImagePane.widthProperty());
        selectedImagePane.getChildren().add(selectedImageView);
    }

    private void fillSelectedPictureControls() {
        ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 0, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getHeadline());

        ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 1, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getCaption());

        ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 2, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getKeywords());

        ((TextField) Helpers.getGridpaneNodeAt(iptcGridpane, 3, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getByLine());

        ((ComboBox) Helpers.getGridpaneNodeAt(iptcGridpane, 4, 1))
                .setValue(mainWindowPM.getCurrentPicture().getIPTC().getCopyrightNotice());

        ((TextField) Helpers.getGridpaneNodeAt(exifGridpane, 0, 1))
                .setText(mainWindowPM.getCurrentPicture().getEXIF().getMake());

        ((TextField) Helpers.getGridpaneNodeAt(exifGridpane, 1, 1))
                .setText(String.valueOf(mainWindowPM.getCurrentPicture().getEXIF().getFNumber()));

        ((TextField) Helpers.getGridpaneNodeAt(exifGridpane, 2, 1))
                .setText(String.valueOf(mainWindowPM.getCurrentPicture().getEXIF().getExposureTime()));

        ((TextField) Helpers.getGridpaneNodeAt(exifGridpane, 3, 1))
                .setText(String.valueOf(mainWindowPM.getCurrentPicture().getEXIF().getISOValue()));

        ((CheckBox) Helpers.getGridpaneNodeAt(exifGridpane, 4, 1))
                .setSelected(mainWindowPM.getCurrentPicture().getEXIF().getFlash());

        ((ComboBox) Helpers.getGridpaneNodeAt(exifGridpane, 5, 1))
                .setValue(mainWindowPM.getCurrentPicture().getEXIF().getExposureProgram());
    }

    private void drawCopyrightComboBox() {
        ComboBox comboBox = new ComboBox(
                FXCollections.observableArrayList(
                        mainWindowPM.getCurrentPicture().getIPTC().getCopyrightNotices()));
        comboBox.setStyle("-fx-font: 11px \"System\";");
        iptcGridpane.add(comboBox, 1, 4);
    }

    private void drawExposureProgramComboBox() {
        ComboBox comboBox = new ComboBox(
                FXCollections.observableArrayList(ExposurePrograms.getNamesAsArray()));
        comboBox.setStyle("-fx-font: 11px \"System\";");
        comboBox.setDisable(true);
        exifGridpane.add(comboBox, 1, 5);
    }

    private Stage getPrimaryStage() {
        if (primaryStage == null) {
            primaryStage = (Stage) rootPane.getScene().getWindow();
        }
        return primaryStage;
    }

    private void showDialog(String title, String dialogTextContent) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL); // no events are processed until the user closes the dialog
        dialog.setTitle(title);
        dialog.initOwner(getPrimaryStage());

        Text text = new Text(dialogTextContent);
        text.setTextAlignment(TextAlignment.CENTER);
        VBox dialogVBox = new VBox(20);

        if (title.equals("About")) {
            Image image = new Image(Constants.LOGO_PATH);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            dialogVBox.getChildren().add(imageView);
        }

        dialogVBox.getChildren().add(text);

        Button okayButton = new Button();
        okayButton.setText("OK");
        okayButton.setOnAction(e -> {
            ((Stage) okayButton.getScene().getWindow()).close();
        });
        dialogVBox.getChildren().add(okayButton);
        dialogVBox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogVBox, 300, 250);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @Override
    public void handle(int clickedID) {
        mainWindowPM.getList().setCurrentIndex(clickedID);
        drawSelectedPicturePane();
        fillSelectedPictureControls();
    }
}
