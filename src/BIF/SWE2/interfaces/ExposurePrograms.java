package BIF.SWE2.interfaces;

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

    public int getValue() {
        return value;
    }

    public static String[] getNamesAsArray() {
        ExposurePrograms[] exposurePrograms = values();
        String[] names = new String[exposurePrograms.length];
        for (int i = 0; i < exposurePrograms.length; i++) {
            names[i] = exposurePrograms[i].name();
        }
        return names;
    }
}
