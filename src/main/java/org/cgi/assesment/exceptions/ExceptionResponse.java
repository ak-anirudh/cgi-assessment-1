package org.cgi.assesment.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.Instant;


@Data
public class ExceptionResponse {
    private Instant timestamp;
    private HttpStatus errorCode;
    private String message;
    private String status;

    public ExceptionResponse() {
        this.timestamp = Instant.now();
    }
    public ExceptionResponse(HttpStatus errorCode, String message) {
        this();
        this.errorCode = errorCode;
        this.message = message;
    }
    public ExceptionResponse(HttpStatus errorCode, String message, String status) {
        this(errorCode, message);
        this.status = status;
    }
}
