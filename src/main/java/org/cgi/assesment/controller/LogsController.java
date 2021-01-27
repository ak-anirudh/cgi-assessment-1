package org.cgi.assesment.controller;

import lombok.RequiredArgsConstructor;
import org.cgi.assesment.exceptions.ResourceNotFoundException;
import org.cgi.assesment.model.LogResult;
import org.cgi.assesment.model.Logs;
import org.cgi.assesment.service.LogsService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@Validated
@RequiredArgsConstructor
public class LogsController {
    private final LogsService logsService;

    @GetMapping(value = "/{level}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Logs> logsByLevel(@PathVariable String level) throws ResourceNotFoundException {
        return logsService.logsByLevel(level);
    }

    @GetMapping(value = "/{level}/{limit}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<LogResult> logsCustomSort(@PathVariable String level, @PathVariable int limit) throws ResourceNotFoundException{
        return logsService.logsCustomSort(level, limit);
    }

}
