package org.cgi.assesment.util;

import org.cgi.assesment.model.LogResult;

import java.util.ArrayList;
import java.util.List;

public class LogResultUtil {
    public static int COLLECTION_COUNT = 5;

    public static List<LogResult> createLogsResults() {
        List<LogResult> logs = new ArrayList();
        for (int i = 1; i <= COLLECTION_COUNT; i++) {
            logs.add(createLogResult(i));
        }
        return logs;
    }

    public static LogResult createLogResult(final int id) {
        LogResult logs = new LogResult();
        logs.setErrorDescription("error Description" + id);
        logs.setOccurrences((long) id);
        return logs;
    }
}
