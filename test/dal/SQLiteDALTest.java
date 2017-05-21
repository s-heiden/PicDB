package dal;

import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Random;
import models.Camera;
import models.EXIF;
import models.IPTC;
import models.Photographer;
import models.Picture;
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

    public void test_has_picture(int id) throws SQLException {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
        SQLiteDAL dal = new SQLiteDAL();
        assertEquals(true, dal.containsRowForTable(id, DBTable.PICTURES));
    }

    public void test_has_camera(int id) throws SQLException {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
        SQLiteDAL dal = new SQLiteDAL();
        assertEquals(true, dal.containsRowForTable(id, DBTable.CAMERAS));
    }

    public void test_has_photographer(int id) throws SQLException {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
        SQLiteDAL dal = new SQLiteDAL();
        assertEquals(true, dal.containsRowForTable(id, DBTable.PHOTOGRAPHERS));
    }

    @Test
    public void test_save_picture() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
        PictureModel picture = new Picture();
        int testID = 1234;

        picture.setID(testID);

        picture.setFileName("C:\\imgs\\img_0001.jpg");
        picture.setCamera(new Camera(1, "Sony", "X-301", LocalDate.now(), "Inter Arma Enim Silent Leges", 3.1, 4.7));
        picture.setEXIF(new EXIF(
                "Sony", 
                34.1, 
                12.1, 
                400, 
                true
        ));
        picture.setIPTC(new IPTC(
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
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
        int ID = 1;
        PictureModel p = dal.getPicture(ID);
        System.out.println(p);
        assertEquals(p.getID(), ID);
    }

    @Test
    public void test_save_photographer() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
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
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
        int ID = 1;
        PhotographerModel p = dal.getPhotographer(ID);
        System.out.println(p);
        assertEquals(p.getID(), ID);
    }

    @Test
    public void test_get_camera() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
        int ID = 1;
        CameraModel p = dal.getCamera(ID);
        System.out.println(p);
        assertEquals(p.getID(), ID);
    }

    @Test
    public void test_get_cameras() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
        Collection<CameraModel> d = dal.getCameras();
        d.forEach((c) -> {
            System.out.println(c);
        });
        assertNotNull(d);
    }

    @Test
    public void test_get_photographers() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
        Collection<PhotographerModel> d = dal.getPhotographers();
        d.forEach((c) -> {
            System.out.println(c);
        });
        assertNotNull(d);
    }
        
    @Test
    public void test_get_pictures_without_search() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());

        SQLiteDAL dal = new SQLiteDAL();
        Collection<PictureModel> d = dal.getPictures(null, null, null, null);
        d.forEach((c) -> {
            System.out.println(c);
        });
        assertNotNull(d);
    }
}