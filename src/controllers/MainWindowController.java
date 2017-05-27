package controllers;

import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.layout.AnchorPane;
import viewModels.MainWindowPM;

public class MainWindowController implements Initializable {

    @FXML
    private GridPane iptc_gridpane;
    @FXML
    private GridPane exif_gridpane;
    @FXML
    private AnchorPane selectedImagePane;
    
    private Set<TextField> iptcTextfields;
    private Set<TextField> exifTextfields;
    private MainWindowPresentationModel mainWindowPM;
    
    @FXML
    private BorderPane rootPane;
    private Stage primaryStage;

    /**
     * It is not possible to access fxml elements in controller ctor => use initialize() of Initializable interface
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainWindowPM = new MainWindowPM();
        
        drawSelectedImage();
        
        iptcTextfields = getAllTextFieldsFromGridpane(iptc_gridpane);
        exifTextfields = getAllTextFieldsFromGridpane(exif_gridpane);
        
    }

    private void drawSelectedImage() {        
        Image selectedImage = new Image(mainWindowPM.getCurrentPicture().getFilePath());
        ImageView selectedImageView = new ImageView();
        selectedImageView.setPreserveRatio(true);
        selectedImageView.setSmooth(true);
        selectedImageView.setCache(true);
        selectedImageView.setImage(selectedImage);
        selectedImageView.fitWidthProperty().bind(selectedImagePane.widthProperty());
        selectedImagePane.getChildren().add(selectedImageView);       
    }

    private Set<TextField> getAllTextFieldsFromGridpane(GridPane gridPane) {
        Set<TextField> textFields = new HashSet<>();
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                textFields.add((TextField) node);
            }
        }
        return textFields;
    }

    /**
     * For the node to access the primary stage, getScene() must be called on this node; getScene() however does not
     * work for Menu Items => => call getScene() on some other node (here: root node)
     */
    private Stage getPrimaryStage() {
        if (primaryStage == null) {
            primaryStage = (Stage) rootPane.getScene().getWindow();
        }
        return primaryStage;
    }

    @FXML
    protected void closeProgram(ActionEvent event) {
        getPrimaryStage().close();
    }

    // saves IPTC and EXIF metadata from texts fields of tab panel into DB
    @FXML
    protected void saveImageMetadata(ActionEvent event) {
        saveMetadataToDatabase();
    }

    @FXML
    public void showHelp(ActionEvent actionEvent) {
        showDialog("Help", "Visit our help page at: https://github.com/s-heiden/PicDB\n");
    }

    @FXML
    public void showAbout(ActionEvent actionEvent) {
        showDialog("About",
                "PicDB 1.0\n"
                + "(c) Team\n"
                + "2017");
    }

    private void showDialog(String title, String dialogTextContent) {
        final Stage dialog = new Stage();
//        Modality.APPLICATION_MODAL => no events are processed elsewhere in the program until the user
//           closes the dialog
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(title);
        dialog.initOwner(getPrimaryStage());

        Text text = new Text(dialogTextContent);
        VBox dialogVbox = new VBox(20);

        // adjust the content depending on menu item
        if (title.equals("About")) {
            Image image = new Image("file:src/resources/logo.jpg");
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            dialogVbox.getChildren().add(imageView);
            text.setTextAlignment(TextAlignment.CENTER);
            dialogVbox.getChildren().add(text);
        } else if (title.equals("Help")) {
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefHeight(180);
            text.setWrappingWidth(250);
            scrollPane.setContent(text);
            dialogVbox.getChildren().add(scrollPane);
        }

        Button btnOk = new Button();
        btnOk.setText("OK");
        btnOk.setOnAction(e -> {
            ((Stage) btnOk.getScene().getWindow()).close();
        });
        dialogVbox.getChildren().add(btnOk);
        dialogVbox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogVbox, 300, 250);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void saveIptc(ActionEvent actionEvent) {
        saveMetadataToDatabase();
    }

    public void saveExif(ActionEvent actionEvent) {
        saveMetadataToDatabase();
    }

//    private void makeTextfieldsEditable(Set<TextField> fields, Boolean makeEditable) {
//        for (TextField textField : fields) {
//            textField.setDisable(!makeEditable);
//        }
//    }

    private void saveMetadataToDatabase() {
        // TODO: implement
    }

    public void showPhotographers(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PhotographersView.fxml"));
        System.out.println(loader.getLocation());
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
}
