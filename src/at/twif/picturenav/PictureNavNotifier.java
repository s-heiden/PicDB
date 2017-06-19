package at.twif.picturenav;

/**
 * A mediator between a controller and a PictureNav Control.
 */
public class PictureNavNotifier {

    private static PictureNavNotifier instance = new PictureNavNotifier();
    private PictureNavHandler pictureNavHandler;

    private PictureNavNotifier() {
    }

    /**
     * Returns the single static instance of this class.
     */
    public static PictureNavNotifier getInstance() {
        return instance;
    }

    /**
     * Sets the ID of the picture which was last clicked.
     */
    public void clickedId(int lastSelectedId) {
        if (pictureNavHandler != null) {
            pictureNavHandler.handle(lastSelectedId);
        } else {
            throw new IllegalStateException("No notifiable was registered.");
        }
    }

    /**
     * Registers an object that will handle the id of the clicked picture.
     * @param pictureNavHandler 
     */
    public void register(PictureNavHandler pictureNavHandler) {
        this.pictureNavHandler = pictureNavHandler;
    }

}
