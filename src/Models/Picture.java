package Models;

import BIF.SWE2.interfaces.models.*;

/**
 * Picture Model.
 */
public class Picture implements PictureModel {

    private int id = -1;
    private String fileName;
    private IPTCModel iptcModel;
    private EXIFModel exifModel;
    private CameraModel cameraModel;
    private PhotographerModel photographer;

    /**
     * Sets the database primary key
     */
    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Filename of the picture, without path.
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the IPTC information.
     */
    @Override
    public IPTCModel getIPTC() {
        return iptcModel;
    }

    /**
     * Sets the IPTC information.
     */
    @Override
    public void setIPTC(IPTCModel iptcModel) {
        this.iptcModel = iptcModel;
    }

    /**
     * EXIF information
     */
    @Override
    public EXIFModel getEXIF() {
        return exifModel;
    }

    @Override
    public void setEXIF(EXIFModel exifModel) {
        this.exifModel = exifModel;
    }

    /**
     * Returns the camera if one is connected to this PictureModel
     *
     * @return the camera connected to this PictureModel or null
     */
    @Override
    public CameraModel getCamera() {
        return cameraModel;
    }

    /**
     * Connects a CameraModel to this PictureModel instance.
     *
     * @param cameraModel the cameraModel that should be connected to this PictureModel
     */
    @Override
    public void setCamera(CameraModel cameraModel) {
        this.cameraModel = cameraModel;
    }

    /**
     * Returns the PhotographerModel connected to this picture or null
     *
     * @return the PhotographerModel connected to this picture or null
     */
    public PhotographerModel getPhotographer() {
        return photographer;
    }

    /**
     * Sets the PhotographerModel connected to this picture
     *
     * @param photographer the PhotographerModel connected to this picture
     */
    public void setPhotographer(PhotographerModel photographer) {
        this.photographer = photographer;
    }

    /**
     * Returns a String representation of this instance.
     *
     * @return a String representation of this instance.
     */
    @Override
    public String toString() {
        return "PictureModelImpl{"
                + "id=" + id
                + ", fileName='" + fileName + '\''
                + ", iptcModel=" + iptcModel
                + ", exifModel=" + exifModel
                + ", cameraModel=" + cameraModel
                + ", photographer=" + photographer
                + '}';
    }
}
