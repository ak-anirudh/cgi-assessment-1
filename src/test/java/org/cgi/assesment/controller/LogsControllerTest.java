package org.cgi.assesment.controller;

import org.cgi.assesment.service.LogsService;
import org.cgi.assesment.util.LogResultUtil;
import org.cgi.assesment.util.LogsUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class LogsControllerTest {
    @MockBean
    private LogsService logsService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() {

    }

    @Test
    public void logsByLevel() throws Exception {
        when(logsService.logsByLevel("DEBUG")).thenReturn(LogsUtils.createLogs());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/logs/DEBUG")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(LogsUtils.COLLECTION_COUNT)));
    }

    @Test
    public void logsCustomSort() throws Exception {
        when(logsService.logsCustomSort("DEBUG", 1)).thenReturn(LogResultUtil.createLogsResults());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/logs/DEBUG/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(LogResultUtil.COLLECTION_COUNT)));
    }
}
