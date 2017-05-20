package dal;

import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Random;
import models.Camera;
import models.EXIF;
import models.IPTC;
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

    @Test
    public void testHasPicture() throws SQLException {
        System.out.println(""
                + "hasPicture\n"
                + "==========");
        SQLiteDAL dal = new SQLiteDAL();
        assertEquals(true, dal.containsPicture(1));
    }

    @Test
    public void testSave_PictureModel() throws Exception {
        SQLiteDAL dal = new SQLiteDAL();
        System.out.println(""
                + "save\n"
                + "====");
        PictureModel picture = new Picture();
        int NEW = 1234;
        
        picture.setID(NEW);
        
        picture.setFileName("C:\\imgs\\img_0001.jpg");
        picture.setCamera(new Camera(1, "Sony", "X-301", LocalDate.now(), "Inter Arma Enim Silent Leges", 3.1, 4.7));
        picture.setEXIF(new EXIF("Sony", 34.1, 12.1, 400, true));
        picture.setIPTC(new IPTC("Example Caption", "Example Headline", "A, number, of, keywords", "John Photographer", "(c) 2017"));
        dal.save(picture);
        
    }
    

    @Test
    public void testGet_PictureModel() throws Exception {
        System.out.println(""
                + "get\n"
                + "===");
        SQLiteDAL dal = new SQLiteDAL();
        int ID = 1;
        PictureModel p = dal.getPicture(ID);
        System.out.println(p);
        assertEquals(p.getID(), ID);
    }

}
