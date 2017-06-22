package at.twif.picdb.models;

import BIF.SWE2.interfaces.models.PhotographerModel;

import java.time.LocalDate;

/**
 * Photographer Model
 */
public class Photographer implements PhotographerModel {

    private int id = -1;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private String notes;

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int value) {
        this.id = value;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public LocalDate getBirthDay() {
        return birthDay;
    }

    @Override
    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "PhotographerModelImpl{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", birthDay=" + birthDay
                + ", notes='" + notes + '\''
                + '}';
    }
}
