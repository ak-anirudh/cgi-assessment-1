package org.cgi.assesment.service;

import org.cgi.assesment.model.Logs;
import org.cgi.assesment.repository.LogsRepository;
import org.cgi.assesment.util.LogsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@SpringBootConfiguration
@ActiveProfiles("test")
public class LogsServiceTest {

    @Mock
    private LogsRepository logsRepository;

    @InjectMocks
    private LogsService logsService;

    @Test
    public void testLogsByLevel() {
        when(logsRepository.findByLogLevel("DEBUG")).thenReturn(LogsUtils.createLogs());
        List<Logs> logs = logsService.logsByLevel("DEBUG");
        assertThat(logs.size()).isEqualTo(LogsUtils.COLLECTION_COUNT);
    }

}
