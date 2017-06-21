package DAL;

import Models.Photographer;
import Models.Picture;
import DAL.DBSchema.*;
import Models.Camera;
import BIF.SWE2.interfaces.*;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import Models.Exif;
import Models.Iptc;
import static helpers.Helpers.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The DataAccessLayer implementation used by PicDB.
 */
public class SQLiteDAL implements DataAccessLayer {

    private static SQLiteDAL sqLiteDalInstance;

    private static final String SQLITE_JDBC = "org.sqlite.JDBC";
    private Connection connection;

    private static final String CREATE_TABLE_PICTURES =
            "CREATE TABLE IF NOT EXISTS " + DBSchema.Pictures.TABLE_NAME + " (" +
                    Pictures.COLUMN_PICTURES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Pictures.COLUMN_PICTURES_CAMERAID + " INTEGER," +
                    Pictures.COLUMN_PICTURES_FILENAME + " VARCHAR(256)," +
                    Pictures.COLUMN_PICTURES_IPTC_CAPTION + " VARCHAR(256)," +
                    Pictures.COLUMN_PICTURES_IPTC_HEADLINE + " VARCHAR(256)," +
                    Pictures.COLUMN_PICTURES_IPTC_KEYWORDS + " VARCHAR(256)," +
                    Pictures.COLUMN_PICTURES_IPTC_BYLINE + " VARCHAR(256)," +
                    Pictures.COLUMN_PICTURES_COPYRIGHTNOTICE + " VARCHAR(256)," +
                    Pictures.COLUMN_PICTURES_EXIF_MAKE + " VARCHAR(256)," +
                    Pictures.COLUMN_PICTURES_EXIF_FNUMBER + " DOUBLE," +
                    Pictures.COLUMN_PICTURES_EXIF_EXPOSURETIME + " DOUBLE," +
                    Pictures.COLUMN_PICTURES_EXIF_ISOVALUE + " DOUBLE," +
                    Pictures.COLUMN_PICTURES_EXIF_FLASH + " BOOLEAN," +
                    Pictures.COLUMN_PICTURES_EXIF_EXPOSUREPROGRAM + " VARCHAR(256));";

    private static final String CREATE_TABLE_CAMERAS =
            "CREATE TABLE IF NOT EXISTS " + Cameras.TABLE_NAME + " (" +
                    Cameras.COLUMN_CAMERAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Cameras.COLUMN_CAMERAS_PRODUCER + " VARCHAR(256)," +
                    Cameras.COLUMN_CAMERAS_MAKE + " VARCHAR(256)," +
                    Cameras.COLUMN_CAMERAS_BOUGHTON + " LONG," +
                    Cameras.COLUMN_CAMERAS_NOTES + " VARCHAR(512)," +
                    Cameras.COLUMN_CAMERAS_ISOLIMIT_GOOD + " DOUBLE," +
                    Cameras.COLUMN_CAMERAS_ISOLIMIT_ACCEPTABLE + " DOUBLE);";

    private static final String CREATE_TABLE_PHOTOGRAPHERS =
            "CREATE TABLE IF NOT EXISTS " + Photographers.TABLE_NAME + " (" +
                    Photographers.COLUMN_PHOTOGRAPHERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Photographers.COLUMN_PHOTOGRAPHERS_FIRST_NAME + " VARCHAR(256)," +
                    Photographers.COLUMN_PHOTOGRAPHERS_LAST_NAME + " VARCHAR(256)," +
                    Photographers.COLUMN_PHOTOGRAPHERS_BIRTHDAY + " LONG," +
                    Photographers.COLUMN_PHOTOGRAPHERS_NOTES + " VARCHAR(512));";

    /**
     * Returns the single static instance of this class.
     */
    public static SQLiteDAL getInstance() {
        if (sqLiteDalInstance == null) {
            sqLiteDalInstance = new SQLiteDAL();
        }
        return sqLiteDalInstance;
    }

    /**
     * Establishes the connection, sets up the database if there are no tables.
     */
    private SQLiteDAL() {
        openDBConnection();
        setupDatabase();
    }

    /**
     * Opens the connection to the sqlite database and creates it if it does not yet exist.
     */
    private void openDBConnection() {
        if (connection == null) {
            try {
                Class.forName(SQLITE_JDBC);
                connection = DriverManager.getConnection(DBSchema.DATABASE_URL);
            } catch (SQLException | ClassNotFoundException s) {
                System.err.println(s.getClass().getName() + ": " + s.getMessage());
            }
        }
    }

