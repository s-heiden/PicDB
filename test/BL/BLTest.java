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
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());
        
        BL bl = new BL();
        assertNotNull("getBusinessLayer", bl);
        Collection<PictureModel> lst = bl.getPictures(null, null, null, null);
        assertNotNull("bl.getPictures", lst);
        assertTrue("bl.getPictures returned nothing", lst.size() > 0);
        lst.forEach((p) -> {
            System.out.println(p);
        });
    }

    @Test
    public void bl_should_return_Photographers() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());
        
        BL bl = new BL();
        assertNotNull("getBusinessLayer", bl);
        Collection<PhotographerModel> lst = bl.getPhotographers();
        assertNotNull("bl.getPhotographers", lst);
        assertTrue("bl.getPhotographers returned nothing", lst.size() > 0);
    }

    @Test
    public void bl_should_simulate_EXIF_extraction() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());
        
        BL bl = new BL();
        assertNotNull("getBusinessLayer", bl);
        EXIFModel mdl = bl.extractEXIF("Img1.jpg");
        assertNotNull("bl.ExtractEXIF", mdl);
        assertTrue("ExposureTime != 0", mdl.getExposureTime() != 0);
        assertTrue("FNumber != 0", mdl.getFNumber() != 0);
        assertTrue("ISOValue != 0", mdl.getISOValue() != 0);
        assertNotNull(mdl.getMake());
    }

    @Test
    public void bl_should_return_Cameras() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());
        
        BL bl = new BL();
        assertNotNull("getBusinessLayer", bl);
        Collection<CameraModel> lst = bl.getCameras();
        assertNotNull("bl.getCameras", lst);
        assertTrue("bl.getCameras returned nothing", lst.size() > 0);
    }

    @Test
    public void bl_should_return_Camera_by_ID() throws Exception {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase());
        
        BL bl = new BL();     
        assertNotNull("getBusinessLayer", bl);
        CameraModel obj = bl.getCamera(1);
        assertNotNull("bl.getCamera", obj);
    }

}
