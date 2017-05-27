package viewModels;

import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.*;
import helpers.Constants;
import java.io.File;

/**
 * Picture Presentation Model.
 */
public class PicturePM implements PicturePresentationModel {

    private final PictureModel pictureModel;
    private CameraPresentationModel cameraPM;
    private PhotographerPresentationModel photographerPM;
    private EXIFPresentationModel exifPM;
    private IPTCPresentationModel iptcPM;

    public PicturePM(PictureModel p) {
        this.pictureModel = p;
        this.cameraPM = p.getCamera() == null ? null : new CameraPM(p.getCamera());
        this.photographerPM = p.getPhotographer() == null ? null : new PhotographerPM(p.getPhotographer());
        this.exifPM = p.getEXIF() == null ? null : new ExifPM(p.getEXIF());
        this.iptcPM = p.getIPTC() == null ? null : new IptcPM(p.getIPTC());
    }

    @Override
    public int getID() {
        return pictureModel.getID();
    }

    @Override
    public String getFileName() {
        return pictureModel.getFileName();
    }

    public void setFileName(String fileName) {
        pictureModel.setFileName(fileName);
    }

    @Override
    public String getFilePath() {
        return new File(Constants.PICTURE_PATH + '/' + getFileName()).toURI().toString();
    }

    /**
     * The line below the Picture.
     *
     * Format: {IPTC.Headline|fileName} (by {Photographer|IPTC.ByLine}).
     */
    @Override
    public String getDisplayName() {
        return ((getIPTC().getHeadline() == null) ? "" : getIPTC().getHeadline())
                + " (by "
                + ((getIPTC().getByLine() == null) ? "" : getIPTC().getHeadline())
                + ")";
    }

    @Override
    public IPTCPresentationModel getIPTC() {
        return iptcPM;
    }

    @Override
    public EXIFPresentationModel getEXIF() {
        return exifPM;
    }

    @Override
    public PhotographerPresentationModel getPhotographer() {
        return photographerPM;
    }

    @Override
    public CameraPresentationModel getCamera() {
        return cameraPM;
    }

    @Override
    public String toString() {
        return "PictureVM{"
                + "pictureModel=" + pictureModel
                + ", cameraPresentationModel=" + cameraPM
                + ", photographerPresentationModel=" + photographerPM
                + ", exifPresentationModel=" + exifPM
                + ", iptcPresentationModel=" + iptcPM
                + '}';
    }
}
