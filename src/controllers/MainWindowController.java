package controllers;

import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import helpers.Constants;
import helpers.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import viewModels.MainWindowPM;

public class MainWindowController implements Initializable {

    private MainWindowPresentationModel mainWindowPM;
    private Stage primaryStage;

    @FXML
    private GridPane iptcGridpane;
    @FXML
    private GridPane exifGridpane;
    @FXML
    private AnchorPane selectedImagePane;
    @FXML
    private BorderPane rootPane;
    @FXML
    private ScrollPane imageNavigationPane;
    @FXML
    private HBox searchHBox;

    @FXML
    protected void quitAction(ActionEvent event) {
        getPrimaryStage().close();
    }

    @FXML
    protected void saveIptcAction(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    public void showHelpAction(ActionEvent actionEvent) {
        showDialog("Help", "Please visit our help page at:\n\nhttps://github.com/s-heiden/PicDB");
    }

    @FXML
    public void showAboutAction(ActionEvent actionEvent) {
        showDialog("About", "PicDB 1.0\n(c) Team\n2017");
    }

    @FXML
    public void resetButtonAction(ActionEvent actionEvent) {
        TextField searchTF = (TextField) searchHBox.getChildren().get(0);
        searchTF.setText("");
        resetImageNavigationPane();
    }

    @FXML
    public void searchButtonAction(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    public void generateImageReportAction(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    public void generateTagReportAction(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainWindowPM = new MainWindowPM();

        drawSelectedImagePane();
        drawImageNavigationHBox();
        drawCopyrightComboBox();
        populateUIFields();
    }

    private void drawSelectedImagePane() {
        Image selectedImage = new Image(mainWindowPM.getCurrentPicture().getFilePath());
        ImageView selectedImageView = new ImageView();
        selectedImageView.setPreserveRatio(true);
        selectedImageView.setSmooth(true);
        selectedImageView.setCache(true);
        selectedImageView.setImage(selectedImage);
        selectedImageView.fitWidthProperty().bind(selectedImagePane.widthProperty());
        selectedImagePane.getChildren().add(selectedImageView);
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
            Image image = new Image("file:src/resources/logo.jpg");
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

    private void drawImageNavigationHBox() {
        for (PicturePresentationModel p : mainWindowPM.getList().getList()) {
            Image image = new Image(p.getFilePath(), 100, 100, false, false);
            ImageView imageView = new ImageView();
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setImage(image);
            HBox hBox = (HBox) imageNavigationPane.getContent();
            hBox.getChildren().add(imageView);
        }
    }

    private void populateUIFields() {
        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(iptcGridpane, 0, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getHeadline());

        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(iptcGridpane, 1, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getCaption());

        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(iptcGridpane, 2, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getKeywords());

        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(iptcGridpane, 3, 1))
                .setText(mainWindowPM.getCurrentPicture().getIPTC().getByLine());

        ((ComboBox) Helpers.getGridpaneNodeViaRowAndColumn(iptcGridpane, 4, 1)).setValue(mainWindowPM.getCurrentPicture().getIPTC().getCopyrightNotice());

        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(exifGridpane, 0, 1))
                .setText(mainWindowPM.getCurrentPicture().getEXIF().getMake());

        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(exifGridpane, 1, 1))
                .setText(String.valueOf(mainWindowPM.getCurrentPicture().getEXIF().getFNumber()));

        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(exifGridpane, 2, 1))
                .setText(String.valueOf(mainWindowPM.getCurrentPicture().getEXIF().getExposureTime()));

        ((TextField) Helpers.getGridpaneNodeViaRowAndColumn(exifGridpane, 3, 1))
                .setText(String.valueOf(mainWindowPM.getCurrentPicture().getEXIF().getISOValue()));

        ((CheckBox) Helpers.getGridpaneNodeViaRowAndColumn(exifGridpane, 4, 1))
                .setSelected(mainWindowPM.getCurrentPicture().getEXIF().getFlash());

    }

    private void drawCopyrightComboBox() {
        ComboBox comboBox = new ComboBox(
                FXCollections.observableArrayList(
                        mainWindowPM.getCurrentPicture().getIPTC().getCopyrightNotices()));
        iptcGridpane.add(comboBox, 1, 4);
    }

    private void resetImageNavigationPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
