package BL;

import BIF.SWE2.interfaces.*;
import BIF.SWE2.interfaces.models.*;
import DAL.DBTable;
import DAL.SQLiteDAL;
import helpers.Helpers;
import static helpers.Helpers.deleteFromPicturePath;
import static helpers.Helpers.existsInPicturePath;
import static helpers.Helpers.getRandomString;
import Models.*;
import helpers.Constants;
import static helpers.Constants.PICTURE_PATH;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class BL implements BusinessLayer {

    private static BL blInstance;
    private static SQLiteDAL dal;

    public static BL getInstance() {
        if (blInstance == null) {
            blInstance = new BL();
        }
        return blInstance;
    }

    private BL() {
        if (dal == null) {
            dal = SQLiteDAL.getInstance();
        }
        sync();
    }

    @Override
    public Collection<PictureModel> getPictures() {
        try {
            return dal.getPictures(
                    "",
                    null,
                    null,
                    null
            );
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<PictureModel> getPictures(
            String namePart,
            PhotographerModel photographerParts,
            IPTCModel iptcParts,
            EXIFModel exifParts
    ) throws Exception {
        return dal.getPictures(namePart, photographerParts, iptcParts, exifParts);
    }

    @Override
    public PictureModel getPicture(int ID) {
        try {
            return dal.getPicture(ID);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public void save(PictureModel picture) throws Exception {
        if (picture.getID() < 0) { // checks if no valid ID has been set yet
            picture.setID(((SQLiteDAL) dal).nextIdFor(DBTable.PICTURES));
        }
        dal.save(picture);
    }

    @Override
    public void deletePicture(int ID) {
        String filename = getPicture(ID).getFileName();
        deleteFromPicturePath(filename);
        try {
            dal.deletePicture(ID);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Override
    public void sync() {
        File pictureDirectory = new File(PICTURE_PATH);
        if (pictureDirectory.isDirectory()) {
            List<String> dirListing = Arrays.asList(pictureDirectory.list());
            List<String> filenamesInModel = new ArrayList<>();

            // if a picture does not exist in the directory but in the model + db
            for (PictureModel picture : getPictures()) {
                filenamesInModel.add(picture.getFileName());
                if (!dirListing.contains(picture.getFileName())) {
                    deletePicture(picture.getID());
                }
            }
            // add a picture to the model/database if it exists in the directory but not in the model/db
            for (String picDirFilename : dirListing) {
                // TODO: check the file suffix for a image file
                if (!filenamesInModel.contains(picDirFilename)) {
                    PictureModel picture = new Picture();
                    picture.setFileName(picDirFilename);
                    try {
                        picture.setEXIF(extractEXIF(picture.getFileName()));
                        picture.setIPTC(extractIPTC(picture.getFileName()));
                        save(picture);
                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public Collection<PhotographerModel> getPhotographers() throws Exception {
        return dal.getPhotographers();
    }

    @Override
    public PhotographerModel getPhotographer(int ID) throws Exception {
        return dal.getPhotographer(ID);
    }

    @Override
    public void save(PhotographerModel photographer) throws Exception {
        if (photographer.getID() < 0) {
            photographer.setID(((SQLiteDAL) dal).nextIdFor(DBTable.PHOTOGRAPHERS));
            System.out.println("Trying to save photographer: " + photographer.getID());
        }
        dal.save(photographer);
    }

    @Override
    public void deletePhotographer(int ID) throws Exception {
        dal.deletePhotographer(ID);
    }

    @Override
    public IPTCModel extractIPTC(String filename) throws FileNotFoundException {
        if (existsInPicturePath(filename)) {
            String randomString = getRandomString(5);
            IPTCModel iptcModel = new Iptc();
            iptcModel.setByLine("byLine_" + randomString);
            iptcModel.setKeywords("keywords_" + randomString);
            iptcModel.setCopyrightNotice(Constants.COPYRIGHT_NOTICES.toArray()[new Random().nextInt(Constants.COPYRIGHT_NOTICES.size())].toString());
            iptcModel.setHeadline("headline_" + randomString);
            iptcModel.setCaption("caption_" + randomString);
            return iptcModel;
        } else {
            throw new FileNotFoundException("Filename " + filename + " does not exist in " + PICTURE_PATH);
        }
    }

    @Override
    public EXIFModel extractEXIF(String filename) throws FileNotFoundException {
        if (Helpers.existsInPicturePath(filename)) {
            EXIFModel exifModel = new Exif();
            exifModel.setExposureProgram(Helpers.randomEnum(ExposurePrograms.class));
            exifModel.setExposureTime(Constants.EXAMPLE_EXPOSURE_TIMES[new Random().nextInt(Constants.EXAMPLE_EXPOSURE_TIMES.length)]);
            exifModel.setFNumber(Constants.EXAMPLE_F_NUMBERS[new Random().nextInt(Constants.EXAMPLE_F_NUMBERS.length)]);
            exifModel.setFlash(Math.random() > 0.5);
            exifModel.setISOValue(Constants.EXAMPLE_ISO_VALUES[new Random().nextInt(Constants.EXAMPLE_ISO_VALUES.length)]);
            exifModel.setMake((Math.random() > 0.5 ? "Leica " : "Polaroid "));
            return exifModel;
        } else {
            throw new FileNotFoundException("Filename " + filename + " does not exist in " + PICTURE_PATH);
        }
    }

    @Override
    public void writeIPTC(String filename, IPTCModel iptc) throws Exception {
        if (existsInPicturePath(filename)) {
            Thread.sleep(50);
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public Collection<CameraModel> getCameras() {
        return dal.getCameras();
    }

    @Override
    public CameraModel getCamera(int ID) {
        return dal.getCamera(ID);
    }
}
