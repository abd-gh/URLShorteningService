package com.stackfortech.urlShorteningService.domin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {
    private Error error ;

    public ErrorResponse() {
    }

    public ErrorResponse(Error error) {
        this.error = error;
    }

    public ResponseEntity<?> generateErrorResponse(final Error error, final HttpStatus httpStatus){
        return ResponseEntity.status(httpStatus).body(error);
    }

    public Error getError() {
        return error;
    }
}
    