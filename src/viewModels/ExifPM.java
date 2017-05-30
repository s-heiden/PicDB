package viewModels;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.ISORatings;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.EXIFPresentationModel;
import helpers.Constants;

public class ExifPM implements EXIFPresentationModel {

    private EXIFModel exifModel;
    private CameraPresentationModel cameraPresentationModel;

    public ExifPM(EXIFModel exifModel) {
        this.exifModel = exifModel;
    }

    @Override
    public String getMake() {
        return exifModel.getMake();
    }

    @Override
    public double getFNumber() {
        return exifModel.getFNumber();
    }

    @Override
    public double getExposureTime() {
        return exifModel.getExposureTime();
    }

    @Override
    public double getISOValue() {
        return exifModel.getISOValue();
    }

    @Override
    public boolean getFlash() {
        return exifModel.getFlash();
    }

    @Override
    public String getExposureProgram() {
        return exifModel.getExposureProgram().toString();
    }

    private ExposurePrograms getExposureProgramObject() {
        return exifModel.getExposureProgram();
    }

    @Override
    public String getExposureProgramResource() {
        if (cameraPresentationModel != null) {
            if (getExposureProgramObject() == ExposurePrograms.ActionProgram) {
                return Constants.RESOURCE_PATH + Constants.EP_ACTION_PROGRAM_PATH;
            } else if (getExposureProgramObject() == ExposurePrograms.AperturePriority) {
                return Constants.RESOURCE_PATH + Constants.EP_APERTURE_PRIORITY;
            } else if (getExposureProgramObject() == ExposurePrograms.CreativeProgram) {
                return Constants.RESOURCE_PATH + Constants.EP_CREATIVE_PROGRAM_PATH;
            } else if (getExposureProgramObject() == ExposurePrograms.LandscapeMode) {
                return Constants.RESOURCE_PATH + Constants.EP_LANDSCAPE_MODE_PATH;
            } else if (getExposureProgramObject() == ExposurePrograms.Manual) {
                return Constants.RESOURCE_PATH + Constants.EP_MANUAL_PATH;
            } else if (getExposureProgramObject() == ExposurePrograms.Normal) {
                return Constants.RESOURCE_PATH + Constants.EP_NORMAL_PATH;
            } else if (getExposureProgramObject() == ExposurePrograms.PortraitMode) {
                return Constants.RESOURCE_PATH + Constants.EP_PORTRAIT_MODE_PATH;
            } else if (getExposureProgramObject() == ExposurePrograms.ShutterPriority) {
                return Constants.RESOURCE_PATH + Constants.EP_SHUTTER_PRIORITY_PATH;
            } else {
                return Constants.RESOURCE_PATH + Constants.EP_NOT_DEFINED_PATH;
            }
        } else {
            return Constants.RESOURCE_PATH + Constants.EP_NOT_DEFINED_PATH;
        }
    }

    @Override
    public CameraPresentationModel getCamera() {
        return cameraPresentationModel;
    }

    @Override
    public void setCamera(CameraPresentationModel cameraPresentationModel) {
        this.cameraPresentationModel = cameraPresentationModel;
    }

    @Override
    public ISORatings getISORating() {
        if (cameraPresentationModel == null) {
            return ISORatings.NotDefined;
        } else {
            return cameraPresentationModel.translateISORating(getISOValue());
        }
    }

    @Override
    public String getISORatingResource() {
        if (cameraPresentationModel != null) {
            if (getISORating() == ISORatings.Acceptable) {
                return Constants.RESOURCE_PATH + Constants.ISO_ACCEPTABLE_PATH;
            } else if (getISORating() == ISORatings.Good) {
                return Constants.RESOURCE_PATH + Constants.ISO_GOOD_PATH;
            } else if (getISORating() == ISORatings.Noisey) {
                return Constants.RESOURCE_PATH + Constants.ISO_NOISEY_PATH;
            } else {
                return Constants.RESOURCE_PATH + Constants.ISO_NOT_DEF_PATH;
            }
        } else {
            return Constants.RESOURCE_PATH + Constants.ISO_NO_CAMERA_PATH;
        }
    }
}
