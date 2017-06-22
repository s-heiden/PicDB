package at.twif.picdb.models;

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

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public IPTCModel getIPTC() {
        return iptcModel;
    }

    @Override
    public void setIPTC(IPTCModel iptcModel) {
        this.iptcModel = iptcModel;
    }

    @Override
    public EXIFModel getEXIF() {
        return exifModel;
    }

    @Override
    public void setEXIF(EXIFModel exifModel) {
        this.exifModel = exifModel;
    }

    @Override
    public CameraModel getCamera() {
        return cameraModel;
    }

    @Override
    public void setCamera(CameraModel cameraModel) {
        this.cameraModel = cameraModel;
    }

    @Override
    public PhotographerModel getPhotographer() {
        return photographer;
    }
    
    @Override
    public void setPhotographer(PhotographerModel photographer) {
        this.photographer = photographer;
    }

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
