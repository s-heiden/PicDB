package BIF.SWE2.interfaces;

/**
 * Defines possible ExposurePrograms.
 */
public enum ExposurePrograms {
    NotDefined(0),
    Manual(1),
    Normal(2),
    AperturePriority(3),
    ShutterPriority(4),
    CreativeProgram(5),
    ActionProgram(6),
    PortraitMode(7),
    LandscapeMode(8);

    private int value;

    private ExposurePrograms(int value) {
        this.value = value;
    }

    /**
     * Returns the value of this object.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the String representation of all possible values as an array.
     */
    public static String[] getNamesAsArray() {
        ExposurePrograms[] exposurePrograms = values();
        String[] names = new String[exposurePrograms.length];
        for (int i = 0; i < exposurePrograms.length; i++) {
            names[i] = exposurePrograms[i].name();
        }
        return names;
    }
}
