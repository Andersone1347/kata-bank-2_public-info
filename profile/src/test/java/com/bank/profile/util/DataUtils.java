package com.bank.profile.util;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {



    public String createTimeStamp() {
        Timestamp timestamp = Timestamp.valueOf("2024-06-26 12:34:56");
        ZonedDateTime utcZonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return utcZonedDateTime.format(formatter) + "+00:00";
    }



}
