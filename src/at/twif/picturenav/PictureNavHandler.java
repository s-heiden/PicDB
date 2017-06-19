package at.twif.picturenav;

/**
 * An interface implemented by classes which can deal with the integer corresponding to the id of the clicked picture.
 */
public interface PictureNavHandler {

    public void handle(int lastClickedId);
}
