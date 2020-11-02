package io.github.boavenn.solvrobackendtask.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler
{
    private final static String DEFAULT_MESSAGE = "Something went wrong";
    private final static HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return defaultResponse(request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = pathFromWebRequest(request);
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage(), path);
        return new ResponseEntity<>(errorResponse, status);
    }

    private ResponseEntity<Object> defaultResponse(WebRequest request) {
        return ResponseEntity
                .status(DEFAULT_STATUS)
                .body(new ErrorResponse(DEFAULT_STATUS, DEFAULT_MESSAGE, pathFromWebRequest(request)));
    }

    private String pathFromWebRequest(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}
