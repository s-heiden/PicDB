package DAL;

import DAL.SQLiteDAL;
import DAL.DBTable;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Random;
import Models.Camera;
import Models.Exif;
import Models.Iptc;
import Models.Photographer;
import Models.Picture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SQLiteDALTest {

    public SQLiteDALTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public void test_has_picture(int id) throws SQLException {;
        SQLiteDAL dal = SQLiteDAL.getInstance();
        assertEquals(true, dal.containsRowForTable(id, DBTable.PICTURES));
    }

    public void test_has_camera(int id) throws SQLException {
        SQLiteDAL dal = SQLiteDAL.getInstance();
        assertEquals(true, dal.containsRowForTable(id, DBTable.CAMERAS));
    }

    public void test_has_photographer(int id) throws SQLException {
        SQLiteDAL dal = SQLiteDAL.getInstance();
        assertEquals(true, dal.containsRowForTable(id, DBTable.PHOTOGRAPHERS));
    }

    @Test
    public void hello_Any_DataAccessLayer() {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        assertNotNull("getAnyDataAccessLayer", dal);
    }

    @Test
    public void test_save_picture() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        PictureModel picture = new Picture();
        int testID = 1234;

        picture.setID(testID);

        picture.setFileName("C:\\imgs\\img_0001.jpg");
        picture.setCamera(new Camera(1, "Sony", "X-301", LocalDate.now(), "Inter Arma Enim Silent Leges", 3.1, 4.7));
        picture.setEXIF(new Exif(
                "Sony",
                34.1,
                12.1,
                400,
                true
        ));
        picture.setIPTC(new Iptc(
                "Example Caption",
                "Example Headline",
                "A, number, of, keywords",
                "John Photographer",
                "(c) 2017"
        ));
        dal.save(picture);
        assertEquals(true, dal.containsRowForTable(testID, DBTable.PICTURES));
        dal.deletePicture(testID);
        assertEquals(false, dal.containsRowForTable(testID, DBTable.PICTURES));
    }

    @Test
    public void test_get_picture() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        int testID = 1234;
        PictureModel picture = new Picture();
        picture.setID(testID);

        picture.setFileName("C:\\imgs\\img_0001.jpg");
        picture.setCamera(null);
        picture.setEXIF(null);
        dal.save(picture);

        PictureModel p = dal.getPicture(1234);
        System.out.println(p);
        assertEquals(p.getID(), 1234);
    }

    @Test
    public void test_save_photographer() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        PhotographerModel p = new Photographer();
        int testID = new Random().nextInt(Integer.MAX_VALUE);

        p.setID(testID);
        p.setBirthDay(LocalDate.now());
        p.setFirstName("Temporary");
        p.setLastName("Photographer");
        p.setNotes("Notes, notes");

        dal.save(p);
        assertEquals(true, dal.containsRowForTable(testID, DBTable.PHOTOGRAPHERS));
        System.out.println((dal.getPhotographer(testID)));
        dal.deletePhotographer(testID);
        assertEquals(false, dal.containsRowForTable(testID, DBTable.PHOTOGRAPHERS));
    }

    @Test
    public void test_get_photographer() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        int ID = 1;
        PhotographerModel p = dal.getPhotographer(ID);
        System.out.println(p);
        assertEquals(p.getID(), ID);
    }

    @Test
    public void test_get_camera() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        int ID = 1;
        CameraModel p = dal.getCamera(ID);
        System.out.println(p);
        assertEquals(p.getID(), ID);
    }

    @Test
    public void test_get_cameras() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        Collection<CameraModel> d = dal.getCameras();
        d.forEach((c) -> {
            System.out.println(c);
        });
        assertNotNull(d);
    }

    @Test
    public void test_get_photographers() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        Collection<PhotographerModel> d = dal.getPhotographers();
        d.forEach((c) -> {
            System.out.println(c);
        });
        assertNotNull(d);
    }

    @Test
    public void test_get_pictures_without_search() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        Collection<PictureModel> d = dal.getPictures(null, null, null, null);
        d.forEach((c) -> {
            System.out.println(c);
        });
        assertNotNull(d);
    }

    @Test
    public void test_next_id_for() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());

        SQLiteDAL dal = SQLiteDAL.getInstance();
        System.out.println("Next ID for Photographers: " + dal.nextIdFor(DBTable.PHOTOGRAPHERS));
    }
}
