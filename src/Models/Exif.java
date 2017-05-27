package Models;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.EXIFModel;

public class Exif implements EXIFModel {

    private String make;
    private double fNumber;
    private double exposureTime;
    private double isoValue;
    private boolean flash;
    private ExposurePrograms exposureProgram = ExposurePrograms.NotDefined;

    public Exif() {
    }

    public Exif(String make, double fNumber, double exposureTime, double isoValue, boolean flash) {
        this.make = make;
        this.fNumber = fNumber;
        this.exposureTime = exposureTime;
        this.isoValue = isoValue;
        this.flash = flash;
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
     * Aperture number
     */
    @Override
    public double getFNumber() {
        return fNumber;
    }

    @Override
    public void setFNumber(double fNumber) {
        this.fNumber = fNumber;
    }

    /**
     * Exposure time
     */
    @Override
    public double getExposureTime() {
        return exposureTime;
    }

    @Override
    public void setExposureTime(double exposureTime) {
        this.exposureTime = exposureTime;
    }

    /**
     * ISO value
     */
    @Override
    public double getISOValue() {
        return isoValue;
    }

    @Override
    public void setISOValue(double isoValue) {
        this.isoValue = isoValue;
    }

    /**
     * Flash yes/no
     */
    @Override
    public boolean getFlash() {
        return flash;
    }

    @Override
    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    /**
     * The exposure program
     */
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
