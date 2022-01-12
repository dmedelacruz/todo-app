package com.todoapp.exception;

import com.todoapp.dao.response.BaseResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalOperationException.class)
    protected ResponseEntity<BaseResponse<Map<String, Object>>> handleIllegalOperation(IllegalOperationException illegalOperationException) {
        return new ResponseEntity<>(
                new BaseResponse<>(Map.of("success", false, "error", illegalOperationException.getMessage())),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<BaseResponse<Map<String, Object>>> handleRecordNotFound(RecordNotFoundException recordNotFoundException) {
        return new ResponseEntity<>(
                new BaseResponse<>(Map.of("success", false, "error", recordNotFoundException.getMessage())),
                HttpStatus.BAD_REQUEST);
    }

}
