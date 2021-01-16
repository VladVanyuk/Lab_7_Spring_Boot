/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Root
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchPostException  extends   RuntimeException {

    public NoSuchPostException() {
    }

    public NoSuchPostException(String message) {
        super(message);
    }

    public NoSuchPostException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
