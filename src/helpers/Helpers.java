package helpers;

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

public class Helpers {

    public static LocalDate toLocalDate(long l) {
        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static long toLong(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
    }

    public static void closeStatementSilently(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void closeResultSilently(ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static String getRandomString(int length) {
        final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < length) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            builder.append(CHARS.charAt(index));
        }
        return builder.toString();
    }

    public static boolean existsInPicturePath(String filename) {
        Path path = FileSystems.getDefault().getPath(Constants.PICTURE_PATH, filename);
        return Files.exists(path) && !Files.isDirectory(path);
    }

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
    
     public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static Node getGridpaneNodeAt(GridPane gridpane, int row, int column) {
        return gridpane.getChildren().get(column * Constants.ROWS_PER_METAINFO_GRIDPANE + row);
    }

    public static String getDatabaseName() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/resources/properties");
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

