package dal;

import BIF.SWE2.interfaces.*;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import helpers.Helpers;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SQLiteDAL implements DataAccessLayer {

    private static final String DATABASE_URL = "jdbc:sqlite:picdb.db";
    private static final String SQLITE_JDBC = "org.sqlite.JDBC";
    private Connection connection;

    /**
     * Establishes the connection, sets up the database if there are no tables.
     */
    public SQLiteDAL() {
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
                connection = DriverManager.getConnection(DATABASE_URL);
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
            // Setting up table 'pictures'
            final String sqlPictures = "CREATE TABLE IF NOT EXISTS pictures\n"
                    + "(\n"
                    + "    id INTEGER PRIMARY KEY,\n"
                    + "    camera_id INTEGER,\n"
                    + "    filename VARCHAR(256),\n"
                    + "    iptc_caption VARCHAR(256),\n"
                    + "    iptc_headline VARCHAR(256),\n"
                    + "    iptc_keywords VARCHAR(256),\n"
                    + "    iptc_byline VARCHAR(256),\n"
                    + "    iptc_copyrightnotice VARCHAR(256),\n"
                    + "    exif_make VARCHAR(256),\n"
                    + "    exif_fnumber DOUBLE,\n"
                    + "    exif_exposuretime DOUBLE,\n"
                    + "    exif_isovalue DOUBLE,\n"
                    + "    exif_flash BOOLEAN\n,"
                    + "    exif_exposureprogram INT\n"
                    + ")";
            statement.executeUpdate(sqlPictures);
            // Setting up table 'cameras'
            final String sqlCameras = "CREATE TABLE IF NOT EXISTS cameras\n"
                    + "(\n"
                    + "    id INTEGER PRIMARY KEY,\n"
                    + "    producer VARCHAR(256),\n"
                    + "    make VARCHAR(256),\n"
                    + "    bought_on LONG,\n"
                    + "    notes VARCHAR(512),\n"
                    + "    iso_limit_good DOUBLE,\n"
                    + "    iso_limit_acceptable DOUBLE\n"
                    + ")";
            statement.executeUpdate(sqlCameras);
            // Setting up table 'photographers'
            final String sqlPhotographers = "CREATE TABLE IF NOT EXISTS photographers\n"
                    + "(\n"
                    + "    id INTEGER PRIMARY KEY,\n"
                    + "    first_name VARCHAR(256),\n"
                    + "    last_name VARCHAR(256),\n"
                    + "    birthday LONG,\n"
                    + "    notes VARCHAR(512)\n"
                    + ")";
            statement.executeUpdate(sqlPhotographers);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public boolean containsRowForTable(int id, DBTable table) {
        PreparedStatement statement = null;
        String string = "SELECT id FROM " + table + " WHERE id = ?";
        try {
            statement = connection.prepareStatement(string);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            return result.next();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
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
    public PictureModel getPicture(int id) throws Exception {
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
            } else {
                // no row of this id was found
            }
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return o;
    }

    /**
     * Returns the PhotographerModel with the given id.
     *
     * @param id the id of the photographer that is being searched
     * @return the PhotographerModel with the given id.
     * @throws java.lang.Exception
     */
    @Override
    public PhotographerModel getPhotographer(int id) throws Exception {
        return (Photographer) getObjectFrom(id, DBTable.PHOTOGRAPHERS);
    }

    private PhotographerModel toPhotographerObject(ResultSet rs) throws SQLException {
        PhotographerModel photographer = new Photographer();
        photographer.setID(rs.getInt("id"));
        photographer.setFirstName(rs.getString("first_name"));
        photographer.setLastName(rs.getString("last_name"));
        photographer.setBirthDay(Helpers.toLocalDate(rs.getLong("birthday")));
        photographer.setNotes(rs.getString("notes"));
        return photographer;
    }

    private PictureModel toPictureObject(ResultSet result) throws SQLException {
        PictureModel picture = new Picture();
        picture.setIPTC(new IPTC());
        picture.setEXIF(new EXIF());
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
        picture.setCamera(getCamera(result.getInt("camera_id")));
        return picture;
    }

    /**
     * Saves all changes to the database.
     *
     * @param picture the PictureModel object which should be saved to the database
     * @throws java.lang.Exception
     */
    @Override
    public void save(PictureModel picture) throws Exception {
        // check if the picture already exists
        if (!containsRowForTable(picture.getID(), DBTable.PICTURES)) {
            addRowToTable(picture.getID(), DBTable.PICTURES);
        }
        // update the database
        final String string = "UPDATE pictures SET "
                + "filename=?,"
                + "iptc_caption=?,"
                + "iptc_headline=?,"
                + "iptc_keywords=?,"
                + "iptc_byline=?,"
                + "iptc_copyrightnotice=?,"
                + "exif_make=?,"
                + "exif_fnumber=?,"
                + "exif_exposuretime=?,"
                + "exif_isovalue=?,"
                + "exif_flash=?,"
                + "exif_exposureprogram=?,"
                + "camera_id=?"
                + " WHERE ID=" + picture.getID();

        PreparedStatement updateStatement = connection.prepareStatement(string);
        updateStatement.setString(1, picture.getFileName());
        updateStatement.setString(2, picture.getIPTC().getCaption());
        updateStatement.setString(3, picture.getIPTC().getHeadline());
        updateStatement.setString(4, picture.getIPTC().getKeywords());
        updateStatement.setString(5, picture.getIPTC().getByLine());
        updateStatement.setString(6, picture.getIPTC().getCopyrightNotice());
        updateStatement.setString(7, picture.getEXIF().getMake());
        updateStatement.setDouble(8, picture.getEXIF().getFNumber());
        updateStatement.setDouble(9, picture.getEXIF().getExposureTime());
        updateStatement.setDouble(10, picture.getEXIF().getISOValue());
        updateStatement.setBoolean(11, picture.getEXIF().getFlash());
        updateStatement.setInt(12, picture.getEXIF().getExposureProgram().getValue());
        updateStatement.setInt(13, picture.getCamera().getID());
        updateStatement.executeUpdate();
        updateStatement.close();
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
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Deletes a Photographer. An exception is thrown if a photographer is still linked to a picture.
     *
     * @param id the photographer which should to be deleted
     * @throws java.lang.Exception
     */
    @Override
    public void deletePhotographer(int id) throws Exception {
        deleteRowFromTable(id, DBTable.PHOTOGRAPHERS);
    }

    /**
     * Returns a filtered list of Pictures from the directory, based on a database SQLiteDAL.
     *
     * @param namePart may contain a name which is searched in the database
     * @param photographerParts may contain photographer information which is searched in the database
     * @param iptcParts may contain IPTC information which is searched in the database
     * @param exifParts may contain EXIF information which is searched in the database
     * @return a Collection of PictureModel objects which match the filter criteria
     */
    @Override
    public Collection<PictureModel> getPictures(String namePart, PhotographerModel photographerParts, IPTCModel iptcParts, EXIFModel exifParts) throws Exception {
        // TODO: implement search
        Collection<PictureModel> pictureModels = new ArrayList<>();
        getObjectsFrom(DBTable.PICTURES).forEach((o) -> {
            pictureModels.add((PictureModel) o);
        });
        return pictureModels;
    }

    /**
     * Returns a collection of all photographers.
     *
     * @return collection of all photographers
     * @throws java.lang.Exception
     */
    @Override
    public Collection<PhotographerModel> getPhotographers() throws Exception {
        Collection<PhotographerModel> cameraModels = new ArrayList<>();
        getObjectsFrom(DBTable.PHOTOGRAPHERS).forEach((o) -> {
            cameraModels.add((PhotographerModel) o);
        });
        return cameraModels;
    }

    /**
     * Saves all changes of the given PhotographerModel instance to the database.
     *
     * @param photographer the PhotographerModel instance whose member information should be stored
     * @throws java.lang.Exception
     */
    @Override
    public void save(PhotographerModel photographer) throws Exception {
        // check if the photographer already exists
        if (getPhotographer(photographer.getID()) == null) {
            Statement insertStatement = connection.createStatement();
            final String insertSQL = "INSERT INTO photographers (id) VALUES (" + photographer.getID() + ")";
            insertStatement.executeUpdate(insertSQL);
            insertStatement.close();
        }
        // update the database
        final String updateSQL = "UPDATE photographers SET "
                + "first_name=?,"
                + "last_name=?,"
                + "birthday=?,"
                + "notes=?"
                + " WHERE id=" + photographer.getID();
        PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
        updateStatement.setString(1, photographer.getFirstName());
        updateStatement.setString(2, photographer.getLastName());
        updateStatement.setDate(3, Date.valueOf(photographer.getBirthDay()));
        updateStatement.setString(4, photographer.getNotes());
        updateStatement.executeUpdate();
        updateStatement.close();
    }

    public ArrayList<Object> getObjectsFrom(DBTable table) {
        ArrayList<Object> objects = new ArrayList<Object>();
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
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
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
        Collection cameraModels = new ArrayList<>();
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
        camera.setBoughtOn(Helpers.toLocalDate(rs.getLong("bought_on")));
        camera.setNotes(rs.getString("notes"));
        camera.setISOLimitAcceptable(rs.getDouble("iso_limit_acceptable"));
        camera.setISOLimitGood(rs.getDouble("iso_limit_good"));
        return camera;
    }

    private void addRowToTable(int id, DBTable table) {
        PreparedStatement statement;
        String string = "INSERT INTO " + table + " (id) VALUES (" + id + ")";
        try {
            statement = connection.prepareStatement(string);
            statement.executeUpdate();
        } catch (SQLException s) {
            System.err.println(s.getClass().getName() + ": " + s.getMessage());
        } finally {

        }
    }
}