    /**
     * Closes the connection to the sqlite database.
     */
    private void closeDBConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("DB closed");
            } catch (SQLException s) {
                System.err.println(s.getClass().getName() + ": " + s.getMessage());
            }
        }
    }

    /**
     * Creates the necessary tables if they do not yet exist.
     */
    private void setupDatabase() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_PICTURES);
            statement.executeUpdate(CREATE_TABLE_CAMERAS);
            statement.executeUpdate(CREATE_TABLE_PHOTOGRAPHERS);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Returns true if an entity with the given id exists in the given table.
     */
    public boolean containsRowForTable(int id, DBTable table) {
        PreparedStatement statement = null;
        String string = "SELECT id FROM " + table + " WHERE id = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(string);
            statement.setInt(1, id);
            result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResultSilently(result);
            closeStatementSilently(statement);
        }
        return false;
    }

    /**
     * Returns ONE Picture from the database.
     *
     * @param id the id of the picture that is requested
     * @return the found Picture object or null if none was found
     * @throws java.lang.Exception
     */
    @Override
    public PictureModel getPicture(int id) {
        return (Picture) getObjectFrom(id, DBTable.PICTURES);
    }

    private Object getObjectFrom(int id, DBTable table) {
        Object o = null;

        PreparedStatement statement = null;
        ResultSet result = null;
        String string = "SELECT * FROM " + table + " WHERE id = ?";

        try {
            statement = connection.prepareStatement(string);
            statement.setInt(1, id);
            result = statement.executeQuery();

            if (result.next()) {
                if (null != table) {
                    switch (table) {
                        case PICTURES:
                            o = toPictureObject(result);
                            break;
                        case PHOTOGRAPHERS:
                            o = toPhotographerObject(result);
                            break;
                        case CAMERAS:
                            o = toCameraObject(result);
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResultSilently(result);
            closeStatementSilently(statement);
        }
        return o;
    }

    /**
     * Returns the PhotographerModel with the given id.
     *
     * @param id the id of the photographer that is being searched
     * @return the PhotographerModel with the given id.
     */
    @Override
    public PhotographerModel getPhotographer(int id) {
        return (Photographer) getObjectFrom(id, DBTable.PHOTOGRAPHERS);
    }

    private PhotographerModel toPhotographerObject(ResultSet rs) throws SQLException {
        PhotographerModel photographer = new Photographer();
        photographer.setID(rs.getInt(Photographers.COLUMN_PHOTOGRAPHERS_ID));
        photographer.setFirstName(rs.getString(Photographers.COLUMN_PHOTOGRAPHERS_FIRST_NAME));
        photographer.setLastName(rs.getString(Photographers.COLUMN_PHOTOGRAPHERS_LAST_NAME));
        photographer.setBirthDay(toLocalDate(rs.getLong(Photographers.COLUMN_PHOTOGRAPHERS_BIRTHDAY)));
        photographer.setNotes(rs.getString(Photographers.COLUMN_PHOTOGRAPHERS_NOTES));
        return photographer;
    }

    private PictureModel toPictureObject(ResultSet result) throws SQLException {
        PictureModel picture = new Picture();
        picture.setIPTC(new Iptc());
        picture.setEXIF(new Exif());
        picture.setID(result.getInt("id"));
        picture.setFileName(result.getString("filename"));
        picture.getIPTC().setCaption(result.getString("iptc_caption"));
        picture.getIPTC().setHeadline(result.getString("iptc_headline"));
        picture.getIPTC().setKeywords(result.getString("iptc_keywords"));
        picture.getIPTC().setByLine(result.getString("iptc_byline"));
        picture.getIPTC().setCopyrightNotice(result.getString("iptc_copyrightnotice"));
        picture.getEXIF().setMake(result.getString("exif_make"));
        picture.getEXIF().setFNumber(result.getDouble("exif_fnumber"));
        picture.getEXIF().setExposureTime(result.getDouble("exif_exposuretime"));
        picture.getEXIF().setISOValue(result.getDouble("exif_isovalue"));
        picture.getEXIF().setFlash(result.getBoolean("exif_flash"));
        picture.getEXIF().setExposureProgram(ExposurePrograms.valueOf(result.getString("exif_exposureprogram")));
        picture.setCamera(getCamera(result.getInt("camera_id")));
        return picture;
    }

    /**
     * Saves all changes to the database.
     *
     * @param picture the PictureModel object which should be saved to the database
     */
    @Override
    public void save(PictureModel picture) {
        PreparedStatement statement = null;

        if (!containsRowForTable(picture.getID(), DBTable.PICTURES)) {
            addRowToTable(picture.getID(), DBTable.PICTURES);
        }
        final String string = "UPDATE pictures SET "
                + "filename = ?,"
                + "iptc_caption = ?,"
                + "iptc_headline = ?,"
                + "iptc_keywords = ?,"
                + "iptc_byline = ?,"
                + "iptc_copyrightnotice = ?,"
                + "exif_make = ?,"
                + "exif_fnumber = ?,"
                + "exif_exposuretime = ?,"
                + "exif_isovalue = ?,"
                + "exif_flash = ?,"
                + "exif_exposureprogram = ?,"
                + "camera_id = ? WHERE id = " + picture.getID();
        try {
            statement = connection.prepareStatement(string);
            statement.setString(1, picture.getFileName());

            if (picture.getIPTC() != null) {
                statement.setString(2, picture.getIPTC().getCaption());
                statement.setString(3, picture.getIPTC().getHeadline());
                statement.setString(4, picture.getIPTC().getKeywords());
                statement.setString(5, picture.getIPTC().getByLine());
                statement.setString(6, picture.getIPTC().getCopyrightNotice());
            }
            if (picture.getEXIF() != null) {
                statement.setString(7, picture.getEXIF().getMake());
                statement.setDouble(8, picture.getEXIF().getFNumber());
                statement.setDouble(9, picture.getEXIF().getExposureTime());
                statement.setDouble(10, picture.getEXIF().getISOValue());
                statement.setBoolean(11, picture.getEXIF().getFlash());
                statement.setString(12, picture.getEXIF().getExposureProgram().name());
            }
            if (picture.getCamera() != null) {
                statement.setInt(13, picture.getCamera().getID());
            }
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeStatementSilently(statement);
        }
    }

    /**
     * Deletes a picture with the given id from the database.
     *
     * @param id the id of the picture that is to be deleted.
     * @throws java.lang.Exception
     */
    @Override
    public void deletePicture(int id) throws Exception {
        deleteRowFromTable(id, DBTable.PICTURES);
    }

    private void deleteRowFromTable(int id, DBTable table) {
        final String string = "DELETE FROM " + table + " WHERE id = " + id;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(string);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeStatementSilently(statement);
        }
    }

    /**
     * Deletes a Photographer. An exception is thrown if a photographer is still linked to a picture.
     *
     * @param id the photographer which should to be deleted
     */
    @Override
    public void deletePhotographer(int id) {
        deleteRowFromTable(id, DBTable.PHOTOGRAPHERS);
    }

    /**
     * Returns a filtered list of Pictures from the directory, based on a database SQLiteDAL.
     *
     * @param searchString
     * @param photographerParts may contain photographer information which is searched in the database
     * @param iptcParts may contain Iptc information which is searched in the database
     * @param exifParts may contain Exif information which is searched in the database
     * @return a Collection of PictureModel objects which match the filter criteria
     */
    @Override
    public Collection<PictureModel> getPictures(String searchString, PhotographerModel photographerParts, IPTCModel iptcParts, EXIFModel exifParts) {
        if (photographerParts != null || iptcParts != null || exifParts != null) {
            throw new UnsupportedOperationException("Detail search not implemented yet, "
                    + "please use argument searchString for the search term");
        }              
        Collection<PictureModel> pictureModels = new ArrayList<>();
        pictureModels.addAll(getPicturesHaving(searchString));
        return pictureModels;
    }
    
    private ArrayList<PictureModel> getPicturesHaving(String searchString){
        searchString = searchString
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        
        ArrayList<PictureModel> pictures = new ArrayList<>();
        final String string = "SELECT * FROM " + DBSchema.Pictures.TABLE_NAME + " WHERE "
                + Pictures.COLUMN_PICTURES_IPTC_CAPTION + " LIKE ? OR "
                + Pictures.COLUMN_PICTURES_IPTC_HEADLINE + " LIKE ? OR "
                + Pictures.COLUMN_PICTURES_IPTC_KEYWORDS + " LIKE ? OR "
                + Pictures.COLUMN_PICTURES_IPTC_BYLINE + " LIKE ? OR "
                + Pictures.COLUMN_PICTURES_COPYRIGHTNOTICE + " LIKE ?";
                
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(string);
            for (int i = 1; i <= 5; i++){
                statement.setString(i, "%" + searchString + "%");            
            }
            result = statement.executeQuery();
            while (result.next()) {
                pictures.add(toPictureObject(result));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResultSilently(result);
            closeStatementSilently(statement);
        }
        return pictures;
    }

    /**
     * Returns a collection of all photographers.
     *
     * @return collection of all photographers
     */
    @Override
    public Collection<PhotographerModel> getPhotographers() {
        Collection<PhotographerModel> photographerModels = new ArrayList<>();
        getObjectsFrom(DBTable.PHOTOGRAPHERS).forEach((o) -> {
            photographerModels.add((PhotographerModel) o);
        });
        return photographerModels;
    }

    /**
     * Saves all changes of the given PhotographerModel instance to the database.
     *
     * @param photographer the PhotographerModel instance whose member information should be stored
     */
    @Override
    public void save(PhotographerModel photographer) {
        PreparedStatement statement = null;

        if (!containsRowForTable(photographer.getID(), DBTable.PHOTOGRAPHERS)) {
            addRowToTable(photographer.getID(), DBTable.PHOTOGRAPHERS);
        }
        final String string = "UPDATE photographers SET "
                + "first_name = ?,"
                + "last_name = ?,"
                + "birthday = ?,"
                + "notes = ? "
                + "WHERE id = " + photographer.getID();
        try {
            statement = connection.prepareStatement(string);
            statement.setString(1, photographer.getFirstName());
            statement.setString(2, photographer.getLastName());
            statement.setLong(3, toLong(photographer.getBirthDay()));
            statement.setString(4, photographer.getNotes());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeStatementSilently(statement);
        }
    }

    /**
     * Returns an ArrayList<Object> of all rows of the given table, de-serialized into corresponding objects.
     */
    public ArrayList<Object> getObjectsFrom(DBTable table) {
        ArrayList<Object> objects = new ArrayList<>();
        final String string = "SELECT * FROM " + table;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(string);
            result = statement.executeQuery();
            while (result.next()) {
                if (null != table) {
                    switch (table) {
                        case PICTURES:
                            objects.add(toPictureObject(result));
                            break;
                        case PHOTOGRAPHERS:
                            objects.add(toPhotographerObject(result));
                            break;
                        case CAMERAS:
                            objects.add(toCameraObject(result));
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResultSilently(result);
            closeStatementSilently(statement);
        }
        return objects;
    }

    /**
     * Returns a list of all cameras.
     *
     * @return a list of all cameras
     */
    @Override
    public Collection<CameraModel> getCameras() {
        Collection<CameraModel> cameraModels = new ArrayList<>();
        getObjectsFrom(DBTable.CAMERAS).forEach((o) -> {
            cameraModels.add((CameraModel) o);
        });
        return cameraModels;
    }

    /**
     * Returns the camera with the given id.
     *
     * @param id the id that is searched
     * @return the camera with the given id
     */
    @Override
    public Camera getCamera(int id) {
        return (Camera) getObjectFrom(id, DBTable.CAMERAS);
    }

    private CameraModel toCameraObject(ResultSet rs) throws SQLException {
        Camera camera = new Camera();
        camera.setID(rs.getInt("id"));
        camera.setProducer(rs.getString("producer"));
        camera.setMake(rs.getString("make"));
        camera.setBoughtOn(toLocalDate(rs.getLong("bought_on")));
        camera.setNotes(rs.getString("notes"));
        camera.setISOLimitAcceptable(rs.getDouble("iso_limit_acceptable"));
        camera.setISOLimitGood(rs.getDouble("iso_limit_good"));
        return camera;
    }

    private void addRowToTable(int id, DBTable table) {
        PreparedStatement statement = null;
        String string = "INSERT INTO " + table + " (id) VALUES (" + id + ")";
        try {
            statement = connection.prepareStatement(string);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeStatementSilently(statement);
        }
    }

    /**
     * Returns the next possible id for the given table.
     */
    public int nextIdFor(DBTable table) {
        int id = 1;
        PreparedStatement statement = null;
        ResultSet result = null;
        String string = "SELECT MAX(id) + 1 AS next_id FROM " + table;

        try {
            statement = connection.prepareStatement(string);
            result = statement.executeQuery();
            if (result.next()) {
                id = result.getInt("next_id");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResultSilently(result);
            closeStatementSilently(statement);
        }
        return id;
    }
    
    public Map<String, Integer> getKeywordStrings() {
        Map<String, Integer> keywordStrings = new HashMap<>();
        final String string = "SELECT " + Pictures.COLUMN_PICTURES_IPTC_KEYWORDS
                + ", COUNT(*) AS COUNT"
                + " FROM " + DBSchema.Pictures.TABLE_NAME
                + " GROUP BY " + DBSchema.Pictures.COLUMN_PICTURES_IPTC_KEYWORDS;
                
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(string);
            rs = statement.executeQuery();
            while (rs.next()) {
                keywordStrings.put(rs.getString(Pictures.COLUMN_PICTURES_IPTC_KEYWORDS), rs.getInt("COUNT"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResultSilently(rs);
            closeStatementSilently(statement);
        }
        return keywordStrings;
    }
}
