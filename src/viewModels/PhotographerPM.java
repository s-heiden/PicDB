package viewModels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;

import java.time.LocalDate;

public class PhotographerPM implements PhotographerPresentationModel {

    private PhotographerModel photographerModel;

    public PhotographerPM(PhotographerModel photographerModel) {
        this.photographerModel = photographerModel;
    }

    @Override
    public int getID() {
        return photographerModel.getID();
    }

    @Override
    public String getFirstName() {
        return photographerModel.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        photographerModel.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return photographerModel.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        photographerModel.setLastName(lastName);
    }

    @Override
    public LocalDate getBirthDay() {
        return photographerModel.getBirthDay();
    }

    @Override
    public void setBirthDay(LocalDate birthDay) {
        photographerModel.setBirthDay(birthDay);
    }

    @Override
    public String getNotes() {
        return photographerModel.getNotes();
    }

    @Override
    public void setNotes(String notes) {
        photographerModel.setNotes(notes);
    }

    @Override
    public int getNumberOfPictures() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean isValid() {
        return isValidBirthDay() && isValidLastName();
    }

    @Override
    public boolean isValidLastName() {
        return ((getLastName() != null) && !getLastName().isEmpty());
    }

    @Override
    public boolean isValidBirthDay() {
        return getBirthDay() == null || getBirthDay().isBefore(LocalDate.now());
    }

    @Override
    public String getValidationSummary() {
        String validationSummary = "";
        if (!isValidLastName()) {
            validationSummary += "Last name not given. ";
        }
        if (!isValidBirthDay()) {
            validationSummary += "Birthday not valid. ";
        }
        return validationSummary;
    }
}
