package Models;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.EXIFModel;

/**
 * Exif information.
 */
public class Exif implements EXIFModel {

    private String make;
    private double fNumber;
    private double exposureTime;
    private double isoValue;
    private boolean flash;
    private ExposurePrograms exposureProgram;

    /**
     * The default constructor.
     */
    public Exif() {
    }

    /**
     * A custom constructor that sets all fields.
     */
    public Exif(String make, double fNumber, double exposureTime, double isoValue, boolean flash, ExposurePrograms exposureProgram) {
        this.make = make;
        this.fNumber = fNumber;
        this.exposureTime = exposureTime;
        this.isoValue = isoValue;
        this.flash = flash;
        this.exposureProgram = exposureProgram;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public double getFNumber() {
        return fNumber;
    }

    @Override
    public void setFNumber(double fNumber) {
        this.fNumber = fNumber;
    }

    @Override
    public double getExposureTime() {
        return exposureTime;
    }

    @Override
    public void setExposureTime(double exposureTime) {
        this.exposureTime = exposureTime;
    }

    @Override
    public double getISOValue() {
        return isoValue;
    }

    @Override
    public void setISOValue(double isoValue) {
        this.isoValue = isoValue;
    }

    @Override
    public boolean getFlash() {
        return flash;
    }

    @Override
    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    @Override
    public ExposurePrograms getExposureProgram() {
        return exposureProgram;
    }

    @Override
    public void setExposureProgram(ExposurePrograms exposureProgram) {
        this.exposureProgram = exposureProgram;
    }

    @Override
    public String toString() {
        return "EXIFModelImpl{"
                + "make='" + make + '\''
                + ", fNumber=" + fNumber
                + ", exposureTime=" + exposureTime
                + ", isoValue=" + isoValue
                + ", flash=" + flash
                + ", exposureProgram=" + (exposureProgram != null ? exposureProgram : "")
                + '}';
    }
}
