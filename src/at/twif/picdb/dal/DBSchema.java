package at.twif.picdb.dal;

/**
 * Encapsulates database related methods and values.
 */
public final class DBSchema {

    /**
     * The URL of the database can be set by the user in resources/properties.
     */
    public static final String DATABASE_URL = at.twif.picdb.helpers.Helpers.getDatabaseName();

    private DBSchema() {}
    
    /**
     * Contains the database columns relating to Pictures as String constants.
     */
    public static final class Pictures {
        public static final String TABLE_NAME = "pictures";
        public static final String COLUMN_PICTURES_ID = "id";
        public static final String COLUMN_PICTURES_CAMERAID = "camera_id";
        public static final String COLUMN_PICTURES_FILENAME = "filename";
        public static final String COLUMN_PICTURES_IPTC_CAPTION = "iptc_caption";
        public static final String COLUMN_PICTURES_IPTC_HEADLINE = "iptc_headline";
        public static final String COLUMN_PICTURES_IPTC_KEYWORDS = "iptc_keywords";
        public static final String COLUMN_PICTURES_IPTC_BYLINE = "iptc_byline";
        public static final String COLUMN_PICTURES_COPYRIGHTNOTICE = "iptc_copyrightnotice";
        public static final String COLUMN_PICTURES_EXIF_MAKE = "exif_make";
        public static final String COLUMN_PICTURES_EXIF_FNUMBER = "exif_fnumber";
        public static final String COLUMN_PICTURES_EXIF_EXPOSURETIME = "exif_exposuretime";
        public static final String COLUMN_PICTURES_EXIF_ISOVALUE = "exif_isovalue";
        public static final String COLUMN_PICTURES_EXIF_FLASH = "exif_flash";
        public static final String COLUMN_PICTURES_EXIF_EXPOSUREPROGRAM = "exif_exposureprogram";
    }

    /**
     * Contains the database columns relating to Cameras as String constants.
     */
    public static final class Cameras {
        public static final String TABLE_NAME = "cameras";
        public static final String COLUMN_CAMERAS_ID = "id";
        public static final String COLUMN_CAMERAS_PRODUCER = "producer";
        public static final String COLUMN_CAMERAS_MAKE = "make";
        public static final String COLUMN_CAMERAS_BOUGHTON = "bought_on";
        public static final String COLUMN_CAMERAS_NOTES = "notes";
        public static final String COLUMN_CAMERAS_ISOLIMIT_GOOD = "iso_limit_good";
        public static final String COLUMN_CAMERAS_ISOLIMIT_ACCEPTABLE = "iso_limit_acceptable";
    }

    /**
     * Contains the database columns relating to Photographers as String constants.
     */
    public static final class Photographers {
        public static final String TABLE_NAME = "photographers";
        public static final String COLUMN_PHOTOGRAPHERS_ID = "id";
        public static final String COLUMN_PHOTOGRAPHERS_FIRST_NAME = "first_name";
        public static final String COLUMN_PHOTOGRAPHERS_LAST_NAME = "last_name";
        public static final String COLUMN_PHOTOGRAPHERS_BIRTHDAY = "birthday";
        public static final String COLUMN_PHOTOGRAPHERS_NOTES = "notes";
    }
}
