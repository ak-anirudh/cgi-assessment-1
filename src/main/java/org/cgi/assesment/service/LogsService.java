package org.cgi.assesment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cgi.assesment.model.LogResult;
import org.cgi.assesment.model.Logs;
import org.cgi.assesment.repository.LogsRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogsService {

    private final LogsRepository logsRepository;

    public void load(String logFile) throws IOException {
        log.info("Reading File .... {}", logFile);
        Stream<String> stream = Files.lines(Paths.get(logFile));
        stream.forEach(this::parse);
    }

    public void parse(String line){
        final String timestampRgx = "(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})";
        final String levelRgx = "(?<level>INFO|ERROR|WARN|TRACE|DEBUG|FATAL)";
        final String threadRgx = "\\[(?<thread>[^\\]]+)]";
        final String descriptionRgx = "(?<description>.*?)(?=\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}|\\Z)";
        Pattern PatternFullLog = Pattern.compile(timestampRgx + " " + levelRgx + "\\s+" + threadRgx + "\\s+" + descriptionRgx, Pattern.DOTALL);
        Matcher matcher = PatternFullLog.matcher(line);
        while (matcher.find()){
            Logs logs = new Logs();
            logs.setTimeStamp(matcher.group("timestamp"));
            logs.setLogLevel(matcher.group("level"));
            logs.setThread(matcher.group("thread"));
            logs.setDescription(matcher.group("description"));
            logsRepository.save(logs);
        }
    }

    public List<Logs> logsByLevel(String level) {
        return logsRepository.findByLogLevel(level);
    }

    public List<LogResult> logsCustomSort(String level, int n) {
        Map<String, Long> logsMap = logsRepository.findByLogLevel(level).stream()
                .collect(Collectors.groupingBy(Logs::getDescription, Collectors.counting()));
        return logsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(e -> new LogResult(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

}
