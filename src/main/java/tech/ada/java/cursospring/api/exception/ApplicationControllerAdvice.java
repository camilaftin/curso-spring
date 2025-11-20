package tech.ada.java.cursospring.api.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
@Log4j2
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {
    
    public static final String METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE = "Campo invalido: '%s'. Causa: '%s'.";
    
//    @Override
//    protected ResponseEntity(Object);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
                                                                  HttpHeaders headers, 
                                                                  HttpStatusCode status, 
                                                                  WebRequest request) {
        log.error(ex.getMessage(), ex);
        String errorMessage = getErrorMessages(ex.getBindingResult());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(), httpStatus, errorMessage);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private String getErrorMessages(BindingResult bindingResult) {
        return Stream.concat(
                bindingResult.getFieldErrors().stream().map(this::getMethodArgumentNotValidErrorMessage),
                bindingResult.getGlobalErrors().stream().map(this::getMethodArgumentValidErrorMessage)
        ).collect(Collectors.joining(","));

    }

    private String getMethodArgumentNotValidErrorMessage(FieldError error){
        return String.format(METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE, error.getField(), error.getDefaultMessage());
    }

    private String getMethodArgumentValidErrorMessage(ObjectError error){
        return String.format(METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE, error.getObjectName(), error.getDefaultMessage());
    }

    @ExceptionHandler(value = NaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> recursoNaoEncontradoExceptionHandler(NaoEncontradoException ex){
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(), ex.getStatus(), ex.getMessage());
        log.debug(ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> defaultExceptionHandler(Exception ex){
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(
//            MethodArgumentNotValidException ex){
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName,errorMessage);
//        });
//        return errors;
    }

