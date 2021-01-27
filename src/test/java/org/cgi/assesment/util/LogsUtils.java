package org.cgi.assesment.util;

import org.cgi.assesment.model.Logs;

import java.util.ArrayList;
import java.util.List;

public class LogsUtils {
    public static int COLLECTION_COUNT = 5;

    public static List<Logs> createLogs() {
        List<Logs> logs = new ArrayList();
        for (int i = 1; i <= COLLECTION_COUNT; i++) {
            logs.add(createLog(i));
        }
        return logs;
    }

    public static Logs createLog(final int id) {
        Logs logs = new Logs();
        logs.setLogLevel("DEBUG");
        logs.setDescription("error Description" + id);
        logs.setId(id);
        logs.setThread("[main]"+id);
        logs.setTimeStamp("2018-09-10 15:32:34,61"+id);
        return logs;
    }

}
