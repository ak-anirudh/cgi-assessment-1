package org.cgi.assesment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogResult {
    private String errorDescription;
    private Long occurrences;
}
