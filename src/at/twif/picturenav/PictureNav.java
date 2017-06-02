package at.twif.picturenav;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * This is the actual PictureNav component which extends ScrollPane.
 * 
 * It is recommended to set the PictureNav as the bottom element of a BorderPane. Only the use of one PictureNav in
 * your app is currently supported.
 */
public class PictureNav extends ScrollPane {

    private HBox hBox;
    private ScrollPane scrollPane;
    private Map<Integer, String> pathsForIDs;

    private static final int THUMBNAIL_HEIGTH = 100;
    private static final int THUMBNAIL_WIDTH = 100;

    /**
     * Returns the data structure which maps IDs on Paths of the displayed pictures.
     * @return 
     */
    public Map<Integer, String> getPathsForIDs() {
        return pathsForIDs;
    }

    /**
     * Sets the data structure which maps IDs on Paths of the displayed pictures.
     */
    public void setPathsForIDs(Map<Integer, String> pathsForIDs) {
        this.pathsForIDs = pathsForIDs;
    }

    /**
     * The only constructor of this class.
     * @param pathsForIDs the data structure which maps IDs on Paths of the displayed pictures
     */
    public PictureNav(Map<Integer, String> pathsForIDs) {
        this.pathsForIDs = pathsForIDs;

        initScrollPane();
        
        initHBox();
        
        // put ScrollPane into content of this element
        this.setContent(scrollPane);

        // put HBox into ScrollPane
        scrollPane.setContent(hBox);

        draw();
    }

    private void draw() {
        // draw individual imageViews
        for (int id : pathsForIDs.keySet()) {
            String path = pathsForIDs.get(id);
            Image image = new Image(path, THUMBNAIL_HEIGTH, THUMBNAIL_WIDTH, false, false);
            ImageView imageView = new ImageView();
            imageView.setId(String.valueOf(id));
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setImage(image);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                // define a handler for mouse clicks
                @Override
                public void handle(MouseEvent event) {
                    int idOfClicked = Integer.valueOf(((ImageView) event.getSource()).getId());
                    PictureNavNotifier.getInstance().clickedId(idOfClicked);
                }
            });
            hBox.getChildren().add(imageView);
        }
    }

    private void initScrollPane() {
        scrollPane = new ScrollPane();
        
        scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        
    }

    private void initHBox() {
        this.hBox = new HBox();
    }

    /**
     * Used to re-draw the pictures and update their IDs.
     */
    public void update() {

        // clear the content of the root ScrollPane
        hBox.getChildren().clear();

        draw();
    }
}
