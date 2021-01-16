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
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class ExistsPostUserExsception extends   RuntimeException {

    public ExistsPostUserExsception() {
    }

    public ExistsPostUserExsception(String message) {
        super(message);
    }

    public ExistsPostUserExsception(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
