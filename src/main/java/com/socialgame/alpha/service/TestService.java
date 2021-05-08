package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class TestService {

    public ResponseEntity<?> testy(String hola) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.addError("ENTITY_NOT_FOUND"  , "Game with Id String: " + hola + " does not exist.");

        return ResponseEntity.ok(errorResponse);
    }
 }
