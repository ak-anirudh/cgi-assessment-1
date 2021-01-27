package org.cgi.assesment.repository;

import org.cgi.assesment.model.Logs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogsRepositoryTest {

    @Autowired
    private LogsRepository logsRepository;

    @Test
    public void testFindLogsByLevel(){
        List<Logs> logs = logsRepository.findByLogLevel("INFO");
        assertThat(logs.size()).isEqualTo(181);
    }

}
