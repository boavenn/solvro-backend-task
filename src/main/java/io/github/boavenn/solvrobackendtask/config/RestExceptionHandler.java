package io.github.boavenn.solvrobackendtask.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    private final static String DEFAULT_MESSAGE = "Something went wrong";
    private final static HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return defaultResponse(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = pathFromWebRequest(request);
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage(), path);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = pathFromWebRequest(request);
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage(), path);
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String message = "Request body is not readable";
        return ResponseEntity.status(status).body(new ErrorResponse(status, message, pathFromWebRequest(request)));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError == null) {
            return defaultResponse(request);
        } else {
            return ResponseEntity
                    .status(status)
                    .body(new ErrorResponse(status, fieldError.getDefaultMessage(), pathFromWebRequest(request)));
        }
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
