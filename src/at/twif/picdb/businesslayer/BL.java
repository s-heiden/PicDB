package at.twif.picdb.businesslayer;

import BIF.SWE2.interfaces.*;
import BIF.SWE2.interfaces.models.*;
import at.twif.picdb.dal.DBTable;
import at.twif.picdb.dal.SQLiteDAL;
import at.twif.picdb.models.Exif;
import at.twif.picdb.models.Iptc;
import at.twif.picdb.models.Picture;
import at.twif.picdb.helpers.Helpers;
import static at.twif.picdb.helpers.Helpers.deleteFromPicturePath;
import static at.twif.picdb.helpers.Helpers.existsInPicturePath;
import static at.twif.picdb.helpers.Helpers.getRandomString;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import at.twif.picdb.helpers.Constants;
import static at.twif.picdb.helpers.Constants.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

/**
 * The BusinessLayer implementation used by the application.
 */
public final class BL implements BusinessLayer {

    private static BL blInstance;
    private static SQLiteDAL dal;
    /**
     * Returns the single static instance.
     */
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
            String searchString,
            PhotographerModel photographerParts,
            IPTCModel iptcParts,
            EXIFModel exifParts
    ) throws Exception {
        if (photographerParts != null || iptcParts != null || exifParts != null) {
            throw new UnsupportedOperationException("Detail search not implemented yet, "
                    + "please use argument searchString for the search term");
        }        
        return dal.getPictures(searchString, null, null, null);
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
    
    /**
     * Makes a directory, if it does not exist yet.
     * 
     * @param dirPath
     * @throws SecurityException if it fails to make the directory
     */
    public void makeDir(String dirPath) throws SecurityException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (SecurityException e) {
                throw new SecurityException("Could not make directory " + dirPath + ".");
            }   
        }
    }
     
    /**
     * Generates a report pdf-file in the reports directory
     * which lists all keywords that occur and the number of their occurrences.
     */
    public void generateTagReport() {
        Map<String, Integer> keywordStrings = dal.getKeywordStrings();
        Map<String, Integer> keywordCount = new HashMap<>();
        String[] splitString;
        for (String keywordString : keywordStrings.keySet()) {
            splitString = keywordString.split("[,;\\s]");
            for (String keyword : splitString) {
                keyword = keyword
                        .toUpperCase()
                        .replaceAll("[^-_0-9a-zA-Z]", "");
                if (keywordCount.containsKey(keyword)) {
                    keywordCount.put(keyword, keywordCount.get(keyword) + keywordStrings.get(keywordString));
                } else {
                    keywordCount.put(keyword, keywordStrings.get(keywordString));
                }
            }
        }
        
        Document document = new Document();
        try {
            makeDir(REPORT_PATH);
            PdfWriter.getInstance(document, new FileOutputStream(REPORT_PATH + "/"
                    + TAG_REPORT_FILENAME + ".pdf"));
            document.open();
            document.addTitle("Tag-Report");
            Font textFont = new Font(Font.getFamily(TEXT_FONT_FAMILY),
                    TEXT_FONT_SIZE, Font.getStyleValue(TEXT_FONT_STYLE));
            Font headlineFont = new Font(Font.getFamily(HEADLINE_FONT_FAMILY),
                    HEADLINE_FONT_SIZE, Font.getStyleValue(HEADLINE_FONT_STYLE));
            PdfPTable table = new PdfPTable(2);
            table.addCell(new Phrase("Keyword/Tag", headlineFont));
            table.addCell(new Phrase("Number of photos", headlineFont));
            for (String keyword : keywordCount.keySet()) {
                table.addCell(keyword);
                table.addCell(keywordCount.get(keyword).toString());
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a report pdf-file of the active picture, including the picture,
     * the EXIF- and IPTC-info and the photographer, if known.
     * @param ID  - must be a valid picture-ID
     */
    public void generateImageReport(int ID) {
        PictureModel picture = getPicture(ID);
        Document document = new Document();
        try {
            makeDir(REPORT_PATH);
            PdfWriter.getInstance(document, new FileOutputStream(REPORT_PATH + "/"
                    + IMG_REPORT_FILENAME + "_"
                    + picture.getFileName() + ".pdf"));
            document.open();
            document.addTitle("Image-Report");
            Image image = Image.getInstance(PICTURE_PATH + "/"
                    + picture.getFileName());
            image.scaleToFit(525f, 750f);
            document.add(image);
            
            Font textFont = new Font(Font.getFamily(TEXT_FONT_FAMILY),
                    TEXT_FONT_SIZE, Font.getStyleValue(TEXT_FONT_STYLE));
            Font headlineFont = new Font(Font.getFamily(HEADLINE_FONT_FAMILY),
                    HEADLINE_FONT_SIZE, Font.getStyleValue(HEADLINE_FONT_STYLE));
            
            Paragraph EXIFPara = new Paragraph();
            EXIFPara.setSpacingBefore(20.0f);
            EXIFPara.setFirstLineIndent(10.0f);
            Paragraph IPTCPara = (Paragraph)EXIFPara.clone();
            Paragraph PhotographerPara = (Paragraph)EXIFPara.clone();
            
            EXIFPara.add(new Chunk("EXIF-Info:\n\n", headlineFont));
            EXIFPara.add(new Chunk(parseListFormatted(picture.getEXIF()), textFont).setLineHeight(15.0f));
            document.add(EXIFPara);
            
            IPTCPara.add(new Chunk("IPTC-Info:\n\n", headlineFont));
            IPTCPara.add(new Chunk(parseListFormatted(picture.getIPTC()), textFont).setLineHeight(15.0f));
            document.add(IPTCPara);

            String photogr = parseListFormatted(picture.getPhotographer());
            PhotographerPara.add(new Chunk("Photographer:\n\n", headlineFont));
            PhotographerPara.add(new Chunk(photogr != null ? photogr : "Photographer unknown.", textFont).setLineHeight(15.0f));
            document.add(PhotographerPara);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
    
    /**
     * Converts an Object with JSON-like toString() functions into a
     * line by line paragraph relating keys an values.
     * @param obj - must have JSON like toString() output; must not be null.
     * @return a formatted multi-line string
     * @throws IllegalArgumentException 
     */
    public String parseListFormatted(Object obj) throws IllegalArgumentException{
        if (obj != null) {
            return obj.toString()
                    .replaceAll(".*\\{", "")
                    .replaceAll("}", "")
                    .replaceAll(", ", "\n");
        } else {
           throw new IllegalArgumentException("Input string was null.");
        }
    }
}
