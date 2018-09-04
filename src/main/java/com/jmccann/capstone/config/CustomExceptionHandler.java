package com.jmccann.capstone.config;

import com.jmccann.capstone.exceptions.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity exception(Exception exception) {
        log.error(exception.getMessage());
        ApiErrorResponse response = new ApiErrorResponse
                .ApiErrorResponseBuilder()
                .withDetail(exception.getMessage())
                .withStatus(HttpStatus.BAD_REQUEST)
                .withError_code(HttpStatus.BAD_REQUEST.value())
                .withMessage(exception.getLocalizedMessage())
                .build();

        return new ResponseEntity(response, response.getStatus());
    }

}
