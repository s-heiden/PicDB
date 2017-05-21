package Models;

import BIF.SWE2.interfaces.models.CameraModel;

import java.time.LocalDate;

/**
 * Camera Model.
 */
public class Camera implements CameraModel {
    private int id = -1;
    private String producer;
    private String make;
    private LocalDate boughtOn;
    private String notes;
    private double isoLimitGood;
    private double isoLimitAcceptable;

    public Camera(){
    }    
    
    /**
     * Constructor which sets all member variables.
     * @param id
     * @param producer
     * @param make
     * @param boughtOn
     * @param notes
     * @param isoLimitGood
     * @param isoLimitAcceptable 
     */
    public Camera(int id, String producer, String make, LocalDate boughtOn, String notes, double isoLimitGood, double isoLimitAcceptable) {
        this.id = id;
        this.producer = producer;
        this.make = make;
        this.boughtOn = boughtOn;
        this.notes = notes;
        this.isoLimitGood = isoLimitGood;
        this.isoLimitAcceptable = isoLimitAcceptable;
    }
    
    /**
     * Returns database primary key
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
     * Name of the producer
     */
    @Override
    public String getProducer() {
        return producer;
    }

    @Override
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * Name of camera
     */
    @Override
    public String getMake() {
        return make;
    }

    @Override
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Optional: date, when the camera was bought
     */
    @Override
    public LocalDate getBoughtOn() {
        return boughtOn;
    }

    @Override
    public void setBoughtOn(LocalDate boughtOn) {
        this.boughtOn = boughtOn;
    }

    /**
     * Notes
     */
    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Max ISO Value for good results. 0 means "not defined"
     */
    @Override
    public double getISOLimitGood() {
        return isoLimitGood;
    }

    @Override
    public void setISOLimitGood(double isoLimitGood) {
        this.isoLimitGood = isoLimitGood;
    }

    /**
     * Max ISO Value for acceptable results. 0 means "not defined"
     */
    @Override
    public double getISOLimitAcceptable() {
        return isoLimitAcceptable;
    }

    @Override
    public void setISOLimitAcceptable(double isoLimitAcceptable) {
        this.isoLimitAcceptable = isoLimitAcceptable;
    }

    @Override
    public String toString() {
        return "CameraModelImpl{" +
                "id=" + id +
                ", producer='" + producer + '\'' +
                ", make='" + make + '\'' +
                ", purchaseDate=" + boughtOn +
                ", notes='" + notes + '\'' +
                ", isoLimitGood=" + isoLimitGood +
                ", isoLimitAcceptable=" + isoLimitAcceptable +
                '}';
    }
}
