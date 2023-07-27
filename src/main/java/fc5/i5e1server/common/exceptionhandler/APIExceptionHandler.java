package fc5.i5e1server.common.exceptionhandler;

import fc5.i5e1server.common.APIErrorResponse;
import fc5.i5e1server.common.util.ErrorMessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> httpMessageNotReadableExceptionHandle(HttpMessageNotReadableException e, BindingResult bindingResult) {
        return handleExceptionInternal(e, HttpStatus.BAD_REQUEST, bindingResult);
    }
    @ExceptionHandler
    public ResponseEntity<?> constraintViolationExceptionHandle(ConstraintViolationException e, BindingResult bindingResult) {
        return handleExceptionInternal(e, HttpStatus.BAD_REQUEST, bindingResult);
    }

    @ExceptionHandler
    public ResponseEntity<?> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e, BindingResult bindingResult) {
        return handleExceptionInternal(e, HttpStatus.BAD_REQUEST, bindingResult);
    }

    @ExceptionHandler
    public ResponseEntity<?> runtimeExceptionHandle(RuntimeException e, BindingResult bindingResult) {
        return handleExceptionInternal(e, HttpStatus.INTERNAL_SERVER_ERROR, bindingResult);
    }

    public ResponseEntity<?> handleExceptionInternal(Exception e, HttpStatus httpStatus, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return APIErrorResponse.of(httpStatus, ErrorMessageConverter.convert(bindingResult));
        }
        return APIErrorResponse.of(httpStatus, new String[]{e.getMessage()});
    }
}
