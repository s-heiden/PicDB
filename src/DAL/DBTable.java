package DAL;

/**
 * An enumeration of all possible database table names.
 */
public enum DBTable {
    PICTURES, PHOTOGRAPHERS, CAMERAS;

    @Override
    public final String toString() {
        switch (this) {
            case PICTURES:
                return "pictures";
            case PHOTOGRAPHERS:
                return "photographers";
            case CAMERAS:
                return "cameras";
            default:
                throw new IllegalArgumentException();
        }
    }
}
