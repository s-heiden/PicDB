package helpers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Helpers {    
    public static LocalDate toLocalDate(long l){
        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate();
    }        
}
