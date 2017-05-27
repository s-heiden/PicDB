package BIF.SWE2.interfaces.models;

public interface PictureModel {

    /**
     * Database primary key
     */
    int getID();

    void setID(int value);

    /**
     * Filename of the picture, without path.
     */
    String getFileName();

    void setFileName(String value);

    /**
     * IPTC information
     */
    IPTCModel getIPTC();

    void setIPTC(IPTCModel value);

    /**
     * EXIF information
     */
    EXIFModel getEXIF();

    void setEXIF(EXIFModel value);

    /**
     * The camera (optional)
     */
    CameraModel getCamera();

    void setCamera(CameraModel value);

    /**
     * The photographer (optional)
     */
    PhotographerModel getPhotographer();

    void setPhotographer(PhotographerModel value);
}
