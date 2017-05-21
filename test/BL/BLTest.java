package BL;

import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BLTest {

    public BLTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void bl_should_return_Pictures() throws Exception {
        BL bl = new BL();
        assertNotNull(bl);
        Collection<PictureModel> lst = bl.getPictures(null, null, null, null);
        assertNotNull(lst);
        assertTrue(lst.size() > 0);
        lst.forEach((p) -> {
            System.out.println(p);
        });
    }
}
