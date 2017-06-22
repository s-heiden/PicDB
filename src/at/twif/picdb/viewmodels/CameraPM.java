package at.twif.picdb.viewmodels;

import BIF.SWE2.interfaces.ISORatings;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;

import java.time.LocalDate;

/**
 * The camera presentation model.
 */
public class CameraPM implements CameraPresentationModel {

    private CameraModel cameraModel;

    public CameraPM(CameraModel cameraModel) {
        this.cameraModel = cameraModel;
    }

    @Override
    public int getID() {
        return cameraModel.getID();
    }

    @Override
    public String getProducer() {
        return cameraModel.getProducer();
    }

    @Override
    public void setProducer(String producer) {
        cameraModel.setProducer(producer);
    }

    @Override
    public String getMake() {
        return cameraModel.getMake();
    }

    @Override
    public void setMake(String make) {
        cameraModel.setMake(make);
    }

    @Override
    public LocalDate getBoughtOn() {
        return cameraModel.getBoughtOn();
    }

    @Override
    public void setBoughtOn(LocalDate date) {
        cameraModel.setBoughtOn(date);
    }

    @Override
    public String getNotes() {
        return cameraModel.getNotes();
    }

    @Override
    public void setNotes(String notes) {
        cameraModel.setNotes(notes);
    }

    @Override
    public int getNumberOfPictures() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isValid() {
        return isValidProducer() && isValidBoughtOn() && isValidMake();
    }

    @Override
    public String getValidationSummary() {
        String validationSummary = "";
        if (!isValidProducer()) {
            validationSummary += "Given producer not valid. ";
        }
        if (!isValidBoughtOn()) {
            validationSummary += "Given date of purchase not valid. ";
        }
        if (!isValidMake()) {
            validationSummary += "Given make not valid. ";
        }
        return validationSummary;
    }

    @Override
    public boolean isValidProducer() {
        return !(cameraModel.getProducer() == null || cameraModel.getProducer().equals(""));
    }

    @Override
    public boolean isValidMake() {
        return !(cameraModel.getMake() == null || cameraModel.getMake().equals(""));
    }

    @Override
    public boolean isValidBoughtOn() {
        return cameraModel.getBoughtOn() == null || !cameraModel.getBoughtOn().isAfter(LocalDate.now());
    }

    @Override
    public double getISOLimitGood() {
        return cameraModel.getISOLimitGood();
    }

    @Override
    public void setISOLimitGood(double isoLimitGood) {
        cameraModel.setISOLimitGood(isoLimitGood);
    }

    @Override
    public double getISOLimitAcceptable() {
        return cameraModel.getISOLimitAcceptable();
    }

    @Override
    public void setISOLimitAcceptable(double isoLimitAcceptable) {
        cameraModel.setISOLimitAcceptable(isoLimitAcceptable);
    }

    @Override
    public ISORatings translateISORating(double iso) {
        if (0 < iso && iso <= getISOLimitGood()) {
            return ISORatings.Good;
        } else if (getISOLimitGood() < iso && iso <= getISOLimitAcceptable()) {
            return ISORatings.Acceptable;
        } else if (getISOLimitAcceptable() < iso) {
            return ISORatings.Noisey;
        } else {
            return ISORatings.NotDefined;
        }
    }
}
