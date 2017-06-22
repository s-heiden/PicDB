package at.twif.picdb.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Provides helper functions used in the project.
 */
public class Helpers {

    /**
     * Returns a LocalDate corresponding to the given integer representing seconds since 1970-01-01.
     */
    public static LocalDate toLocalDate(long l) {
        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Returns an integer representing seconds since 1970-01-01 for the given LocalDate.
     */
    public static long toLong(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
    }

    /**
     * Closes a statement silently and catches possible SQLException.
     */
    public static void closeStatementSilently(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Closes a result set silently and catches possible SQLException.
     */
    public static void closeResultSilently(ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Returns a random string of the given length.
     */
    public static String getRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < length) {
            int index = (int) (rnd.nextFloat() * Constants.CHARS.length());
            builder.append(Constants.CHARS.charAt(index));
        }
        return builder.toString();
    }

    /**
     * Returns true if the file of the given filename exists in the picture directory.
     */
    public static boolean existsInPicturePath(String filename) {
        Path path = FileSystems.getDefault().getPath(Constants.PICTURE_PATH, filename);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    /**
     * Deletes a the file with the given filename from the picture directory.
     */
    public static void deleteFromPicturePath(String filename) {
        if (filename != null) {
            try {
                System.out.println("BL: Filename of deletable file: " + filename);
                Path path = FileSystems.getDefault().getPath(Constants.PICTURE_PATH, filename);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Returns a random enum.
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Returns the gridpane node in the given gridpane at the given coordinates.
     */
    public static Node getGridpaneNodeAt(GridPane gridpane, int row, int column) {
        int rows = gridpane.getChildren().size() / at.twif.picdb.helpers.Constants.COLS_IN_METAINFO_GRIDPANE;
        return gridpane.getChildren().get(column * rows + row);
    }

    /**
     * Reads the database name from src/resources/properties.
     */
    public static String getDatabaseName() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/at/twif/picdb/resources/properties");
            prop.load(input);
            return prop.getProperty("database_name");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
