package at.twif.picdb;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * An image viewing application.
 */
public class App extends Application {
        
    static Logger log = Logger.getLogger(App.class.getName());
    
    /**
     * The entry point of this application.
     */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/MainWindow.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("resources/app.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
