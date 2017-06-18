package helpers;

import java.util.Arrays;
import java.util.Collection;

/**
 * Encapsulates a number of application specific constants.
 */
public class Constants {

    /**
     * Path where the pictures reside in relation to the run path.
     */
    public static final String PICTURE_PATH = "user_folder";
    
    /**
     * Exemplary Iso values.
     */
    public static final double[] EXAMPLE_ISO_VALUES = {100, 200, 400, 800, 1600, 6400};
    
    /**
     * Exemplary Exposure time values.
     */
    public static final double[] EXAMPLE_EXPOSURE_TIMES = {0.001, 0.002, 0.004, 0.008, 0.016, 0.033, 0.066, 0.125, 0.25, 0.5, 1};
    
    /**
     * Exemplary fNumber values.
     */
    public static final double[] EXAMPLE_F_NUMBERS = {2.8, 4, 5.6, 8, 11, 16};
    
    /**
     * All possible copyright notices.
     */
    public static final Collection<String> COPYRIGHT_NOTICES = Arrays.asList("All rights reserved", "CC-BY", "CC-BY-SA", "CC-BY-ND", "CC-BY-NC", "CC-BY-NC-SA", "CC-BY-NC-ND");
    
    /**
     * The number of columns in the metainfo gridpane in main window.
     */
    public static final int COLS_IN_METAINFO_GRIDPANE = 2;
    
    /**
     * The path were the application logo resides.
     */
    public static final String LOGO_PATH = "file:src/resources/logo.jpg";
    
    /**
     * Some characters for debugging.
     */
    public static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    
    public static final String ISO_GOOD_PATH = "iso_good.png";
    public static final String EP_PORTRAIT_MODE_PATH = "ep_portrait_mode.png";
    public static final String EP_NORMAL_PATH = "ep_normal.png";
    public static final String EP_LANDSCAPE_MODE_PATH = "ep_landscape_mode.png";
    public static final String ISO_NOT_DEF_PATH = "iso_not_def.png";
    public static final String RESOURCE_PATH = "Resources/";
    public static final String EP_CREATIVE_PROGRAM_PATH = "ep_creative_program.png";
    public static final String EP_ACTION_PROGRAM_PATH = "ep_action_program.png";
    public static final String ISO_NO_CAMERA_PATH = "iso_no_camera.png";
    public static final String EP_MANUAL_PATH = "ep_manual.png";
    public static final String EP_SHUTTER_PRIORITY_PATH = "ep_shutter_priority.png";
    public static final String ISO_NOISEY_PATH = "iso_noisey.png";
    public static final String ISO_ACCEPTABLE_PATH = "iso_acceptable.png";
    public static final String EP_APERTURE_PRIORITY = "ep_aperture_priority.png";
    public static final String EP_NOT_DEFINED_PATH = "ep_not_defined.png";

    
}
