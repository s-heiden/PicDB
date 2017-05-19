package controllers;

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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MainWindowController implements Initializable {

    @FXML private GridPane iptc_gridpane;
    @FXML private GridPane exif_gridpane;
    private Set<TextField> iptcTextfields;
    private Set<TextField> exifTextfields;

    @FXML private BorderPane rootPane;
    private Stage primaryStage;

    /**
     * It is not possible to access fxml elements in controller ctor
     * => use initialize() of Initializable interface
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("test");

        iptcTextfields = getAllTextFieldsFromGridpane(iptc_gridpane);
        exifTextfields = getAllTextFieldsFromGridpane(exif_gridpane);

        // just testing
//        for (TextField t : iptcTextfields) {
//            System.out.println(t.toString());
//        }
//        for (TextField t : exifTextfields) {
//            System.out.println(t.toString());
//        }
    }

    private Set<TextField> getAllTextFieldsFromGridpane(GridPane gridPane) {
        Set<TextField> textFields = new HashSet<>();
        for(Node node:gridPane.getChildren()) {
            if(node instanceof TextField) {
                textFields.add((TextField) node);
            }
        }
        return textFields;
    }

    /**
     * For the node to access the primary stage, getScene() must be called on this node;
     *  getScene() however does not work for Menu Items =>
     *  => call getScene() on some other node (here: root node)
     */
    private Stage getPrimaryStage() {
        if (primaryStage == null) {
            primaryStage = (Stage) rootPane.getScene().getWindow();
        }
        return primaryStage;
    }

    // Menu actions

    @FXML protected void openImagesFolder(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Images Folder");
        chooser.showOpenDialog(rootPane.getScene().getWindow());
    }

    @FXML protected void closeProgram(ActionEvent event) {
        getPrimaryStage().close();
    }

    // saves IPTC and EXIF metadata from texts fields of tab panel into DB
    @FXML protected void saveImageMetadata(ActionEvent event) {
        makeTextfieldsEditable(iptcTextfields, false);
        makeTextfieldsEditable(exifTextfields, false);
        saveMetadataToDatabase();
    }


    @FXML public void showHelp(ActionEvent actionEvent) {
          showDialog("Help",
                  "Lorem ipsum dolor sit amet, eam no minim graeci sanctus, mea dolore periculis at. Meis utamur cu duo, sed amet dicta ei. Vim quas audiam eleifend ne, error audiam posidonium id vix. Harum urbanitas ius ei, et dico habeo mel. No partiendo expetendis vel.\n\n"
                  + "Elitr quidam tibique pri id. Eum id reque accusata rationibus. Eu omnis phaedrum mea, has munere probatus forensibus te, has et causae appetere. Id sit eius theophrastus, scripta facilisi sed et. Sit equidem minimum ad.\n\n"
                  + "Lorem ipsum dolor sit amet, eam no minim graeci sanctus, mea dolore periculis at. Meis utamur cu duo, sed amet dicta ei. Vim quas audiam eleifend ne, error audiam posidonium id vix. Harum urbanitas ius ei, et dico habeo mel. No partiendo expetendis vel.\n\n"
                  + "Elitr quidam tibique pri id. Eum id reque accusata rationibus. Eu omnis phaedrum mea, has munere probatus forensibus te, has et causae appetere. Id sit eius theophrastus, scripta facilisi sed et. Sit equidem minimum ad.\n\n"
          );
    }

    @FXML public void showAbout(ActionEvent actionEvent) {
        showDialog("About",
                "FancyImageManager 1.0 \n" +
                "Created by Max Musterman & Co \n" +
                "2017");
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
            Image image = new Image("file:resources/logo.jpg");
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

    // Edit and Save buttons actions in the right pane (IPTC & EXIF metadata)
    public void editIptc(ActionEvent actionEvent) {
        makeTextfieldsEditable(iptcTextfields, true);
    }

    public void saveIptc(ActionEvent actionEvent) {
        makeTextfieldsEditable(iptcTextfields, false);
        saveMetadataToDatabase();
    }

    public void editExif(ActionEvent actionEvent) {
        makeTextfieldsEditable(exifTextfields, true);
    }

    public void saveExif(ActionEvent actionEvent) {
        makeTextfieldsEditable(exifTextfields, false);
        saveMetadataToDatabase();
    }

    private void makeTextfieldsEditable(Set<TextField> fields, Boolean makeEditable) {
        for (TextField textField : fields) {
            textField.setDisable(!makeEditable);
        }
    }

    private void saveMetadataToDatabase() {
        // TODO: implement
    }


    public void showPhotographers(ActionEvent actionEvent) {

//        PhotographersController pController = new PhotographersController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Photographers.fxml"));
//        loader.setController(pController);
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
