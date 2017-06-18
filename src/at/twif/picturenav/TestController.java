package at.twif.picturenav;

import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * A test controller which shows the use of this UI component.
 */
public class TestController implements Initializable, PictureNavHandler {
    private Stage primaryStage;
    
    private PictureNav pictureNav;

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Populate a HashMap containing IDs and their picture paths
        Map<Integer, String> pathsForIDs = new HashMap<>();
        // pathForIDs.put(1, "file:abc.png");
        pathsForIDs.put(1, "http://orf.at/static/images/site/news/20170622/alois_mock_tot_wuerdigungen_1k_pd.4758190.jpg");
        pathsForIDs.put(2, "http://orf.at/static/images/site/news/20170622/link_oest_pfingsten_stau_1k_juh.4758122.jpg");
        pathsForIDs.put(4, "http://orf.at/static/images/site/news/20170622/manila_ueberfall_casino_1k_front_afp.4758237.jpg");
        pathsForIDs.put(7, "http://orf.at/static/images/site/news/20170622/link_sport_fus_oefb_cup_finale_pokal_1k_g.4758226.jpg");
        pathsForIDs.put(10, "http://orf.at/static/images/site/news/20170622/alois_mock_tot_wuerdigungen_1k_pd.4758190.jpg");
        pathsForIDs.put(20, "http://orf.at/static/images/site/news/20170622/link_oest_pfingsten_stau_1k_juh.4758122.jpg");
        pathsForIDs.put(40, "http://orf.at/static/images/site/news/20170622/manila_ueberfall_casino_1k_front_afp.4758237.jpg");
        pathsForIDs.put(70, "http://orf.at/static/images/site/news/20170622/link_sport_fus_oefb_cup_finale_pokal_1k_g.4758226.jpg");
        pathsForIDs.put(11, "http://orf.at/static/images/site/news/20170622/alois_mock_tot_wuerdigungen_1k_pd.4758190.jpg");
        pathsForIDs.put(21, "http://orf.at/static/images/site/news/20170622/link_oest_pfingsten_stau_1k_juh.4758122.jpg");
        pathsForIDs.put(41, "http://orf.at/static/images/site/news/20170622/manila_ueberfall_casino_1k_front_afp.4758237.jpg");
        pathsForIDs.put(71, "http://orf.at/static/images/site/news/20170622/link_sport_fus_oefb_cup_finale_pokal_1k_g.4758226.jpg");
      
        // Create PictureNav
        pictureNav = new PictureNav(pathsForIDs);
        
        // Register to get notified of clicks
        PictureNavNotifier.getInstance().register(this);        
        
        // Add PictureNav to BorderPane
        borderPane.setBottom(pictureNav);             
        
    }
    
    
    @Override
    public void handle(int lastClickedId) {
        // Do something outside of the the PictureNav
        pictureNav.getPathsForIDs().remove(lastClickedId);
        pictureNav.update();
    }
}
